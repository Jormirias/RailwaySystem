/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataTypes.interfaces.*;

import java.io.Serial;

public class TimeClass implements Time {

    public static final int MIN_HOURS = 0;
    public static final int MAX_HOURS = 23;
    public static final int MIN_MINUTES = 0;
    public static final int MAX_MINUTES = 59;
    @Serial
    private static final long serialVersionUID = -1051531344653689437L;

    /**
     * Hours and minutes that compose this time.
     */
    private int hours = 0;
    private int minutes = 0;

    /**
     * Constructor
     * @param hours - hours associated to this Time.
     * @param minutes - minutes associated to this Time.
     */
    public TimeClass(int hours, int minutes) {
        if((MIN_HOURS <= hours) && (hours <= MAX_HOURS)) {
            this.hours = hours;
        }

        if((MIN_MINUTES <= minutes) && (minutes <= MAX_MINUTES)) {
            this.minutes = minutes;
        }
    }

    /**
     * Constructor
     * @param timeAsString - the Time given in a String formated as "<hours>:<minutes>"
     */
    public TimeClass(String timeAsString) {
        String[] splitString = timeAsString.split(":");
        int hours = Integer.parseInt(splitString[0]);
        int minutes = Integer.parseInt(splitString[1]);
        
        if((MIN_HOURS <= hours) && (hours <= MAX_HOURS)) {
            this.hours = hours;
        }

        if((MIN_MINUTES <= minutes) && (minutes <= MAX_MINUTES)) {
            this.minutes = minutes;
        }
    }

    @Override
    public int getHours() {
        return this.hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int compareTo(Time other) {
        if(this.hours < other.getHours()) {
            return -1;
        }
        else if(this.hours > other.getHours()) {
            return 1;
        } else {
            if(this.minutes < other.getMinutes()) {
                return -1;
            }
            else if(this.minutes > other.getMinutes()) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String hours = String.format("%02d", this.hours);
        String minutes = String.format("%02d", this.minutes);
        return hours + ":" + minutes;
    }
}
