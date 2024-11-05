/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

public interface Time extends Comparable<Time>{
    /**
     * Get the hours of this Time.
     * @return the hours of this Time.
     */
    public int getHours();

    /**
     * Get the minutes of this Time.
     * @return the minutes of this Time.
     */
    public int getMinutes();
}
