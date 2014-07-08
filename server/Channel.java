package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: mtolstykh
 * Date: 23.08.13
 * Time: 0:07
 */
public class Channel extends Thread {

    private static final Logger logger = Logger.getGlobal();
    BufferedReader in = null;
    Socket socket;
    OutputStream out;

    public Channel(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list();

            String input;

            while ((input = in.readLine()) != null) {
                if (input.startsWith(Cmd.join)){
                    String[] split = input.split(" ");
                    int roomId = Integer.parseInt(split[1]);
                    TcpUnit tcpUnit = Tcp.root.getThreads().get(roomId);
                    Channel ch = this;
                    tcpUnit.getThreads().add(ch);
                    Tcp.lobby.remove(roomId);
                    out.write(("[" + tcpUnit.getName() + "]\n").getBytes());
                    out.flush();
                }
                if (input.startsWith("/list"))
                    list();

                logger.log(Level.SEVERE, input);
                out.write((input + "\n").getBytes());
                out.flush();
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void list() throws IOException {
        out.write("Available rooms\n".getBytes());
        out.flush();
        for (TcpUnit unit : Tcp.root.getThreads())
            out.write(("\t" + unit.getName() + "(" +unit.getThreads().size() + "/" + TcpUnit.CAPACITY + ")\n").getBytes());
        out.flush();
    }
}
