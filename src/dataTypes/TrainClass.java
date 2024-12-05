/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;

import java.io.Serial;

public class TrainClass implements Train {

    @Serial
    private static final long serialVersionUID = 6621631726176179672L;

    /**
     * The unique number and departure Time of this Train.
     */
    private final int number;
    private final Time departureTime;

    /**
     * Names of the stations at which this train may stop.
     * Stopping or not is given by the stored value being true or false.
     */
    private final Dictionary<String, Boolean> stops;

    public TrainClass(int number, ListInArray<Station> stations, Time departureTime) {
        this.number = number;
        this.departureTime = departureTime;

        this.stops = new SepChainHashTable<>(stations.size());
        Iterator<Station> it = stations.iterator();
        while(it.hasNext()) {
            stops.insert(it.next().getName(), false);
        }
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Time getDepartureTime() {
        return departureTime;
    }

    @Override
    public boolean departsBefore(Time time) {
        return departureTime.compareTo(time) < 0;
    }

    @Override
    public boolean departsAfter(Time time) {
        return departureTime.compareTo(time) > 0;
    }

    @Override
    public void setAsStop(Station station) {
        stops.insert(station.getName(), true);
    }

    @Override
    public boolean stopsAt(Station station) {
        return stops.find(station.getName());
    }
}
