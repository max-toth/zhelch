package client;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: mtolstykh
 * Date: 23.08.13
 * Time: 1:00
 */
public class Client {

    private static final String EXIT = "exit";
    private static final String command_bye = "bye";
    public static final String default_token = "0x-24f";
    public static final String command_create_game = "create_game";
    public static final String backend = "10.0.102.53";
    public static final int port = 8787;

    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket socket = null;

    public static void main(String[] args) {    
        if (args.length == 0) {
            System.out.println("Usage: zhelch <server>");
        }
        run(System.in);
    }

    private Client() {
        try {
            socket = new Socket(backend, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread t = new Thread() {

                @Override
                public void run() {
                    String fromServer;
                    try {
                        do {
                            fromServer = in.readLine();

                            System.out.println(fromServer);
                            if (fromServer.equals(EXIT)) {
                                break;
                            }
                        } while (fromServer != null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        out.close();
        in.close();
        socket.close();
    }
    
    public static void run(InputStream is) {

        final Client client = new Client();

        String input;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(is));

        try {
            while ((input = stdin.readLine()) != null) {
                client.out.println(input);
                client.out.flush();
            }
            stdin.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
