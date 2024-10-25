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

    private final ListInArray<Station> stations;

    // private OrderedList<Schedule> schedulesFromBeginTerminal;
    // private OrderedList<Schedule> schedulesFromEndTerminal;

    // replace with above.
    // should be ordered by departure time.
    private OrderedDoubleList<Time, Schedule> schedulesNormal;
    private OrderedDoubleList<Time, Schedule> schedulesInverted;

    public Line(String lineName, ListInArray<Station> newStations) {
        name = lineName;
        stations = newStations;
    }

    public String getName() {
        return this.name;
    }

    public ListInArray<Station> getStations() {
        return stations;
    }

    /* Currently only supports one way */
    public void insertSchedule(String trainNumber, ListInArray<EntryClass<Station,Time>> stationAndTimes) throws IllegalArgumentException {

        if(!scheduleCheck(stationAndTimes))
            throw new IllegalArgumentException();

        else {
            int train = Integer.parseInt(trainNumber);

            Schedule schedule = new Schedule(train, stationAndTimes);
            EntryClass<Station,Time> firstStation = stationAndTimes.getFirst();
            if(firstStation.getKey() == stations.getFirst())
                schedulesNormal.insert(firstStation.getValue(), schedule);
            else if(firstStation.getKey() == stations.getLast())
                schedulesInverted.insert(firstStation.getValue(), schedule);
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

    private boolean scheduleCheck (ListInArray<EntryClass<Station,Time>> stationAndTimes) {

        Iterator<EntryClass<Station,Time>> stationAndTimesIt = stationAndTimes.iterator();
        Iterator<Station> stationIt = stations.iterator();

        /**
        while (stationAndTimesIt.hasNext()) {
            EntryClass<Station,Time> stationAndTime = stationAndTimesIt.next();
            String stationName = stationAndTime.getKey().getName();
            Time timeAsString = stationAndTime.getValue();

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (!station.getName().equals(stationName)) {
                   return false;
                }
            }
        }**/
        //TODO
        //se a primeira estção não é uma das duas terminais, return false
        //se a sequencia de estaçoes não segue as da linha, return false
        //se a sequencia de horários não for cronologica, return false
        return true;
    }
}
