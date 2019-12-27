import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection conn;

    public Database(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dancing_school?useUnicode=true&serverTimezone=UTC", "root", "");
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Membership> getAllMemberships(){
        ArrayList<Membership> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from memberships");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("membership_id");
                String type = rs.getString("type");
                int price = rs.getInt("price");

                list.add(new Membership(id, type, price));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public User signIn(String login, String password) {
        ArrayList <User> users = getAllUsers();
        for (User u : users) {
            if(u.getLogin().equals(login) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    public ArrayList<Appointment> getAllAppointments(){
        ArrayList<Appointment> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from appointments");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("appointment_id");
                Long class_id = rs.getLong("class_id");
                Long user_id = rs.getLong("user_id");

                list.add(new Appointment(id, class_id, user_id));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<DanceClass> getAllClasses(){
        ArrayList<DanceClass> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from classes");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("class_id");
                String month = rs.getString("month");
                int date = rs.getInt("date");
                String time = rs.getString("time");
                Long style_id = rs.getLong("style_id");
                Long ch_id = rs.getLong("choreographer_id");

                list.add(new DanceClass(id, month, date, time, style_id, ch_id));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Style> getAllStyles(){
        ArrayList<Style> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from styles");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("style_id");
                String name = rs.getString("name");

                list.add(new Style(id, name));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Choreographer> getAllChoreographers(){
        ArrayList<Choreographer> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from choreographers");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("choreographer_id");
                String name = rs.getString("name");
                Long style_id = rs.getLong("style_id");
                int experience = rs.getInt("experience");

                list.add(new Choreographer(id, name, style_id, experience));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateUser(User u) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE users set mem_count = ? where user_id = ?");
        ps.setInt(1, u.getMem_count());
        ps.setLong(2,u.getId());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("user_id");
                String name = rs.getString("name");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                int mem_count = rs.getInt("mem_count");


                list.add(new User(id, name, login, password, email, phoneNumber,mem_count));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addAppointment(Appointment a){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO appointments (appointment_id, class_id, user_id) VALUES(NULL, ?, ?)");
            ps.setLong(1, a.getDance_class_id());
            ps.setLong(2, a.getUser_id());


            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addClass(DanceClass dc){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO classes (class_id, month, date, time, style_id, choreographer_id) VALUES(NULL, ?, ?, ?, ?, ?)");
            ps.setString(1, dc.getMonth());
            ps.setInt(2, dc.getDate());
            ps.setString(3, dc.getTime());
            ps.setLong(4, dc.getStyle_id());
            ps.setLong(5, dc.getCh_id());


            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addStyle(Style style){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO styles (style_id, name) VALUES(NULL, ?)");
            ps.setString(1, style.getName());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addUser(User u){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (user_id, name, login, password, email, phoneNumber, mem_count) VALUES(NULL, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, u.getName());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhoneNumber());
            ps.setInt(6,0);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addChoreographer(Choreographer ch){
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO choreographers (choreographer_id, name, style_id, experience) VALUES(NULL, ?, ?, ?)");
            ps.setString(1, ch.getName());
            ps.setLong(2, ch.getStyle_id());
            ps.setInt(3, ch.getExperience());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void removeChoreographer(Long id){
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM choreographers where choreographer_id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void removeClass(DanceClass dc) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE * FROM choreographers where choreographer_id = ?");
            ps.setLong(1, dc.getId());
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
