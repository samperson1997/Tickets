package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "plans")
public class Plan implements Serializable {
    @Id
    private int id;

    private String venueId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int type;

    private String introduction;

    private int isCharged;

    public Plan() {
    }

    public Plan(int id, String venueId, LocalDateTime startTime, LocalDateTime endTime, int type, String introduction, int isCharged) {
        this.id = id;
        this.venueId = venueId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.introduction = introduction;
        this.isCharged = isCharged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIsCharged() {
        return isCharged;
    }

    public void setIsCharged(int isCharged) {
        this.isCharged = isCharged;
    }
}
