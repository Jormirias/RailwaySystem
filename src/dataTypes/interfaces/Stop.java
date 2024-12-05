/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import java.io.Serializable;

public interface Stop extends Serializable{
    /**
     * Get the Station of this Stop.
     * @return the Station of this Stop.
     */
    Station getStation();

    /**
     * Get the Time of this Stop.
     * @return the Time of this Stop.
     */
    Time getTime();

}
