package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import universalClasses.Message;

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
    private ArrayList<String> Inputs;

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
                Inputs = new ArrayList<>();
                done = false;
                CurrentLine = 0;
                while (!done) {
                    Inputs.add(br.readLine());
                    if (Inputs.get(CurrentLine).equals("§")) {
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
            switch (Inputs.get(0)) {
                case "NEW":

                    break;
                case "Message":
                    // TYPE
                    // TimeStamp
                    // AfsenderID
                    // Beskeden
                    // END
                    Message message = new Message(Inputs.get(1), Inputs.get(2), Inputs.get(3));
                    break;
            }
            //Message Message = new Message();
            // Construct message

            // Udnyt Input efter modtagelse

            //ChatDatabase.getClients();

            //Get sockets from Clients

            //Create pw from sockets

            //toString message and send to pw

        }
    }

}
