import java.io.Serializable;
import java.util.ArrayList;

public class Choreographer implements Serializable {
    private Long id;
    private String name;
    private Long style_id;
    private int experience;

    public Choreographer(Long id, String name, Long style_id, int experience) {
        this.id = id;
        this.name = name;
        this.style_id = style_id;
        this.experience = experience;
    }

    public Choreographer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStyle_id() {
        return style_id;
    }

    public void setStyle_id(Long style_id) {
        this.style_id = style_id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Choreographer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", style_id=" + style_id +
                ", experience=" + experience +
                '}';
    }
}