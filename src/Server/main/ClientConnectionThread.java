package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import com.sun.deploy.util.SessionState;
import jdk.net.Sockets;
import org.omg.PortableInterceptor.ACTIVE;
import universalClasses.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

        // pw.write("Bob§James§Kagemand");
        // pw.write(ChatDatabase.GetChatIDs(this.ClientID));
        // pw.flush();

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
                    Clients = ChatDatabase.getClients(Inputs.get(2));
                    ArrayList<Socket> Sockets = new ArrayList<>();
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
                                System.out.println("Error in forwarding message!");
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
