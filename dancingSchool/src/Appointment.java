import java.io.Serializable;

public class Appointment implements Serializable {
    private Long id;
    private Long dance_class_id;
    private Long user_id;

    public Appointment() {}

    public Appointment(Long id, Long dance_class_id, Long user_id) {
        this.id = id;
        this.dance_class_id = dance_class_id;
        this.user_id = user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDance_class_id() {
        return dance_class_id;
    }

    public void setDance_class_id(Long dance_class_id) {
        this.dance_class_id = dance_class_id;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", dance_class_id=" + dance_class_id +
                ", user_id=" + user_id +
                '}';
    }
}
