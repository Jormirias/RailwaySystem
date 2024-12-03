/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import java.io.Serializable;

import dataStructures.*;
import dataTypes.exceptions.*;

public interface Network extends Serializable {

    // TODO
    public void insertLine(String lineName, ListInArray<String> stationNames) throws LineAlreadyExistsException;

    /**
     * Remove Line from Network
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, and if it does, it removes it.
     * If it doesn't exist, it throws an error upstream
     * This method doesn't use the findLineWithName method to prevent iterating twice over the same collection. Instead, it automatically removes the line while iterating.
     *
     */
    public void removeLine(String lineName) throws NoSuchLineException;

    /**
     * Consult Line Stations
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @return If the Line exists, the method returns a collection of its Stations
     *
     */
    public Iterator<Station> getLineStations(String lineName) throws NoSuchLineException;

    // TODO
    public Iterator<Entry<String,String>> getStationLines(String stationName) throws NoSuchStationException;

    /**
     * Insert a new Schedule in a Line
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @param trainNumber indicates the train number associated with the new Schedule
     * @param stationAndTimes is the collections of stops to be associated with the new Schedule
     *
     */
    public void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchLineException, InvalidScheduleException;

    /**
     * Remove a new from a Line
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @param departureStationName indicates the name of the first station of the Schedule
     * @param timeAsString is the time corresponding to the first time of the schedule
     *
     */
    public void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchLineException, NoSuchScheduleException;

    /**
     * Returns the schedules of a Line.
     * @param lineName - the name of the desired Line.
     * @param departureStationName - the name of the desired departure Station.
     * @return Iterator to the all the Schedules, organized by Time, as an Entry.
     * @throws NoSuchLineException
     * @throws NoSuchDepartureStationException
     */
    public Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchLineException, NoSuchDepartureStationException;

    public Schedule getBestSchedule(String lineName, String departureStationName, String arrivalStationName, String timeAsString);

    public Iterator<Entry<TrainTime, Time>> getStationRegistrySchedules(String stationName) throws NoSuchStationException;
}
