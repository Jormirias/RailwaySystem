/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataTypes.*;

public interface Station {
    /**
     * Get the name of the Station.
     * @return the name of the Station.
     */
    public String getName();

    /**
     * Add a stop to the Station.
     * @param train - the number of the train.
     * @param time - the time at which it stops.
     */
    public void addStop(int train, Time time);

    /**
     * Remove the stop from the Station.
     * @param train - the number of the train.
     * @param time - the time at which it stops.
     */
    public void removeStop(int train, Time time);
}
