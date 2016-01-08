package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionThread extends Thread{
    public boolean threadOK;
    private Socket socket;
    //private PrintWriter pw;
    private BufferedReader br;
    private String ID;
    private int CurrentLine;
    private boolean done;

    public ClientConnectionThread(Socket socket, String ID) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(50000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ID = ID;

            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run() {
        while (true) {
            try {
                ArrayList<String> Input = new ArrayList<>();
                done = false;
                CurrentLine = 0;
                while (!done) {
                    Input.add(br.readLine());
                    if (Input.get(CurrentLine).equals("§")) {
                        done = true;
                    } else {
                        CurrentLine++;
                    }
                }
            } catch (Exception ex) {
                try {
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (Exception ex1) {
                    return;
                }
            }
            // Construct message

            // Udnyt Input efter modtagelse

            //ChatDatabase.getClients();

            //Get sockets from Clients

            //Create pw from sockets

            //toString message and send to pw

        }
    }

}
