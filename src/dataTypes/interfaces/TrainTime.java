/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import java.io.Serializable;

public interface TrainTime extends Serializable{
    /**
     * Get the Train reference
     * @return the train id
     */
    public int getTrain();

    /**
     * Get the Time at which the train passes by
     * @return the Time reference of the train
     */
    public Time getTime();
}
