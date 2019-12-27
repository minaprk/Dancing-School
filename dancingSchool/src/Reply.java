import java.io.Serializable;
import java.util.ArrayList;

public class Reply implements Serializable {
    private String code;
    private ArrayList<Choreographer> chs = null;

    public ArrayList<Style> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<Style> styles) {
        this.styles = styles;
    }

    public ArrayList<DanceClass> getDanceClasses() {
        return danceClasses;
    }

    public void setDanceClasses(ArrayList<DanceClass> danceClasses) {
        this.danceClasses = danceClasses;
    }

    public ArrayList<Appointment> getApps() {
        return apps;
    }

    public void setApps(ArrayList<Appointment> apps) {
        this.apps = apps;
    }

    public ArrayList<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(ArrayList<Membership> memberships) {
        this.memberships = memberships;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    private ArrayList<Style> styles = null;
    private ArrayList<DanceClass> danceClasses = null;
    private ArrayList<Appointment> apps = null;
    private ArrayList<Membership> memberships = null;
    private ArrayList<User> users = null;

    public void addChoreographer(Choreographer ch){
        chs.add(ch);
    }

    public void addStyle(Style s){styles.add(s);}

    public void addAppointment(Appointment app){apps.add(app);}

    public void addUser(User u){users.add(u);}

    public void addMembership(Membership m){memberships.add(m);}

    public void addClass(DanceClass dc){danceClasses.add(dc);}

    public Reply() {
        chs = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Choreographer> getChs() {
        return chs;
    }

    public void setChs(ArrayList<Choreographer> chs) {
        this.chs = chs;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "code='" + code + '\'' +
                ", cars=" + chs +
                '}';
    }
}
