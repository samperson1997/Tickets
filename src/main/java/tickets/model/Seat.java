package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "seats")
public class Seat implements Serializable {

    @Id
    private int id;

    private String venueId;

    private String name;

    private int num;

    public Seat() {
    }

    public Seat(String venueId, String name, int num) {
        this.venueId = venueId;
        this.name = name;
        this.num = num;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
