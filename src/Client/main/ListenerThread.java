package Client.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import javafx.application.Platform;
import universalClasses.Message;
import universalClasses.TimeStamp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ListenerThread extends Thread {
    public boolean threadOK;
    private Socket socket;
    //private PrintWriter pw;
    private BufferedReader br;
    private boolean done;
    private String input;
    private String ChatID;
    private String Clients;
    private String message;

    public ListenerThread(Socket socket) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(100000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run() {

        // Setup done, start main listener
        while (true) {
            //Håndtering af motagelse af besked
            try {
                input = br.readLine();
                System.out.println("INPUT:  " + input);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (Exception ex1) {
                    return;
                }
                return;
            }
            // Udnyt Arrayet af beskeder
            //Message New = new Message(Input);
            if (input != null) {
                if (input.startsWith("NewChat§")) {
                    // Informere om der er kommet en ny chat.

                    // TYPE
                    // Clients
                    // END

                    // Vis i layout, om den nye chat
                    Platform.runLater(() -> {
                        // kald metoden her
                        Main.newChat(Message.toMessage(input.substring(8)).samtaleID);
                    });
                } else {
                    if (input.startsWith("Message§")) {
                        //Modtagelse af besked
                        //Send besked til Layout
                        Platform.runLater(() -> Main.addMessage(Message.toMessage(input)));
                    } else if (input.startsWith("GetUsers§")) {
                        System.out.println(input);

                        Platform.runLater(() -> newChat.makeChat(input.substring(9)));

                    }
                }
            }
            else {
                System.out.println("Got null from input! Assuming socket is dead...");
                return;
            }
        }
    }
}