import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientHandler extends Thread {
    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        Database db = new Database();
        while (true) {
            try {
                Request req = (Request) ois.readObject();
                if (req.getCode().equals("EXIT")) {
                    oos.close();
                    ois.close();
                    break;
                }
                else if(req.getCode().equals("ADD_CLASS")) {
                    db.addClass(req.getDc());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }
                else if(req.getCode().equals("ADD_STYLE")) {
                    db.addStyle(req.getS());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }
                else if(req.getCode().equals("ADD_CHOREOGRAPHER")) {
                    db.addChoreographer(req.getCh());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }
                else if(req.getCode().equals("ADD_USER")) {
                    db.addUser(req.getU());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }else if(req.getCode().equals("ADD_APPOINTMENT")) {
                    db.addAppointment(req.getApp());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }else if(req.getCode().equals("DELETE_CLASS")) {
                    db.removeClass(req.getDc());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }
                else if(req.getCode().equals("UPDATE_USER")) {
                    db.updateUser(req.getU());

                    Reply rep = new Reply();
                    rep.setCode("SAVED");
                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_STYLES")) {
                    Reply rep = new Reply();
                    for (Style s : db.getAllStyles())
                        rep.addStyle(s);

                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_CHS")) {
                    Reply rep = new Reply();
                    for (Choreographer ch : db.getAllChoreographers())
                        rep.addChoreographer(ch);

                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_CLASSES")) {
                    Reply rep = new Reply();
                    for (DanceClass dc : db.getAllClasses())
                        rep.addClass(dc);

                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_APPOINTMENTS")) {
                    Reply rep = new Reply();
                    for (Appointment a : db.getAllAppointments())
                        rep.addAppointment(a);

                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_USERS")) {
                    Reply rep = new Reply();
                    for (User u : db.getAllUsers())
                        rep.addUser(u);

                    oos.writeObject(rep);
                }else if(req.getCode().equals("GET_MEMBERSHIPS")) {
                    Reply rep = new Reply();
                    for (Membership m : db.getAllMemberships())
                        rep.addMembership(m);

                    oos.writeObject(rep);
                }


            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println("client " + socket.getInetAddress().getHostAddress() + " disconnected!  ( " + dateFormat.format(cal.getTime()) + " )");
    }
}