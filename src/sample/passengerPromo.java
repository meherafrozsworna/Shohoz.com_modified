package sample;

public class passengerPromo {
    String name ;
    String address ;

    public passengerPromo(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "passengerPromo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
