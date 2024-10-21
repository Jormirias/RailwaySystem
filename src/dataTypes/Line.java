/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.DoubleList;
import dataStructures.Iterator;

/**
 * Class which implements a Rail Line
 */
public class Line {
    /* Unique identifier for a Line */
    private String name;

    private DoubleList<Station> stations;

    // private OrderedList<Schedule> schedulesFromBeginTerminal;
    // private OrderedList<Schedule> schedulesFromEndTerminal;

    // replace with above.
    // should be ordered by departure time.
    private DoubleList<Schedule> schedules;

    public Line(String lineName, DoubleList<String> stationNames) {
        this.name = lineName;

        this.stations = new DoubleList<Station>();
        Iterator<String> it = stationNames.iterator();
        while (it.hasNext()) {
            this.stations.addLast(new Station(it.next()));
        }
    }

    public Line(String lineName) {
        this.name = lineName;

        this.stations = new DoubleList<Station>();
    }

    public String getName() {
        return this.name;
    }

    public Iterator<Station> getStations() {
        return this.stations.iterator();
    }

    /* Currently only supports one way */
    public void insertSchedule(String trainNumber, DoubleList<String> stationAndTimes) {
        int train = Integer.parseInt(trainNumber);
        Iterator<String> stationAndTimesIt = stationAndTimes.iterator();
        Iterator<Station> stationIt = stations.iterator();

        stationAndTimes.getFirst();

        Schedule schedule = new Schedule(train);

        while (stationAndTimesIt.hasNext()) {
            String[] stationAndTime = stationAndTimesIt.next().split(" ");
            String stationName = stationAndTime[0];
            String timeAsString = stationAndTime[1];

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if(station.getName().equals(stationName)) {
                    Time time = new Time(timeAsString);
                    station.addStop(train, time);
                    schedule.addStop(station, time);
                } else {
                    Time time = null;
                    station.addStop(train, time);
                    schedule.addStop(station, time);
                }
            }
        }

        schedules.addLast(schedule);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Line other = (Line) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }

        return true;
    }
}
