import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.MonthDay;

public class Schedule {
    private Long id;
    private Month month;
    private DayOfWeek dayOfWeek;
    private MonthDay monthDay;
    private Time time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Schedule() {}

    public Schedule(Long id, Month month, DayOfWeek dayOfWeek, MonthDay monthDay, Time time) {
        this.id = id;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.monthDay = monthDay;
        this.time = time;
    }
    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", " + month + monthDay +
                ", " + dayOfWeek +
                ", " + ", " + time +
                '}';
    }
}

