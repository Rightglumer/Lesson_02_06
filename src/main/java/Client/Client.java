package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    static final String SERVER_NAME = "localhost";
    static final int SERVER_PORT = 2354;

    public static void main(String[] args) {
        try (final Socket socket = new Socket(SERVER_NAME, SERVER_PORT)){
            final DataInputStream in = new DataInputStream(socket.getInputStream());
            final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                        while (!socket.isClosed()) {
                            try {
                                String serverMessage = in.readUTF();
                                System.out.println("Server: " + serverMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } else{
                                break;
                            }
                        }
                    }
            }).start();
            String inputMessage = "";
            Scanner inputScanner = new Scanner(System.in);
            while (!inputMessage.equals("/quit")) {
                inputMessage = inputScanner.nextLine();
                if (!inputMessage.equals("")) {
                    out.writeUTF(inputMessage + "\n");
                    out.flush();
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
