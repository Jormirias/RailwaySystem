/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import java.io.Serializable;

import dataStructures.*;
import dataTypes.exceptions.*;

public interface Network extends Serializable {

    /**
     * Creates and inserts a Line and it's Stations into the Network.
     * @param lineName - the name of the Line to be created.
     * @param stationNames - the names of the Stations that belong to the Line.
     * @throws LineAlreadyExistsException if a Line with the same name already exists.
     */
    void insertLine(String lineName, ListInArray<String> stationNames) throws LineAlreadyExistsException;

    /**
     * Removes a Line from the Network.
     * @param lineName - the name of the Line to be removed.
     * @throws NoSuchLineException if there isn't a Line with the given name.
     */
    void removeLine(String lineName) throws NoSuchLineException;

    /**
     * For a given Line, get it's Stations.
     * @param lineName - the name of the Line
     * @return an Iterator to the Stations of the Line
     * @throws NoSuchLineException if there isn't a Line with the given name.
     */
    Iterator<Station> getLineStations(String lineName) throws NoSuchLineException;

    /**
     * For a given Station, get it's Lines.
     * @param stationName - the name of the Station
     * @return an Iterator to the Lines of the Station
     * @throws NoSuchStationException if there isn't a Station with the given name.
     */
    Iterator<Entry<String,String>> getStationLines(String stationName) throws NoSuchStationException;


    /**
     * Create and insert a Schedule for a given Line.
     * @param lineName - the name of the Line 
     * @param trainNumber - the number of the Train which will operate the Schedule
     * @param stationAndTimes - the route followed by the Train, described by a collection of Strings 
     * in the format "<station_name> <hours>:<minutes>".
     * @throws NoSuchLineException if there isn't a Line with the given name.
     * @throws InvalidScheduleException if:
     * the first station is not one of the terminal stations,
     * the stations are not in order,
     * the times are not strictly increasing and
     * in case of an overtake.
     * An overtake means that the current train will pass through a station either:
     * Before another train that has departed from the terminal station after the current train;
     * After another train that has departed from the terminal station before the current train.
     */
    void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchLineException, InvalidScheduleException;

    /**
     * Removes a Schedule from the given Line.
     * @param lineName - the name of the Line.
     * @param departureStationName - the first station in the Schedule.
     * @param timeAsString - the departure time from the station, as a String.
     * @throws NoSuchLineException if there isn't a Line with the given name.
     * @throws NoSuchScheduleException if there isn't a Schedule which starts a the departure station at the given time.
     */
    void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchLineException, NoSuchScheduleException;

    /**
     * Returns the schedules of a Line.
     * @param lineName - the name of the desired Line.
     * @param departureStationName - the name of the desired departure Station.
     * @return Iterator to the all the Schedules, organized by Time, as an Entry.
     * @throws NoSuchLineException if there isn't a Line with the given name.
     * @throws NoSuchDepartureStationException if there isn't a terminal Station with the given name.
     */
    Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchLineException, NoSuchDepartureStationException;

    /**
     * Gets all the trains which stop at a given station, by order of arrival.
     * @param stationName - the name of the Station
     * @return an iterator to the trains.
     * @throws NoSuchStationException if there isn't a Station with the given name.
     */
    Iterator<Entry<TrainTime, Time>> getStationRegistrySchedules(String stationName) throws NoSuchStationException;
    
    /**
     * Finds the Schedule whose train passes through a given departure station and arrival station.
     * The train must arrive at the arrival station either at or before the specified time.
     * @param lineName - the name of the Line
     * @param departureStationName - the name of the Station of departure.
     * @param arrivalStationName - the name of the Station of arrival.
     * @param timeAsString - the desired arrival time in a String format.
     * @return the Schedule which fulfills the criteria.
     * @throws NoSuchLineException if there isn't a Line with the given name.
     * @throws NoSuchDepartureStationException if there isn't a terminal Station with the given name.
     * @throws ImpossibleRouteException if no Schedule is found which fulfills the criteria.
     */
    Schedule getBestSchedule(String lineName, String departureStationName, String arrivalStationName, String timeAsString) throws NoSuchLineException, NoSuchDepartureStationException, ImpossibleRouteException;

}
