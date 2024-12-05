package dataTypes.interfaces;

import java.io.Serializable;

public interface Train extends Serializable {
    public int getNumber();

    public Time getDepartureTime();

    public boolean departsBefore(Time time);

    public boolean departsAfter(Time time);

    public void setAsStop(Station station);

    public boolean stopsAt(Station station);
}
