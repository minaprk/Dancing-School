import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1999);
            while (true) {
                System.out.println("waiting for client...");
                Socket socket = ss.accept();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                System.out.println("client connected: " + socket.getInetAddress().getHostAddress() + " ( " + dateFormat.format(cal.getTime()) + " )");
                ClientHandler ch = new ClientHandler(socket);
                ch.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}