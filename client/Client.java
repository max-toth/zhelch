package client;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: mtolstykh
 * Date: 23.08.13
 * Time: 1:00
 */
public class Client {

    private static final Logger logger = Logger.getGlobal();

    public static final String backend = "127.0.0.1";
//    public static final String backend = "10.0.102.53";
//    public static final int port = 8787;
    public static final int port = 23456;

    private PrintWriter out = null;
    private BufferedReader in = null;
    private Socket socket = null;

    public static void main(String[] args) {
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

                            System.out.println(">>" + fromServer);
                            if (fromServer.equals("/exit")) {
                                break;
                            }
                        } while (true);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, e.getMessage());
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
        super.finalize();
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
