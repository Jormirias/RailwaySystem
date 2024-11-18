/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.*;

import java.io.Serializable;

public interface Schedule extends Serializable{
    /**
     * Get the number of the train that runs this Schedule.
     * @return the number of the train.
     */
    public int getTrainNumber();

    /**
     * Get the Stops that compose the Schedule.
     * @return Iterator to each Stop.
     */
    public TwoWayIterator<Stop> getStops();

    public Station getOriginStation();
}
