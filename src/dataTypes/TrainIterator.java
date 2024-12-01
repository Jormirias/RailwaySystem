package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;;

public class TrainIterator implements Iterator<Entry<Time,Train>> {
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

    Entry<Time, Train> advanceAndReturn(boolean isNormal) {
        Entry<Time, Train> entryToReturn;

        if(isNormal) {
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
        }
        
        return entryToReturn;
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
                    return advanceAndReturn(true);
                } else {
                    return advanceAndReturn(false);
                }
            }

            if(timeComparsion < 0) {
                return advanceAndReturn(true);
            } else {
                return advanceAndReturn(false);
            }
        }

        if(currentReverse == null) {
            return advanceAndReturn(true);
        }

        if(currentNormal == null) {
            return advanceAndReturn(false);
        }

        throw new NoSuchElementException();
    }

    @Override
    public void rewind() {
        stopsNormalIt.rewind();
        stopsReverseIt.rewind();
    }

}
