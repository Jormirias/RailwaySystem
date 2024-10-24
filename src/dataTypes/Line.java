/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataStructures.IllegalArgumentException;

import java.io.Serializable;

/**
 * Class which implements a Rail Line
 */
public class Line implements Serializable {
    /* Unique identifier for a Line */
    private final String name;

    private final Queue<Station> stations;

    // private OrderedList<Schedule> schedulesFromBeginTerminal;
    // private OrderedList<Schedule> schedulesFromEndTerminal;

    // replace with above.
    // should be ordered by departure time.
    private OrderedDoubleList<Integer, Schedule> schedulesNormal;
    private OrderedDoubleList<Integer, Schedule> schedulesInverted;

    public Line(String lineName, Queue<Station> newStations) {
        name = lineName;
        stations = newStations;
    }

    public String getName() {
        return this.name;
    }

    public Queue<Station> getStations() {
        return stations;
    }

    /* Currently only supports one way */
    public void insertSchedule(String trainNumber, QueueInArray<EntryClass<Station,Time>> stationAndTimes) throws IllegalArgumentException {

        if(!scheduleCheck(stationAndTimes))
            throw new IllegalArgumentException();

        else {
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
                    if (station.getName().equals(stationName)) {
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

    private boolean scheduleCheck (QueueInArray<EntryClass<Station,Time>> stationAndTimes) {
        //TODO
        //se a primeira estção não é uma das duas terminais, return false
        //se a sequencia de estaçoes não segue as da linha, return false
        //se a sequencia de horários não for cronologica, return false
        return true;
    }
}
