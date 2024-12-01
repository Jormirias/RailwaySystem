/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;
import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Train Station
 */
public class StationClass implements Station {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private final String name;
    
    // Class to help with organizing Stops per Line in this Station.
    class LineWithStops {
        private final Line line;
        private OrderedDictionary<Time, Train> stopsNormal;
        private OrderedDictionary<Time, Train> stopsReverse;

        public LineWithStops(Line line) {
            this.line = line;
            stopsNormal = new AVLTree<>();
            stopsReverse = new AVLTree<>();
        }

        public Line getLine() {
            return line;
        }

        public void addStop(Time time, Train train, boolean isInverted) {
            if(isInverted) {
                stopsReverse.insert(time, train);
            } else {
                stopsNormal.insert(time, train);
            }
        }

        public void removeStop(Time time, boolean isInverted) {
            if(isInverted) {
                stopsReverse.remove(time);
            } else {
                stopsNormal.remove(time);
            }
        }

        public Iterator<Entry<Time, Train>> getStops(boolean isInverted) {
            if(isInverted) {
                return stopsReverse.iterator();
            } else {
                return stopsNormal.iterator();
            }
        }
    
    }

    private Dictionary<String, LineWithStops> lines;
    
    public StationClass(String name) {
        this.name = name;
        this.lines = new SepChainHashTable<>();
    }

    public String getName() {
        return name;
    }

    public boolean isStopValid(String lineName, Time departureTime, Time arrivalTime, boolean isInverted) {
        LineWithStops lineWithStops = lines.find(lineName.toUpperCase());
        Iterator<Entry<Time, Train>> it = lineWithStops.getStops(isInverted);
        
        Train previousTrain = null;
        Train nextTrain = null;
        while(it.hasNext())    {
            Entry<Time, Train> next = it.next();
            nextTrain = next.getValue();
            Time nextStopTime = next.getKey();
            int timeComparsion = arrivalTime.compareTo(nextStopTime);
            if(timeComparsion == 0) { // two trains going the same direction can't be stopped at the same station at the same time.
                return false;
            }
            if(timeComparsion < 0) { // first stop which will come after the current one.
                break;
            }

            previousTrain = nextTrain;
        }

        if(previousTrain != null && previousTrain.departsAfter(departureTime)) {
            return false;
        }
        if(nextTrain != previousTrain && nextTrain.departsBefore(departureTime)) {
            return false;
        }
        
        return true;
    }

    @Override
    public void addStop(Line line, Time time, int train, boolean isInverted) {
        LineWithStops lineWithStops = lines.find(line.getName().toUpperCase());
        lineWithStops.addStop(time, train, isInverted);
    }

    @Override
    public void removeStop(Line line, Time time, boolean isInverted) {
        LineWithStops lineWithStops = lines.find(line.getName().toUpperCase());
        lineWithStops.removeStop(time, isInverted);
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationClass otherStation = (StationClass) other;
        return this.name.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.name.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return name;
    }


    public TwoWayIterator<Entry<Time, Train>> stopsIterator(boolean isInverted) {
        // if(isInverted) {
        //     return stopsReverse.iterator();
        // } else {
        //     return stopsNormal.iterator();
        // }
        return null;
    }

    // Internal class to help with listing Trains for this Station
    // This might not be worth it vs having one more collection. Look at that next()...
    class TrainIterator implements Iterator<Entry<Time,Train>> {
        private final Iterator<Entry<Time,Train>> stopsNormalIt;
        private final Iterator<Entry<Time,Train>> stopsReverseIt;

        private Entry<Time, Train> currentNormal;
        private Entry<Time, Train> currentReverse;

        public TrainIterator(Iterator<Entry<Time,Train>> stopsNormalIt, Iterator<Entry<Time,Train>> stopsReverseIt) {
            this.stopsNormalIt = stopsNormalIt;
            this.stopsReverseIt = stopsReverseIt;

            currentNormal = null;
            currentReverse = null;
            if(stopsNormalIt.hasNext()) {
                currentNormal = stopsNormalIt.next();
            }
            if(stopsReverseIt.hasNext()) {
                currentReverse = stopsReverseIt.next();
            }
        }

        @Override
        public boolean hasNext() {
            return stopsNormalIt.hasNext() || stopsReverseIt.hasNext();
        }

        @Override
        public Entry<Time, Train> next() throws NoSuchElementException {
            Entry<Time, Train> entryToReturn = null;
            if(currentNormal != null && currentReverse != null) {
                Time normalTime = currentNormal.getKey();
                Time reverseTime = currentReverse.getKey();
                int timeComparsion = normalTime.compareTo(reverseTime);
                if(timeComparsion == 0) {
                    Train normalTrain = currentNormal.getValue();
                    Train reverseTrain = currentReverse.getValue();
                    int trainComparsion = normalTrain.getTrainNumber().compareTo(reverseTrain.getTrainNumber());
                    if(trainComparsion < 0) {
                        entryToReturn = currentNormal;
                        if(stopsNormalIt.hasNext()) {
                            currentNormal = stopsNormalIt.next();
                        } else {
                            currentNormal = null;
                        }
                        return entryToReturn;
                    } else {
                        entryToReturn = currentReverse;
                        if(stopsReverseIt.hasNext()) {
                            currentReverse = stopsReverseIt.next();
                        } else {
                            currentReverse = null;
                        }
                        return entryToReturn;
                    }
                }

                if(timeComparsion < 0) {
                    entryToReturn = currentNormal;
                    if(stopsNormalIt.hasNext()) {
                        currentNormal = stopsNormalIt.next();
                    } else {
                        currentNormal = null;
                    }
                    return entryToReturn;
                } else {
                    entryToReturn = currentReverse;
                    if(stopsReverseIt.hasNext()) {
                        currentReverse = stopsReverseIt.next();
                    } else {
                        currentReverse = null;
                    }
                    return entryToReturn;
                }
            }

            if(currentReverse == null) {
                entryToReturn = currentNormal;
                if(stopsNormalIt.hasNext()) {
                    currentNormal = stopsNormalIt.next();
                } else {
                    currentNormal = null;
                }
                return entryToReturn;
            }

            if(currentNormal == null) {
                entryToReturn = currentReverse;
                if(stopsReverseIt.hasNext()) {
                    currentReverse = stopsReverseIt.next();
                } else {
                    currentReverse = null;
                }
                return entryToReturn;
            }

            throw new NoSuchElementException();
        }

        @Override
        public void rewind() {
            stopsNormalIt.rewind();
            stopsReverseIt.rewind();
        }

    }

    public Iterator<Entry<Time,Train>> getTrains(String lineName) {
        LineWithStops lineWithStops = lines.find(lineName.toUpperCase());
        return new TrainIterator(lineWithStops.getStops(false), lineWithStops.getStops(true)); // TODO: random booleans are code smell.
    }

    @Override
    public void addLine(Line line) {
        lines.insert(line.getName().toUpperCase(), new LineWithStops(line));
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
        // return lines.iterator();
        return null;
    }
    
}
