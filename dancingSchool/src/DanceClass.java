import java.io.Serializable;
import java.util.Date;

public class DanceClass implements Serializable {
    private Long id;
    private String month;
    private int date;
    private String time;
    private Long style_id;
    private Long ch_id;

    public DanceClass() {}

    public DanceClass(Long id, String month, int date, String time, Long style_id, Long ch_id) {
        this.id = id;
        this.month = month;
        this.date = date;
        this.time = time;
        this.style_id = style_id;
        this.ch_id = ch_id;
    }

    public Long getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getStyle_id() {
        return style_id;
    }

    public void setStyle_id(Long style_id) {
        this.style_id = style_id;
    }

    public Long getCh_id() {
        return ch_id;
    }

    public void setCh_id(Long ch_id) {
        this.ch_id = ch_id;
    }

    @Override
    public String toString() {
        return "DanceClass{" +
                "id=" + id +
                ", month='" + month + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", style_id=" + style_id +
                ", ch_id=" + ch_id +
                '}';
    }
}
