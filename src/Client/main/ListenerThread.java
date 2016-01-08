package Client.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import universalClasses.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ListenerThread extends Thread {
    public boolean threadOK;
    private Socket socket;
    //private PrintWriter pw;
    private BufferedReader br;
    private int CurrentLine;
    private boolean done;
    private ArrayList<String> Inputs;

    public ListenerThread(Socket socket) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(50000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run() {
        while (true) {
            //Håndtering af motagelse af besked
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
            // Udnyt Arrayet af beskeder
            //Message New = new Message(Input);
            switch (Inputs.get(0)) {
                case "NewChat":
                    // Informere om der er kommet en ny chat.

                    // TYPE
                    // Clients
                    // END

                    // Vis i layout, om den nye chat

                    break;
                case "Message":
                    //Modtagelse af besked

                    //Send besked til Layout

                    //Gem i log?
            }
        }
    }
}