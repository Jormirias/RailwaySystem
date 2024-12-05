/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */

package dataTypes.interfaces;

import java.io.Serializable;

public interface Time extends Comparable<Time>, Serializable{
    /**
     * Get the hours of this Time.
     * @return the hours of this Time.
     */
    int getHours();

    /**
     * Get the minutes of this Time.
     * @return the minutes of this Time.
     */
    int getMinutes();
}
