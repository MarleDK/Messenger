package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import com.sun.deploy.util.SessionState;
import jdk.net.Sockets;
import org.omg.PortableInterceptor.ACTIVE;
import universalClasses.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientConnectionThread extends Thread{
    public boolean threadOK;
    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private String ClientID;
    private int CurrentLine;
    private boolean done;
    private ArrayList<String> Inputs;

    public ClientConnectionThread(Socket socket, String ID) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(50000);
            pw = new PrintWriter(this.socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ClientID = ID;

            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run() {

        // Setup
        // Show Online
        //pw.write("Bob§James§Kagemand");
        
        // Show History (chat messsages)
        pw.write(ChatDatabase.toString(ChatDatabase.GetChatIDs(this.ClientID)));
        pw.flush();

        for (String s : ChatDatabase.GetChatIDs(this.ClientID)) {
            File chatFile = new File("serverdatabase/chat/" + s + ".txt");
            pw.write(s);
            pw.flush();
            try {
                Scanner sc = new Scanner(chatFile);
                while (sc.hasNextLine()){
                    pw.println(sc.nextLine());
                    pw.flush();
                }
                sc.close();
            }
            catch (Exception ex) {
                System.out.println("Failed logging file:" + "Server/database/chat/" + s + ".txt");
            }
            pw.write("§");
            pw.flush();
        }

        pw.write("§");
        pw.flush();

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
                return;
            }
            switch (Inputs.get(0)) {
                case "NewChat":
                    // En client vil gerne oprette en ny chat

                    // TYPE
                    // Clients
                    // END

                    ArrayList<String> clients = new ArrayList<>();
                    int lastindex = 0;
                    for (int i = 0; i < Inputs.get(1).length(); i++) {
                        if (Inputs.get(1).charAt(i) == '§') {
                            clients.add(Inputs.get(1).substring(lastindex, i));
                            lastindex = i + 1;
                        }
                    }
                    ChatDatabase.makeChat(clients);
                    break;
                case "Message":
                    // En besked er kommet, videresend det!

                    // TYPE
                    // TimeStamp
                    // SamtaleID
                    // AfsenderID
                    // Beskeden
                    // END

                    Message message = new Message(Inputs.get(1), Inputs.get(2), Inputs.get(3));
                    //ClientDatabase.logMessage(message);

                    ArrayList<String> Clients;
                    Clients = ChatDatabase.getClients(message.samtaleID);
                    ArrayList<Socket> Sockets = new ArrayList<>();
                    Clients.remove(message.afsenderID);
                    if (Clients != null) {
                        for (String Client : Clients) {
                            Sockets.add(ActiveClient.getSocket(Client));
                        }
                        for (int i = Sockets.size(); 0 < i;i--) {
                            if (Sockets.get(i) == null) {
                                Sockets.remove(i);
                            }
                            try {
                                PrintWriter pw = new PrintWriter(Sockets.get(i).getOutputStream());
                                pw.write(message.toString());
                                pw.flush();
                                pw.close();
                            }
                            catch (Exception ex) {
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
                    }
                    else {
                        System.out.println("No clients available to forward to!");
                    }
                    break;
            }

        }
    }

}
