package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.CommunicationThread;
import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.Utilities;


public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket socket = null;


    public ServerThread(int port) {
        this.port = port;


        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public ServerSocket getServerSocket() {
        return socket;
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Utilities.debug("SERVER", "Waiting for a client invocation...");
                Socket client = socket.accept();
                if (client != null) {
                    CommunicationThread commThread = new CommunicationThread(client, this);
                    Utilities.debug("SERVER", "New connection from " + client.getInetAddress());
                    commThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        interrupt();

        if(getServerSocket() != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
