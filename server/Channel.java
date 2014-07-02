package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * User: mtolstykh
 * Date: 23.08.13
 * Time: 0:07
 */
public class Channel extends Thread {

    BufferedReader in = null;
    Socket socket;
    OutputStream out;

    public Channel(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            System.out.println(socket.getInetAddress().getHostAddress());
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String input;

            while ((input = in.readLine()) != null) {
                System.out.println(input);
                int i = Integer.parseInt(input); 
                /**
                * room processing
                */              
                in.close();
                out.close();
                socket.close();
                break;                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
