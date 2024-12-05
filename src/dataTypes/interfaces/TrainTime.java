/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import java.io.Serializable;

public interface TrainTime extends Comparable<TrainTime>, Serializable{

    /**
     * @return the Time at which the train stops.
     */
    Time getTime();

    /**
     * @return the number of the train.
     */
    Integer getTrain();
}
