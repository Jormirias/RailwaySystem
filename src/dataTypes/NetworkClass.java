/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Rail Network
 */
public class NetworkClass implements Network {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    // Dictionary, with Separate Chaining Hash Table implementation.
    // Why? Strings are used as unique identifiers for the both elements,
    // and hash maps allow an expected case O(1 + occupancy) for insertion, removal and search.
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
            insertStationRegistrySchedules(lineName, trainNumber, stops);
        }
    }

    @Override
    public void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchLineException, NoSuchScheduleException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            line.removeSchedule(departureStationName, timeAsString);
            //TODO removeStationRegistrySchedules();
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


    // Checks if input stations already exist in Registry; If they do, adds the Line registry. If they don't,
    // creates a new StationRegistry entry and adds a Line registry
    // Then, returns the List of Stations to be used in Line
    private Line addLineToStationRegistry(String lineName, ListInArray<String> newStations) {

        ListInArray<Station> lineStations = new ListInArray<>();

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

    // Removes Line registry from each Station Registry in the removed Line;
    // If the Station does not have any Line anymore, the Station Registry is removed
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
        }
    }

    /**
     * Inserts Schedule data into each corresponding StationRegistry,
     * while iterating over the input stations given by the command
     *
     */
    private void insertStationRegistrySchedules(String lineName, String trainNumber, ListInArray<Stop> stationAndTimes) {
        Iterator<Stop> it = stationAndTimes.iterator();
        Stop currStop = it.next();
        Time departureTime = currStop.getTime();
        while(it.hasNext()) {


            String stationNameUpper = currStop.getStation().getName().toUpperCase();
            StationRegistry stationReg = stationRegistries.find(stationNameUpper);

            int trainNumberInt = Integer.parseInt(trainNumber);
            //For each added station in the schedule.. add an entry to stationreg
            stationReg.addTrainTime(departureTime, trainNumberInt, currStop.getTime());

            currStop = it.next();
        }
    }

    private void removeStationRegistrySchedules(int trainNumber) {

    }

    public TwoWayIterator<Entry<Time, TrainTime>> getStationRegistrySchedules(String stationName) throws NoSuchStationException {
        StationRegistry stationReg = stationRegistries.find(stationName.toUpperCase());
        if (stationReg == null){
            throw new NoSuchStationException();
        }
        else
            return stationReg.getTrainTimes();
    }
}
