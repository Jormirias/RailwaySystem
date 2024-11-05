/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.*;
import dataTypes.*;
import dataTypes.exceptions.*;

public interface Line {
    /**
     * Returns the name of the Line
     * @return the String holding the name of this Line.
     *
     */
    public String getName();

    /**
     * Returns the Station collection of the Line
     * @return the Collection of Stations in this Line
     *
     */
    public ListInArray<Station> getStations();

    /**
     * Insert a new Schedule into the Line.
     * @param trainNumber - the number of the train that operates the schedule.
     * @param stationAndTimesString - Parsed Strings that map a station (first element) to a time (second element)
     * @throws InvalidScheduleException if the Stations are not in order or the Time is not strictly increasing.
     */
    public void insertSchedule(String trainNumber, ListInArray<String[]> stationAndTimesString) throws InvalidScheduleException;

    /**
     * Remove a Schedule from the Line.
     * @param departureStationName - the name of the first Station in the Schedule.
     * @param timeAsString - the Time of that departure as a String.
     * @throws NoSuchScheduleException if the Schedule does not exist.
     */
    public void removeSchedule(String departureStationName, String timeAsString) throws  NoSuchScheduleException;

    /**
     * Gets all of the Schedules of a Line.
     * @param departureStationName - the name of the departure Station.
     * @return Iterator to the all the Schedules, organized by Time, as an Entry
     * @throws NoSuchDepartureStationException if there is no Station with the given name
     *         in the Line.
     */
    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NoSuchDepartureStationException;
}
