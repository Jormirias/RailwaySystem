/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import dataTypes.TimeClass;

import java.io.Serializable;

public interface Station extends Serializable {
    /**
     * Get the name of the Station.
     * @return the name of the Station.
     */
    public String getName();

    /**
     * Add a stop to the Station.
     * @param time - the time at which it stops.
     * @param train - the number of the train.
     * @param isInverted - indicates if this stop belongs to an inverted schedule
     */
    public void addStop(Time time, int train, boolean isInverted);

    /**
     * Remove the stop from the Station.
     * @param time - the time at which it stops.
     * @param isInverted - indicates if this stop belongs to an inverted schedule
     */
    public void removeStop(Time time, boolean isInverted);

    // TODO
    public void addLine(Line line);

    // TODO
    public void removeLine(Line line);

    // TODO
    public Iterator<Entry<String, Line>> getLines();

    // TODO
    public boolean hasLines();

    public boolean testName(String other);

    public TwoWayIterator<Entry<Time, Integer>> stopsIterator(boolean isInverted);
}
