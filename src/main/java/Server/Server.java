package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    static final int SERVER_PORT = 2354;

    static void consoleOutput(DataOutputStream out) throws IOException {

    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)){
            try (Socket client = server.accept()) {
                System.out.println("Client connected!");
                final DataOutputStream out = new DataOutputStream(client.getOutputStream());
                try (Scanner inputClient = new Scanner(client.getInputStream())){
                    String clientMessage;
                    String serverMessage;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Scanner keyboardScanner = new Scanner(System.in);
                            String consoleMessage;
                            while (true) {
                                consoleMessage = keyboardScanner.nextLine();
                                try {
                                    out.writeUTF(consoleMessage);
                                    out.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();

                    while (!client.isClosed()) {
                        clientMessage = inputClient.nextLine();
                        System.out.println("Client: " + clientMessage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
