/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.*;
import dataTypes.*;
import dataTypes.interfaces.*;

import java.io.Serializable;

public interface Station extends Serializable {
    /**
     * Get the name of the Station.
     * @return the name of the Station.
     */
    public String getName();

    /**
     * Verify if a train stopping at this Station at a given time would not cause an overtake situation.
     * @param departureTime - the Time at which the train to be added departs from the departure station.
     * @param arrivalTime - the Time at which the train arrives at the current station.
     * @param isInverted - what way the route is going.
     * @return true if the stop is valid, false if it isn't.
     */
    public boolean isStopValid(Time departureTime, Time arrivalTime, boolean isInverted);

    /**
     * Add a stop to the Station
     * @param time - the time at which the Train stops
     * @param train - the Train which makes the stop
     * @param isInverted - what way the route is going.
     */
    public void addStop(Time time, Train train, boolean isInverted);

    /**
     * Remove a stop from the Station.
     * @param time - the time at which the stop happens.
     * @param isInverted - what way the route is going.
     */
    public void removeStop(Time time, boolean isInverted);

    /**
     * Compares this Station's name with a given string.
     * @param other - the String for comparsion
     * @return true if they are equal, false if they are not.
     */
    public boolean testName(String other);

    /**
     * Get the stops at this Station, ordered by the time at which they happen.
     * @param isInverted - what way the route is going.
     * @return an Iterator to the stop collection
     */
    public Iterator<Entry<Time, Train>> getStops(boolean isInverted);

    /**
     * Checks if the Station has any stops.
     * @param isInverted - what way the route is going.
     * @return true if there are any stops, false if none.
     */
    public boolean hasStops(boolean isInverted);

    /**
     * Finds the trains which stop at this Station up to, inclusive, a given time.
     * @param time - the latest Time at which a train has to stop.
     * @param isInverted - what way the route is going.
     * @return a Stack with the Trains which meet the given criteria.
     */
    public Stack<Train> findBestScheduleTrains(Time time, boolean isInverted);
}
