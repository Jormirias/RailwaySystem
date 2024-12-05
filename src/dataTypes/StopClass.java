/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;
import dataTypes.interfaces.*;

import java.io.Serial;

public class StopClass implements Stop {

    @Serial
    private static final long serialVersionUID = 8511184505874733381L;
    private Station station;
    private Time time;

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