package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import com.sun.deploy.util.SessionState;
import com.sun.org.apache.xpath.internal.SourceTree;
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


    public ClientConnectionThread(Socket socket) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(100000);
            pw = new PrintWriter(this.socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));


            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run() {
        try {
            boolean loginin = false;
            while (!loginin) {

                String input;
                input = br.readLine();
                String userID ="";
                String passwordUsr;
                if (input.startsWith("ExistingUser§")) {
                    int i = 13;
                    while(true){
                        if(input.charAt(i) == '§'){
                            i++;
                            break;
                        } else{
                            userID += input.charAt(i);
                            i++;
                        }
                    }
                    passwordUsr = input.substring(i);
                    System.out.println(userID);
                    System.out.println(passwordUsr);
                    File clientFolder = new File("serverdatabase/client");
                    Boolean gotUser = false;
                    for (i = 0; i < clientFolder.listFiles().length; i++) {
                        System.out.println(clientFolder.listFiles()[i].getName());
                        if (clientFolder.listFiles()[i].getName().substring(0, userID.length()).equals(userID)) {
                            System.out.println(clientFolder.listFiles()[i].getName());
                            File clientFil = new File("serverdatabase/client/" + clientFolder.listFiles()[i].getName());
                            Scanner clientFilScanner = new Scanner(clientFil);
                            String passwordSrv = clientFilScanner.nextLine();
                            clientFilScanner.close();
                            PrintWriter pw = new PrintWriter(socket.getOutputStream());
                            if (passwordSrv.equals(passwordUsr)) {
                                if (ActiveClient.ClientLogged(userID)) {
                                    break;
                                } else {
                                    System.out.println("LoginOkay");
                                    pw.println("LoginOkay");
                                    pw.flush();
                                    loginin = true;
                                    this.ClientID = userID;
                                    new ActiveClient(userID, socket);
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                    }
                    if(!loginin) {
                        System.out.println("LoginFailed");
                        pw.println("LoginFailed");
                        pw.flush();
                    }
                }
            }
        } catch (IOException ex) {
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

        // Setup
        // Show Online
        //pw.println("Bob§James§Kagemand");

        System.out.println("Run Setup! Sender liste...");
        // Show History (chat messsages)
        pw.println("ChatLogs§");
        pw.flush();



        System.out.println("Indlæser chat history...");
        for (String s : ChatDatabase.GetChatIDs(this.ClientID)) {
            File chatFile = new File("serverdatabase/chat/" + s);
            System.out.println("Sender ChatID! " + s);
            pw.println(s);
            pw.flush();
            try {
                Scanner sc = new Scanner(chatFile);
                System.out.println("Sender Beskeder!");
                while (sc.hasNextLine()){
                    pw.println(sc.nextLine());
                    pw.flush();
                }
                sc.close();
            }
            catch (Exception ex) {
                System.out.println("Failed reading file:" + "Server/database/chat/" + s);
                System.out.println(ex.getMessage());
                try {
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (Exception ex1) {
                    break;
                }
                break;
            }
            System.out.println("Færdig sende beskeder!");
            pw.println("§");
            pw.flush();
        }

        pw.println("§");
        pw.flush();

        System.out.println("Setup færdiggjort! Venter på client...");

        while (true) {
            String input;
            try {
                input = br.readLine();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                } catch (Exception ex1) {
                    break;
                }
                break;
            }
            if(input.startsWith("NewChat§")) {
                System.out.println("Making new chat");
                // En client vil gerne oprette en ny chat

                // TYPE
                // Clients
                // END
                String clientInput = ChatDatabase.makeChat(input);

                ArrayList<String> Clients;
                Clients = ChatDatabase.getClients(clientInput);
                ArrayList<Socket> Sockets = new ArrayList<>();
                if (Clients != null) {
                    for (String Client : Clients) {
                        Sockets.add(ActiveClient.getSocket(Client));
                    }
                    for (int i = Sockets.size(); 0 < i; i--) {
                        if (Sockets.get(i) == null) {
                            Sockets.remove(i);
                        }
                        try {
                            PrintWriter pw = new PrintWriter(Sockets.get(i).getOutputStream());
                            pw.println("NewChat§" + clientInput);
                            pw.flush();
                            pw.close();
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            try {
                                socket.shutdownOutput();
                                socket.shutdownInput();
                                socket.close();
                            } catch (Exception ex1) {
                                break;
                            }
                            break;
                        }
                    }
                } else {
                    System.out.println("No clients available to forward to!");
                }

            } else if(input.startsWith("Message§")) {
                // En besked er kommet, videresend det!

                // TYPE
                // Beskeden
                // END

                Message message = Message.toMessage(input);
                ChatDatabase.logMessage(message);

                ArrayList<String> Clients;
                if (message != null) {
                    Clients = ChatDatabase.getClients(message.samtaleID);
                    ArrayList<Socket> Sockets = new ArrayList<>();
                    if (Clients != null) {
                        Clients.remove(message.afsenderID);
                        for (String Client : Clients) {
                            Sockets.add(ActiveClient.getSocket(Client));
                        }
                        for (int i = Sockets.size()-1; 0 < i; i--) {
                            if (Sockets.get(i) == null) {
                                Sockets.remove(i);
                            }
                            try {
                                PrintWriter pw = new PrintWriter(Sockets.get(i).getOutputStream());
                                pw.println(message.toString());
                                pw.flush();
                                pw.close();
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                try {
                                    socket.shutdownOutput();
                                    socket.shutdownInput();
                                    socket.close();
                                } catch (Exception ex1) {
                                    break;
                                }
                                break;
                            }
                        }
                    } else {
                        System.out.println("No clients available to forward to!");
                    }
                }
                else {
                    System.out.println("SamtaleID not found!");
                }
            } else if(input.startsWith("GetUsers§")) {
                System.out.println("Sending users");
                System.out.println(ClientDatabase.getClients());
                pw.println(ClientDatabase.getClients());
                pw.flush();
            }
        }
        System.out.println("Removing client..." + this.ClientID);
        ActiveClient.removeActiveClient(this.ClientID);
    }
}


