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
    private int CurrentLine;
    private boolean done;
    private String input;
    private String ChatID;
    private String Clients;
    private String message;

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

        // Modtagelse af listen
        try {
            String list =  br.readLine();
        } catch (Exception ex) {
            try {
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            } catch (Exception ex1) {
                return;
            }
            return;
        }
        // Check om § eller ny samtale ID
        boolean allmessages = false;
        while (!allmessages) {
            try {
                ChatID = br.readLine();
            } catch (Exception ex) {
                try {
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (Exception ex1) {
                    return;
                }
                return;
            }
            if (!ChatID.equals("§")) {
                // Ny chat
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        Main.newChat(ChatID);
                    }
                });
                // Første linje indeholder clienter i denne chat
                try {
                    Clients = br.readLine();
                } catch (Exception ex) {
                    try {
                        socket.shutdownOutput();
                        socket.shutdownInput();
                        socket.close();
                    } catch (Exception ex1) {
                        return;
                    }
                    return;
                }
                // Vise hvilke clienter du skriver til
                // TODO

                // Kør modtagelse af beskeder
                while (!done) {
                    try {
                        done = false;
                        while (!done) {
                            message = br.readLine();
                            if (message.equals("§")) {
                                done = true;
                            } else {
                                // Write into client/layout
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        Main.addMessage(Message.toMessage(message));
                                    }
                                });

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
                        return;
                    }
                }
                // Færdig modtagelse af beskeder
            }
            else {
                allmessages = true;
            }
        }


        // Setup done, start main listener
        while (true) {
            //Håndtering af motagelse af besked
            try {
                input = br.readLine();

            } catch (Exception ex) {
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
            if(input.startsWith("NewChat")) {
                // Informere om der er kommet en ny chat.

                // TYPE
                // Clients
                // END

                // Vis i layout, om den nye chat
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        // kald metoden her
                        Main.newChat(Message.toMessage(input.substring(8)).samtaleID);
                    }
                });

            }
            else if(input.startsWith("Message")) {
                    //Modtagelse af besked
                    //Send besked til Layout
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        Main.addMessage(Message.toMessage(input));
                    }
                });
                    //Gem i log?
            }
        }
    }
}