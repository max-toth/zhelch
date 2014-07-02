package server;

import com.unlocked.server.Channel;
import sun.rmi.rmic.iiop.ClassPathLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Tcp {

    private static Properties properties;
    private static final String APP_PROPS = "application.properties";
    private static final int CAPACITY = 3;
    private static Tcp tcp;
    private static ServerSocket socket;

    static {
        properties = new Properties();
        try {

            properties.load(Tcp.class.getClassLoader().getResourceAsStream(APP_PROPS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<TcpUnit> threads;

    public List<TcpUnit> getThreads() {
        return threads;
    }

    public void setThreads(List<TcpUnit> threads) {
        this.threads = threads;
    }

    public Tcp() {

        try {
            socket = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        threads = new ArrayList<TcpUnit>(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            TcpUnit unit = new TcpUnit(socket);
            unit.setName("thread_unit_" + (i + 1));
            threads.add(unit);
        }
    }

    public static void run() {
        tcp = new Tcp();
        for (Thread unit : tcp.getThreads()) {
            try {
                unit.start();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start " + unit.getName());
        }
    }

    public static void shutdown() {
        for (TcpUnit unit : tcp.getThreads()) {
            unit.shutdown();
        }
    }

    public static void main(String[] args) {
        Tcp.run();
    }
}
