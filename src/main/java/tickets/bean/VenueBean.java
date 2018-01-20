package tickets.bean;

public class VenueBean {

    private String venueId;

    private String name;

    private String location;

    private double account;

    private String password;

    public VenueBean() {
    }

    public VenueBean(String venueId, String name, String location, double account, String password) {
        this.venueId = venueId;
        this.name = name;
        this.location = location;
        this.account = account;
        this.password = password;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
