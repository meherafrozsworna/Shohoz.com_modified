package sample;

public class tripPromo {
    String trip_id ;

    public tripPromo(String trip_id) {
        this.trip_id = trip_id;
    }

    @Override
    public String toString() {
        return "tripPromo{" +
                "trip_id='" + trip_id + '\'' +
                '}';
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }
}
