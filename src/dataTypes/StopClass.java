/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataTypes.interfaces.*;

public class StopClass implements Stop {
    private final Station station;
    private final Time time;

    public StopClass(Station station, Time time) {
        this.station = station;
        this.time = time;
    }

    @Override
    public Station getStation() {
        return station;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return station + " " + time;
    }
}