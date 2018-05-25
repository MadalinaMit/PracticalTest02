package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MadalinaM on 5/25/2018.
 */

public class CommunicationThread extends Thread {
    private Socket socket;
    private ServerThread serverThread;

    public CommunicationThread(Socket socket, ServerThread serverThread) {
        this.socket = socket;
        this.serverThread = serverThread;
    }

    @Override
    public void run() {
        if(socket == null) {
            Utilities.debug("COMMUNICATION", "Communication Socket is null");
            return;
        }

        try {
            BufferedReader reader = Utilities.getReader(socket);
            PrintWriter writer = Utilities.getWriter(socket);

            if(reader == null || writer == null) {
                Utilities.debug("COMMUNICATION", "Reader or writer is null");
                return;
            }

            String url = reader.readLine();
            String result = null;
            if(!url.contains("bad")) {
                result = OpenHttpConnection(url).toString();
            } else {
                result = "BAD URL!";
            }

            writer.println(result);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private InputStream OpenHttpConnection(String strURL)
            throws IOException {
        URLConnection conn = null;
        InputStream inputStream = null;
        URL url = new URL(strURL);
        conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = httpConn.getInputStream();
        }
        return inputStream;
    }
}
