package sample;

import java.sql.Timestamp;

public class current_trip_insert {
    String trip_id ;
    Timestamp start_time ;
    String elapsed_time ;
    int elapsed_distance ;
    String current_location ;

    public current_trip_insert(String trip_id, Timestamp start_time,
                               String elapsed_time, int elapsed_distance, String current_location) {
        this.trip_id = trip_id;
        this.start_time = start_time;
        this.elapsed_time = elapsed_time;
        this.elapsed_distance = elapsed_distance;
        this.current_location = current_location;
    }

    @Override
    public String toString() {
        return "current_trip_insert{" +
                "trip_id='" + trip_id + '\'' +
                ", start_time=" + start_time +
                ", elapsed_time='" + elapsed_time + '\'' +
                ", elapsed_distance=" + elapsed_distance +
                ", current_location='" + current_location + '\'' +
                '}';
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public String getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(String elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public int getElapsed_distance() {
        return elapsed_distance;
    }

    public void setElapsed_distance(int elapsed_distance) {
        this.elapsed_distance = elapsed_distance;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }
}
