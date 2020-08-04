package sample;

public class Rideinfo {
    private String source ;
    private String destination ;
    private double distance;
    private String start_time;
    private String end_time;

    public Rideinfo(String source, String destination, double distance, String start_time, String end_time) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Rideinfo{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", distance='" + distance + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
