package Client.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import universalClasses.Message;

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
        String ChatID;
        String message;
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
                // Kør modtagelse af beskeder
                while (!done) {
                    try {
                        done = false;
                        File chatFile = new File("clientdatabase/log/" + ChatID + ".txt");
                        FileWriter outFile = new FileWriter(chatFile, true);
                        PrintWriter out = new PrintWriter(outFile);
                        while (!done) {
                            message = br.readLine();
                            if (message.equals("§")) {
                                done = true;
                            } else {
                                // Write into file
                            }
                        }
                        out.close();
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
                return;
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