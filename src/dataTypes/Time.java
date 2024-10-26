package dataTypes;

public class Time implements Comparable<Time> {
    public static final int MIN_HOURS = 0;
    public static final int MAX_HOURS = 23;
    public static final int MIN_MINUTES = 0;
    public static final int MAX_MINUTES = 59;

    private int hours = 0;
    private int minutes = 0;

    public Time(int hours, int minutes) {
        if((MIN_HOURS <= hours) && (hours <= MAX_HOURS)) {
            this.hours = hours;
        }

        if((MIN_MINUTES <= minutes) && (minutes <= MAX_MINUTES)) {
            this.minutes = minutes;
        }
    }

    public Time(String timeAsString) {
        String[] splitString = timeAsString.split(":");
        int hours = Integer.parseInt(splitString[0]);
        int minutes = Integer.parseInt(splitString[1]);
        
        if((MIN_HOURS <= hours) && (hours <= MAX_HOURS)) {
            this.hours = hours;
        }

        if((MIN_MINUTES <= minutes) && (minutes <= MAX_MINUTES)) {
            this.minutes = minutes;
        }
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int compareTo(Time other) {
        if(this.hours < other.hours) {
            return -1;
        }
        else if(this.hours > other.hours) {
            return 1;
        } else {
            if(this.minutes < other.minutes) {
                return -1;
            }
            else if(this.minutes > other.minutes) {
                return 1;
            }
        }
        return 0;
    }
}
