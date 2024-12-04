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


    public boolean isStopValid(Time departureTime, Time arrivalTime, boolean isInverted);

    public void addStop(Time time, Train train, boolean isInverted);

    public void removeStop(Time time, boolean isInverted);

    public boolean testName(String other);

    public Iterator<Entry<Time, Train>> getStops(boolean isInverted);

    public boolean hasStops(boolean isInverted);

    public Stack<Train> findBestScheduleTrains(Time time, boolean isInverted);
}
