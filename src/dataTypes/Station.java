/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;


/**
 * Class which implements a Train Station
 */
public class Station {
    private final String name;

    /**
     * All the stops at this station.
     */
    private final StationStop[] stops;
    private int stopsCount = 0;

    public Station(String name) {
        this.name = name;
        this.stops = new StationStop[36];
    }

    public String getName() {
        return name;
    }

    public void addStop(int train, Time time) {
        StationStop stop = new StationStop(train, time);
        stops[stopsCount++] = stop;
    }
    
}
