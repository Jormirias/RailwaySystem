/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;

import java.io.Serializable;

/**
 * Class which implements a Rail Line
 */
public class Line implements Serializable {
    /* Unique identifier for a Line */
    private final String name;

    private final ListInArray<Station> stations;
    
    // Ordered by departure time.
    private OrderedDoubleList<Time, Schedule> schedulesNormal;
    private OrderedDoubleList<Time, Schedule> schedulesInverted;

    public Line(String lineName, ListInArray<Station> newStations) {
        name = lineName;
        stations = newStations;

        schedulesNormal = new OrderedDoubleList<>();
        schedulesInverted = new OrderedDoubleList<>();
    }

    public String getName() {
        return this.name;
    }

    public ListInArray<Station> getStations() {
        return stations;
    }

    /* Currently only supports one way */
    public void insertSchedule(String trainNumber, ListInArray<ScheduleStop> stationAndTimes) throws dataStructures.IllegalArgumentException {
        int train = Integer.parseInt(trainNumber);
        ScheduleStop firstStop = stationAndTimes.getFirst();

        if(!firstStop.getStation().equals(stations.getFirst()) && !firstStop.getStation().equals(stations.getLast())) {
            throw new dataStructures.IllegalArgumentException();
        }
        
        if(!scheduleCheck(train, stationAndTimes)) {
            throw new dataStructures.IllegalArgumentException();
        }

        Schedule schedule = new Schedule(train, stationAndTimes);
        if(firstStop.getStation().equals(stations.getFirst())) {
            schedulesNormal.insert(firstStop.getTime(), schedule);
        }
        else if(firstStop.getStation().equals(stations.getLast())) {
            schedulesInverted.insert(firstStop.getTime(), schedule);
        }
    }

    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) {
        if(departureStationName.equals(stations.getFirst().getName())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.equals(stations.getLast().getName())) {
            return schedulesInverted.iterator();
        }

        return null;
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

    private boolean scheduleCheck (int train, ListInArray<ScheduleStop> stationAndTimes) {

        Iterator<ScheduleStop> stationAndTimesIt = stationAndTimes.iterator();
        Iterator<Station> stationIt = stations.iterator();

        Time lastTime = null;
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            ScheduleStop stationAndTime = stationAndTimesIt.next();
            String stationName = stationAndTime.getStation().getName();
            Time time = stationAndTime.getTime();

            //se a sequencia de horários não for cronologica, return false
            if(lastTime != null && time.compareTo(lastTime) < 0) {
                return false;
            }

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                   station.addStop(train, time);
                   break;
                }
            }

            lastTime = time;
        }
        
        //se a sequencia de estaçoes não segue as da linha, return false
        return !stationAndTimesIt.hasNext();
    }
}
