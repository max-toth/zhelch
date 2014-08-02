package com.allboxx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tcp {

//    private static final Logger logger = Logger.getGlobal();

    private static final String APP_PROPS = "application.properties";
    private static final int CAPACITY = 3;
    public static Tcp root;
    private static ServerSocket socket;

    private List<TcpUnit> threads;
    public static List<Thread> lobby = new ArrayList<Thread>();

    public List<TcpUnit> getThreads() {
        return threads;
    }

    public void setThreads(List<TcpUnit> threads) {
        this.threads = threads;
    }

    public Tcp() {

        try {
            socket = new ServerSocket(23456);
        } catch (IOException e) {
            e.printStackTrace();
        }

        threads = new ArrayList<TcpUnit>(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            threads.add(new TcpUnit(socket, "Thread-TcpUnit-" + i));
        }
    }

    public static void run() {
        root = new Tcp();
        for (Thread unit : root.getThreads()) {
            try {
                unit.start();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            logger.log(Level.INFO, "start " + unit.getName());
        }


        Socket clientSocket = null;
        try {
            while (true) {
                clientSocket = socket.accept();
                Channel channel = new Channel(clientSocket);
                channel.setName("Lobby");
                Thread item = new Thread(channel);
                item.start();
                lobby.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void shutdown() {
        for (TcpUnit unit : root.getThreads()) {
            unit.shutdown();
        }
    }

    public static void main(String[] args) {
        Tcp.run();
    }
}
