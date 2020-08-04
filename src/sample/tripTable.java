package sample;

public class tripTable {
    boolean is_accepted ;
    String trip_id ;
    String payment_id ;
    int driver_id ;

    @Override
    public String toString() {
        return "tripTable{" +
                "is_accepted=" + is_accepted +
                ", trip_id='" + trip_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", driver_id=" + driver_id +
                '}';
    }

    public tripTable(boolean is_accepted, String trip_id, String payment_id, int driver_id) {
        this.is_accepted = is_accepted;
        this.trip_id = trip_id;
        this.payment_id = payment_id;
        this.driver_id = driver_id;
    }

    public boolean isIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }
}
