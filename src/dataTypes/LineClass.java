/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

import java.io.Serial;

/**
 * Class which implements a Rail Line
 */
public class LineClass implements Line {


    @Serial
    private static final long serialVersionUID = 8810177429357124920L;
    /**
     * String Line name
     * Unique identifier for a Line
     *
     */
    private final String name;

    /**
     * All the Stations of this Line, by order of insertion.
     *
     */
    private final ListInArray<Station> stations;

    /**
     * All the Schedules of this Line, by order of departure time.
     * One collection for the normal route (from first station), 
     * another for the inverted route (from last station.)
     *
     */
    private final OrderedDictionary<Time, Schedule> schedulesNormal;
    private final OrderedDictionary<Time, Schedule> schedulesInverted;

    /**
     * Line Constructor
     * Receives the lineName and the Station collection, allocates them, and initializes two Schedule Collections, one normal and the other for the inverted line direction
     *
     */
    public LineClass(String lineName, ListInArray<Station> newStations) {
        name = lineName;
        stations = newStations;

        schedulesNormal = new AVLTree<>();
        schedulesInverted = new AVLTree<>();
    }

    public String getName() {
        return this.name;
    }

    public Iterator<Station> getStations() {
        return stations.iterator();
    }

    public ListInArray<Stop> insertSchedule(String trainNumber, ListInArray<String[]> stationAndTimesString) throws InvalidScheduleException {

        String[] firstStopString = stationAndTimesString.getFirst();
        
        Time departureTime =  new TimeClass(firstStopString[1]);
        Train train = new TrainClass(Integer.parseInt(trainNumber), stations, departureTime);

        //Validity Check
        if(!scheduleCheck(stationAndTimesString, firstStopString)) {
            throw new InvalidScheduleException();
        }

        boolean isInverted;
        isInverted = !firstStopString[0].equals(stations.getFirst().getName());
        
        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, inserts the train number and time to its collections.
        ListInArray<Stop> stops = new ListInArray<>(stationAndTimesString.size());
        TwoWayIterator<String[]> stationAndTimesStringIt = stationAndTimesString.iterator();

        while(stationAndTimesStringIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesStringIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);
            Iterator<Station> stationIt = stations.iterator();
            
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    station.addStop(time, train, isInverted);
                    train.setAsStop(station);
                    stops.addLast(new StopClass(station, time));
                    break;
                }
            }
        }

        //Create Schedule and put it in corresponding collection.
        Schedule schedule = new ScheduleClass(train.getNumber(), stops);
        Stop firstStop = stops.getFirst();
        if(firstStop.getStation().getName().equals(stations.getFirst().getName())) {
            schedulesNormal.insert(firstStop.getTime(), schedule);
        }
        else if(firstStop.getStation().getName().equals(stations.getLast().getName())) {
            schedulesInverted.insert(firstStop.getTime(), schedule);
        }

        return stops;
    }

    public Schedule removeSchedule(String departureStationName, String timeAsString) throws  NoSuchScheduleException {
        Schedule schedule = null;
        Time time = new TimeClass(timeAsString);
        boolean isInverted = false;

        if(departureStationName.equalsIgnoreCase(stations.getFirst().getName())) {
            schedule = schedulesNormal.remove(time);
        }
        else if(departureStationName.equalsIgnoreCase(stations.getLast().getName())) {
            schedule = schedulesInverted.remove(time);
            isInverted = true;
        }

        if(schedule == null) {
            throw new NoSuchScheduleException();
        }

        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, removes the train number from this line Station
        TwoWayIterator<Stop> stopsIt = schedule.getStops();

        while(stopsIt.hasNext()) {
            Stop stop = stopsIt.next();
            Iterator<Station> stationIt = stations.iterator();
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getStation())) {
                   station.removeStop(stop.getTime(), isInverted);
                   break;
                }
            }
        }
        return schedule;
    }

    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NoSuchDepartureStationException {
        if(departureStationName.equalsIgnoreCase(stations.getFirst().getName())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.equalsIgnoreCase(stations.getLast().getName())) {
            return schedulesInverted.iterator();
        } else {
            throw new NoSuchDepartureStationException();
        }
    }

    public Schedule bestSchedule(String departureStationName, String arrivalStationName, String timeAsString)
            throws NoSuchDepartureStationException, ImpossibleRouteException {

        // Search for the arrival station, already searching for the first station.
        TwoWayIterator<Station> stationIt = stations.iterator();
        Station firstStation = null;
        Station lastStation = null;
        Station currStation;
        boolean isInverted = false;
        while (stationIt.hasNext()) {
            currStation = stationIt.next();
            if (currStation.testName(departureStationName)) {
                firstStation = currStation;
            }
            if (currStation.testName(arrivalStationName)) {
                lastStation = currStation;
                break;
            }
        }
        if (lastStation == null) {
            throw new ImpossibleRouteException();
        }

        // Then, search for the departure station and establish which schedules (normal or inverted) to use

        if (firstStation == null) {
            isInverted = true;
            //Iterate from the last station to the end of Stations collection..
            while (stationIt.hasNext()) {
                currStation = stationIt.next();
                if (currStation.testName(departureStationName)) {
                    firstStation = currStation;
                    break;
                }
            }

            if (firstStation == null) {
                throw new NoSuchDepartureStationException();
            }
        }
        Time targetTime = new TimeClass(timeAsString);
        Stack<Train> trainsInOrder = lastStation.findBestScheduleTrains(targetTime, isInverted);
        while(!trainsInOrder.isEmpty()) {
            Train arrivalTrain = trainsInOrder.pop();
            if (arrivalTrain.stopsAt(firstStation)) {
                if (isInverted) {
                    return schedulesInverted.find(arrivalTrain.getDepartureTime());
                } else {
                    return schedulesNormal.find(arrivalTrain.getDepartureTime());
                }
            }
        }

        throw new ImpossibleRouteException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final LineClass other = (LineClass) obj;
        return this.name.equals(other.name);
    }


    /**
     * Helper method to check a Schedule validity.
     * @return true if valid, false if not
     * Validity means respecting the criteria described in insertSchedule's documentation.
     */
    private boolean scheduleCheck (ListInArray<String[]> stationAndTimesString, String[] firstStopString) {

        
        boolean inverted;
        boolean invertFlag = false;
        
        //Verificar se a Station da primeira Stop corresponde a uma das 2 estações terminais, e a qual
        if(firstStopString[0].equalsIgnoreCase(stations.getFirst().getName())) {
            inverted=false;
        } else if (firstStopString[0].equalsIgnoreCase(stations.getLast().getName())) {
            inverted = true;
        } else
        return false;
        
        if(inverted) {
            stationAndTimesString.invert();
            invertFlag = true;
        }
        
        Time departureTime =  new TimeClass(firstStopString[1]);

        TwoWayIterator<String[]> stationAndTimesIt = stationAndTimesString.iterator();
        Iterator<Station> stationIt = stations.iterator();
        Time lastTime = new TimeClass(0, 0);
        if(inverted) {
            stationAndTimesIt.rewind();
            lastTime = new TimeClass(23, 59);
        }
        //itera sobre stationAndTimes, e stations
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);

            //se a sequencia de horários não for estritamente crescente, return false

            if(invertFlag && time.compareTo(lastTime) >= 0) {
                return false;
            }
            else if (!invertFlag && time.compareTo(lastTime) <= 0){
                return false;
            }

            //Para cada estação dada, compara à atual das stations. se não corresponder, avança à procura dela,
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    if(station.isStopValid(departureTime, time, inverted)) {
                        break;
                    } else {
                        return false;
                    }
                }
            }

            lastTime = time;
        }
        if(invertFlag) {
            stationAndTimesString.invert();
        }

        //se a sequencia de estaçoes não segue as da linha, return false
        return !stationAndTimesIt.hasNext();
    }

}
