import java.io.Serializable;

public class Request implements Serializable {
    private String code;
    private Choreographer ch;
    private Style s;
    private DanceClass dc;
    private User u;
    private int mem;
    private Appointment app;

    public Request() {}

    public Request(String code, Appointment app) {
        this.code = code;
        this.app = app;
    }

    public Appointment getApp() {
        return app;
    }

    public void setApp(Appointment app) {
        this.app = app;
    }

    public Request(String code) {
        this.code = code;
    }

    public Request(String code, Choreographer ch) {
        this.code = code;
        this.ch = ch;
    }
    public Request(String code, User u) {
        this.code = code;
        this.u = u;
    }
    public Request(String code, Style s) {
        this.code = code;
        this.s = s;
    }
    public Request(String code,int mem){
        this.code = code;
        this.mem = mem;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Request(String code, DanceClass dc){
        this.code = code;
        this.dc = dc;
    }

    public Style getS() {
        return s;
    }

    public int getMem() {
        return mem;
    }

    public void setMem(int mem) {
        this.mem = mem;
    }

    public void setS(Style s) {
        this.s = s;
    }

    public Choreographer getCh() {
        return ch;
    }

    public void setCh(Choreographer ch) {
        this.ch = ch;
    }

    public DanceClass getDc() {
        return dc;
    }

    public void setDc(DanceClass dc) {
        this.dc = dc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Choreographer getChoreographer() {
        return ch;
    }

    public void setChoreographer(Choreographer ch) {
        this.ch = ch;
    }

    @Override
    public String toString() {
        return "Request{" +
                "code='" + code + '\'' +
                ", car=" + ch +
                '}';
    }

}
