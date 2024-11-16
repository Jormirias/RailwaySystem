/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

import java.io.Serializable;
import java.util.HashMap;

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
    private final Dictionary<String, Station> stations;

    public NetworkClass()
    {
        lines = new SepChainHashTable<>();
        stations = new SepChainHashTable<>();
    }

    @Override
    public void insertLine(String lineName, ListInArray<String> stationNames) throws LineAlreadyExistsException {
        String lineNameUpper = lineName.toUpperCase();
        if (lines.find(lineNameUpper) != null){
            throw new LineAlreadyExistsException();
        }

        else{
            ListInArray<Station> lineStations = getStations(stationNames);
            Line line = new LineClass(lineName, lineStations);
            lines.insert(lineNameUpper, line);
            addLineToStations(line, lineStations);
        }
    }

    @Override
    public void removeLine(String lineName) throws NoSuchLineException {
        Line line = lines.remove(lineName);
        if(line == null) {
            throw new NoSuchLineException();
        }
        removeLineFromStations(line);
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
    public Iterator<Line> getStationLines(String stationName) throws NoSuchStationException {
        Station station = stations.find(stationName);
        if (station == null) {
            throw new NoSuchStationException();
        }
        else {
            return station.getLines();
        }
    }

    @Override
    public void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchLineException, InvalidScheduleException, NullPointerException {
        Line line = lines.find(lineName.toUpperCase());
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            line.insertSchedule(trainNumber, stationAndTimes);
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

    // TODO
    private ListInArray<Station> getStations(ListInArray<String> newStations) {
        ListInArray<Station> lineStations = new ListInArray<>();

        Iterator<String> it = newStations.iterator();
        while(it.hasNext()) {
            String stationName = it.next();
            String stationNameUpper = stationName.toUpperCase();
            Station station = stations.find(stationNameUpper);
            if(station == null) {
                station = new StationClass(stationName);
                stations.insert(stationNameUpper, station);
            }
            lineStations.addLast(station);
        }

        return lineStations;
    }

    // TODO
    private void addLineToStations(Line line, ListInArray<Station> lineStations) {
        Iterator<Station> it = lineStations.iterator();
        while(it.hasNext()) {
            Station station = it.next();
            station.addLine(line);
        }
    }

    // TODO
    private void removeLineFromStations(Line line) {
        Iterator<Station> it = line.getStations();
        while(it.hasNext()) {
            Station station = it.next();
            station.removeLine(line);
            if(!station.hasLines()) {
                stations.remove(station.getName().toUpperCase());
            }
        }
    }

}
