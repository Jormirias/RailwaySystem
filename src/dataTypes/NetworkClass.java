/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

import java.io.Serial;

/**
 * Class which implements a Rail Network
 */
public class NetworkClass implements Network {


    @Serial
    private static final long serialVersionUID = -8049057733459193441L;

    /**
     * The Lines and StationRegistries which compose this Network.
     */
    private final Dictionary<String, Line> lines;
    private final Dictionary<String, StationRegistry> stationRegistries;

    public NetworkClass()
    {
        lines = new SepChainHashTable<>(500);
        stationRegistries = new SepChainHashTable<>(1000);
    }

    @Override
    public void insertLine(String lineName, ListInArray<String> stationNames) throws LineAlreadyExistsException {
        String lineNameUpper = lineName.toUpperCase();
        if (lines.find(lineNameUpper) != null){
            throw new LineAlreadyExistsException();
        }

        else{

            Line newLine = addLineToStationRegistry(lineName, stationNames);
            lines.insert(lineNameUpper, newLine);

        }
    }

    @Override
    public void removeLine(String lineName) throws NoSuchLineException {
        Line line = lines.remove(lineName.toUpperCase());
        if(line == null) {
            throw new NoSuchLineException();
        }
        removeLineFromStationRegistry(line);
    }

    @Override
    public Iterator<Station> getLineStations(String lineName) throws NoSuchLineException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else
            return line.getStations();
    }

    @Override
    public Iterator<Entry<String,String>> getStationLines(String stationName) throws NoSuchStationException {
        StationRegistry stationReg = stationRegistries.find(stationName.toUpperCase());
        if (stationReg == null) {
            throw new NoSuchStationException();
        }
        else {
            return stationReg.getLines();
        }
    }

    @Override
    public void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchLineException, InvalidScheduleException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            ListInArray<Stop> stops = line.insertSchedule(trainNumber, stationAndTimes);
            insertStationRegistrySchedules(trainNumber, stops);
        }
    }

    @Override
    public void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchLineException, NoSuchScheduleException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            Schedule removedSchedule = line.removeSchedule(departureStationName, timeAsString);

            removeStationRegistrySchedules(removedSchedule);
        }
    }

    @Override
    public Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchLineException, NoSuchDepartureStationException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            return line.getSchedules(departureStationName);
        }
    }

    @Override
    public Iterator<Entry<TrainTime, Time>> getStationRegistrySchedules(String stationName) throws NoSuchStationException {
        StationRegistry stationReg = stationRegistries.find(stationName.toUpperCase());
        if (stationReg == null){
            throw new NoSuchStationException();
        }
        else if(stationReg.hasTrainTimes()) {
            return stationReg.getTrainTimes();
        }
        else
            return null;
    }

    @Override
    public Schedule getBestSchedule(String lineName, String departureStationName, String arrivalStationName, String timeAsString)
            throws NoSuchLineException, NoSuchDepartureStationException, ImpossibleRouteException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            return line.bestSchedule(departureStationName, arrivalStationName, timeAsString);
        }
    }

    /**
     * Checks if input stations already exist in Registry. If they do, adds the Line registry. If they don't,
     * creates a new StationRegistry entry and adds a Line registry
     * @param lineName - the name of the Line
     * @param newStations - the name of the Stations of the Line.
     * @return Line that was created
     */
    private Line addLineToStationRegistry(String lineName, ListInArray<String> newStations) {

        ListInArray<Station> lineStations = new ListInArray<>(newStations.size());

        Iterator<String> it = newStations.iterator();
        while(it.hasNext()) {

            String stationName = it.next();
            String stationNameUpper = stationName.toUpperCase();
            Station station = new StationClass(stationName);

            StationRegistry stationReg = stationRegistries.find(stationNameUpper);
            if(stationReg == null) {
                stationReg = new StationRegistryClass(station.getName());
                stationRegistries.insert(stationNameUpper, stationReg);
            }

            stationReg.addLine(lineName);
            lineStations.addLast(station);
        }

        return new LineClass(lineName, lineStations);
    }

    /**
     * Removes Line registry from each Station Registry in the removed Line.
     * If the Station does not have any Line anymore, the Station Registry is removed
     * @param line - the Line to be removed.
     */
    private void removeLineFromStationRegistry(Line line) {
        Iterator<Station> it = line.getStations();
        while(it.hasNext()) {
            Station station = it.next();

            String stationNameUpper = station.getName().toUpperCase();
            StationRegistry stationReg = stationRegistries.find(stationNameUpper);
            stationReg.removeLine(line.getName());
            if(!stationReg.hasLines()) {
                stationRegistries.remove(stationNameUpper);
            }
            else {
                TrainTime keyTrainTime;

                if(station.hasStops(false)) {
                    Iterator<Entry<Time, Train>> itStops = station.getStops(false);
                    while (itStops.hasNext()) {
                        Entry<Time, Train> currStop = itStops.next();
                        keyTrainTime = new TrainTimeClass(currStop.getKey(), currStop.getValue().getNumber());
                        stationReg.removeTrainTime(keyTrainTime);
                    }
                }

                if(station.hasStops(true)) {
                    Iterator<Entry<Time, Train>> itStopsReversed = station.getStops(true);
                    while (itStopsReversed.hasNext()) {
                        Entry<Time, Train> currStop = itStopsReversed.next();
                        keyTrainTime = new TrainTimeClass(currStop.getKey(), currStop.getValue().getNumber());
                        stationReg.removeTrainTime(keyTrainTime);
                    }
                }
            }
        }
    }

    /**
     * Inserts Schedule data into each corresponding StationRegistry
     * @param trainNumber - the number of the train to be inserted, as a String
     * @param stationAndTimes - the Stops which the train performs.
     */
    private void insertStationRegistrySchedules(String trainNumber, ListInArray<Stop> stationAndTimes) {
        Iterator<Stop> it = stationAndTimes.iterator();
        while(it.hasNext()) {
            Stop currStop = it.next();

            String stationNameUpper = currStop.getStation().getName().toUpperCase();
            StationRegistry stationReg = stationRegistries.find(stationNameUpper);

            int trainNumberInt = Integer.parseInt(trainNumber);
            //For each added station in the schedule.. add an entry to stationreg
            stationReg.addTrainTime(currStop.getTime(), trainNumberInt, currStop.getTime());

        }
    }

    /**
     * Remove a Schedule from a StationRegistry.
     * @param targetSchedule - Schedule to be removed.
     */
    private void removeStationRegistrySchedules(Schedule targetSchedule) {
        TwoWayIterator<Stop> it = targetSchedule.getStops();
        TrainTime keyTrainTime = null;
        while(it.hasNext()) {
            Stop currStop = it.next();

            String stationNameUpper = currStop.getStation().getName().toUpperCase();
            StationRegistry stationReg = stationRegistries.find(stationNameUpper);
            keyTrainTime = new TrainTimeClass(currStop.getTime(), targetSchedule.getTrainNumber());
            stationReg.removeTrainTime(keyTrainTime);

        }


    }
}
