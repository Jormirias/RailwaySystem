package dataTypes.interfaces;

public interface Train {
    public int getNumber();

    public boolean departsBefore(Time time);

    public boolean departsAfter(Time time);

    public void setAsStop(Station station);

    public boolean stopsAt(Station station);
}
