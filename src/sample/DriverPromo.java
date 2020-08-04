package sample;

public class DriverPromo {
    String name ;
    int driver_id ;

    public DriverPromo(String name, int driver_id) {
        this.name = name;
        this.driver_id = driver_id;
    }

    @Override
    public String toString() {
        return "DriverPromo{" +
                "name='" + name + '\'' +
                ", driver_id=" + driver_id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }
}
