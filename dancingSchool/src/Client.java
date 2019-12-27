import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1999);

            DancingSchool dancingSchool = new DancingSchool(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

