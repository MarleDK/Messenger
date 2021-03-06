package Client.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import javafx.application.Platform;
import universalClasses.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListenerThread extends Thread {
    private Socket socket;
    private BufferedReader br;
    private String input;

    public ListenerThread(Socket socket) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(100000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


        } catch (Exception ex) {
            System.out.println("Error creating socket on ListenerThread");
        }
    }

    public void run() {

        // Setup done, start main listener
        while (true) {
            //Håndtering af motagelse af besked
            try {
                input = br.readLine();
                System.out.println("INPUT:  " + input);

            } catch (java.io.IOException ex) {
                System.out.println(ex.getMessage());
                try {
                    Main.getPw().println("Quit§Error");
                    Main.getPw().flush();
                    Main.getPw().close();
                    br.close();
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (java.io.IOException ex1) {
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
                        System.out.println(input.substring(8));
                        Main.newChat(input.substring(8));

                    });
                } else if (input.startsWith("Message§")) {
                    //Modtagelse af besked
                    //Send besked til Layout
                    Platform.runLater(() -> Main.addMessage(Message.toMessage(input)));
                } else if (input.startsWith("GetUsers§")) {
                    System.out.println(input);

                    Platform.runLater(() -> newChat.makeChat(input.substring(9)));


                }
            } else {
                System.out.println("Got null from input! Assuming socket is dead...");
                break;
            }
        }
    }
}