/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */

package dataTypes.interfaces;

import dataStructures.*;
import dataTypes.exceptions.*;

import java.io.Serializable;

public interface Line extends Serializable {
    /**
     * Returns the name of the Line
     * @return the String holding the name of this Line.
     *
     */
    String getName();

    /**
     * Returns the Stations of the Line
     * @return an iterator of Stations in this Line
     *
     */
    Iterator<Station> getStations();

    /**
     * Insert a new Schedule into the Line.
     * @param trainNumber - the number of the train that operates the schedule.
     * @param stationAndTimesString - Parsed Strings that map a station (first element) to a time (second element)
     * @throws InvalidScheduleException if:
     * the first station is not one of the terminal stations,
     * the stations are not in order,
     * the times are not strictly increasing and
     * in case of an overtake.
     * An overtake means that the current train will pass through a station either:
     * Before another train that has departed from the terminal station after the current train;
     * After another train that has departed from the terminal station before the current train.
     */
    ListInArray<Stop> insertSchedule(String trainNumber, ListInArray<String[]> stationAndTimesString) throws InvalidScheduleException;

    /**
     * Remove a Schedule from the Line.
     * @param departureStationName - the name of the first Station in the Schedule.
     * @param timeAsString - the Time of that departure as a String.
     * @throws NoSuchScheduleException if the Schedule does not exist.
     */
    Schedule removeSchedule(String departureStationName, String timeAsString) throws  NoSuchScheduleException;

    /**
     * Gets all of the Schedules of a Line.
     * @param departureStationName - the name of the departure Station.
     * @return Iterator to the all the Schedules, organized by Time, as an Entry
     * @throws NoSuchDepartureStationException if there is no Station with the given name
     *         in the Line.
     */
    Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NoSuchDepartureStationException;

    /**
     * Finds the Schedule whose train passes through a given departure station and arrival station.
     * The train must arrive at the arrival station either at or before the specified time.
     * @param departureStationName - the name of the Station of departure.
     * @param arrivalStationName - the name of the Station of arrival.
     * @param timeAsString - the desired arrival time in a String format.
     * @return the Schedule which fulfills the criteria.
     * @throws NoSuchDepartureStationException if there is not Station with the given name.
     * @throws ImpossibleRouteException if no Schedule is found which fulfills the criteria.
     */
    Schedule bestSchedule(String departureStationName, String arrivalStationName, String timeAsString) throws NoSuchDepartureStationException, ImpossibleRouteException;
}
