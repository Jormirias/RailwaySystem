/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.DoubleList;
import dataStructures.Iterator;

/**
 * Class which implements a Train Schedule
 */
public class Schedule  {
    int train;
    /**
     * ALl the stops in this schedule.
     */
    DoubleList<ScheduleStop> stops;

    public Schedule(int train) {
        this.train = train;
        this.stops = new DoubleList<ScheduleStop>();
    }

    /**
     * Only to be used while building the schedule.
     */
    public void addStop(Station station, Time time) {
        ScheduleStop stop = new ScheduleStop(station, time);
        stops.addLast(stop);
    }

    public Iterator<ScheduleStop> getStops() {
        return this.stops.iterator();
    }

    public Station getDepartureStation() {
        return stops.getFirst().getStation();
    }
}
