/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.OrderedDoubleList;
import dataStructures.TwoWayIterator;
import dataTypes.interfaces.*;

/**
 * Class which implements a Train Station
 */
public class StationRegistryClass implements StationRegistry {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private final String name;

    /**
     * Collections of lines and times for the station registry to have as reference
     * STRUCT_CHOICE: We chose to have these be OrderedDoubleList, since Lines should be ordered alphabetically
     * and the TrainTimes should be ordered by Departure time.
     *
     */
    private OrderedDoubleList<String, Line> lines;

    private OrderedDoubleList<Time, TrainTime> trainTimes;

    public StationRegistryClass(String name) {
        this.name = name;
        this.lines = new OrderedDoubleList<>();
        this.trainTimes = new OrderedDoubleList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationRegistryClass otherStation = (StationRegistryClass) other;
        return this.name.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.name.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void addLine(Line line) {
        lines.insert(line.getName().toUpperCase(), line);
    }

    @Override
    public void removeLine(Line line) {
        lines.remove(line.getName().toUpperCase());
    }

    @Override
    public boolean hasLines() {
        return !lines.isEmpty();
    }

    @Override
    public Iterator<Entry<String, Line>> getLines() {
        return lines.iterator();
    }

    public void addTrainTime(Time departureTime, int train, Time time) {
        trainTimes.insert(departureTime, new TrainTimeClass(train, time) {
        }) ;
    }

    public void removeTrainTime(int train) {
        TwoWayIterator<Entry<Time, TrainTime>> trainTimesIt = trainTimesIterator();
        while(trainTimesIt.hasNext()) {
            Entry<Time, TrainTime> trainTime = trainTimesIt.next();

            //If there is a registry of a train passing in this station,
            if (trainTime.getValue().getTrain() == train) {
                //Remove that entry, and stop iterating
                trainTimes.remove(trainTime.getKey());
                break;
            }

        }
    }

    public TwoWayIterator<Entry<Time, TrainTime>> trainTimesIterator() {
        return trainTimes.iterator();
    }

}
