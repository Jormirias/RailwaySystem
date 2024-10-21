/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.DoubleList;
import dataStructures.Iterator;

/**
 * Class which implements a Rail Network
 */
public class Network {
    /*Should this be Dictionaries as their identifier is only their name?*/
    private DoubleList<Line> lines;

    // maybe not worth it yet as we do not have all the tools available
    // private Station[] stations;

    public Network()
    {
        this.lines = new DoubleList<Line>();
    }

    /**
     * Add new Line to Network
     */
    public void insertLine(String lineName, DoubleList<String> stationNames) {
        Line line = new Line(lineName, stationNames);
        this.lines.addLast(line);
    }

    /**
     * Remove Line from Network
     */
    public void removeLine(String lineName) {
        Line line = new Line(lineName);
        int pos = this.lines.find(line);
        this.lines.remove(pos);
    }

    public Iterator<String> getStationNames(String lineName) {
        Line line = new Line(lineName);
        int pos = this.lines.find(line);
        Iterator<Station> it = this.lines.get(pos).getStations();
        DoubleList<String> stationNames = new DoubleList<>();
        
        while(it.hasNext()) {
            stationNames.addLast(it.next().getName());
        }

        return stationNames.iterator();
    }

    
    /**
     * Add new Schedule to Line
     */
    public void insertSchedule(String lineName, String trainNumber, DoubleList<String> stationAndTimes) {
        int pos = this.lines.find(new Line(lineName));
        Line line = this.lines.get(pos);
        line.insertSchedule(trainNumber, stationAndTimes);
    }
    
    // private Line findLineWithName(String lineName) {
    //     Iterator<Line> it = this.lines.iterator();
    //     while(it.hasNext()) {
    //         Line next = it.next();
    //         if(next.getName().equals(lineName)) {
    //             return next;
    //         }
    //     }

    //     return null;
    // }

}
