/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */

package dataTypes.interfaces;

import dataStructures.*;

import java.io.Serializable;

public interface Schedule extends Serializable{
    /**
     * Get the number of the train that runs this Schedule.
     * @return the number of the train.
     */
    int getTrainNumber();

    /**
     * Get the Stops that compose the Schedule.
     * @return Iterator to each Stop.
     */
    TwoWayIterator<Stop> getStops();

    /**
     * Get the station from which this Schedule's train departs.
     * @return the departure Station
     */
    Station getOriginStation();

    /**
     * Get the time of departure for the Schedule's train.
     * @return the Time of departure.
     */
    Time getDepartureTime();
}
