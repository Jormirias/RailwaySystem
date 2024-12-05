/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.Entry;
import dataStructures.Iterator;

import java.io.Serializable;

public interface StationRegistry extends Serializable {
    /**
     * Get the name of the Station.
     * @return the name of the Station.
     */
    public String getName();

    /**
     * Add a train time to the StationRegistry.
     * @param departureTime - the time at which the train leaves the terminal station.
     * @param train - the number of the train passing
     * @param time - indicates the time the train passes this station
     */
    public void addTrainTime(Time departureTime, int train, Time time);

    /**
     * Remove a train time from the StationRegistry.
     * @param train - id of the train to be removed.
     */
    public void removeTrainTime(TrainTime train);

    /**
     * Add a line name to the StationRegistry (if it doesn't already exist)
     * @param lineName - name of the line to be added
     */
    public void addLine(String lineName);

    /**
     * Remove a line name from the StationRegistry
     * This should only be called if no lines hold this Station name
     * @param lineName - name of the line to be removed
     */
    public void removeLine(String lineName);

    /**
     * @return an iterator of all the lines where this Station is included, ordered lexicographically
     */
    public Iterator<Entry<String, String>> getLines();

    /**
     * Checks if the station associated to this StationRegistry have any lines.
     * @return true if the station has lines, false if not.
     */
    public boolean hasLines();

    /**
     * Compares this Line's name with a given string.
     * @param other - the String for comparsion
     * @return true if they are equal, false if they are not.
     */
    public boolean testName(String other);

    /**
     * @return an iterator of all the Train times of train passing this Station, ordered by Time of departure
     */
    public Iterator<Entry<TrainTime, Time>> getTrainTimes();

    /**
     * Checks if there are any trains running schedule through the stations associated with this StationRegistry
     * @return 
     */
    public boolean hasTrainTimes();

}
