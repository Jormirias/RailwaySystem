/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;

import java.io.Serializable;

/**
 * Class which implements a Rail Network
 */
public class Network implements Serializable {
    /*Should this be Dictionaries as their identifier is only their name?*/
    private final DoubleList<Line> lines;

    // maybe not worth it yet as we do not have all the tools available
    // private Station[] stations;

    public Network()
    {
        lines = new DoubleList<>();
    }

    /**
     * Add new Line to Network
     */
    public void insertLine(String lineName, ListInArray<Station> newStations) throws dataStructures.IllegalArgumentException {
        if (findLineWithName(lineName) != null){
            throw new dataStructures.IllegalArgumentException();
        }

        else{
            lines.addLast(new Line(lineName, newStations));
        }
    }

    /**
     * Remove Line from Network
     */
    public void removeLine(String lineName) throws NoSuchElementException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else
            lines.remove(line);
    }

    public ListInArray<Station> getStationNames(String lineName) throws NoSuchElementException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else
            return line.getStations();
    }

    /**
     * Add new Schedule to Line
     */
    public void insertSchedule(String lineName, String trainNumber, ListInArray<ScheduleStop> stationAndTimes) throws NoSuchElementException, dataStructures.IllegalArgumentException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else {
            line.insertSchedule(trainNumber, stationAndTimes);
        }
    }

    public Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchElementException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else {
            return line.getSchedules(departureStationName);
        }
    }
    
    private Line findLineWithName(String lineName) {
        Iterator<Line> it = lines.iterator();
        while(it.hasNext()) {
            Line next = it.next();
            if(next.getName().equals(lineName)) {
                 return next;
            }
        }

         return null;
     }

}
