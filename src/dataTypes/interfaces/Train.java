/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */

package dataTypes.interfaces;

import java.io.Serializable;

public interface Train extends Serializable {
    /**
     * @return the number which identifies this Train
     */
    int getNumber();

    /**
     * @return the Time at which this Train departs from the origin terminal station.
     */
    Time getDepartureTime();

    /**
     * Checks if this Train departs before a given time.
     * @param time - the Time for reference
     * @return true if it does, false if it doesn't.
     */
    boolean departsBefore(Time time);

    /**
     * Checks if this Train departs before a given time.
     * @param time - the Time for reference
     * @return true if it does, false if it doesn't.
     */
    boolean departsAfter(Time time);

    /**
     * Sets one a given station as a stop of this Train
     * @param station - the Station at which this Train stops
     */
    void setAsStop(Station station);
    
    /**
     * Checks if this Train stops at a given station.
     * @param station - the Station for reference
     * @return true if this Train stops at the Station, false if it doesn't.
     */
    boolean stopsAt(Station station);
}
