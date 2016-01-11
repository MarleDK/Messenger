package Server.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ServerMain {
    private static int serverPort = 1102;

    public static void main(String[] args) throws IOException {
        ServerSocket srv = null;
        try {
            srv = new ServerSocket(serverPort);
        } catch (IOException ex) {
            System.out.println("Error creating server socket");
            System.exit(0);
        }
        while (true) {
            Socket s = null;
            try {
                s = srv.accept();
                System.out.println("Client connection established");
            } catch (IOException ex) {
                System.out.println("Error accepting from socket");
                System.exit(0);
            }

            //Følgende er for at chekcke om brugeren, er en oprettet bruger

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String type = br.readLine();

            String userID;
            String passwordUsr;
            if(type.equals("ExistingUser")){
                userID = br.readLine();
                System.out.println(userID);
                passwordUsr = br.readLine();
                System.out.println(passwordUsr);
                File clientFolder = new File("serverdatabase/client");
                for(int i=0; i<clientFolder.listFiles().length; i++) {
                    if(clientFolder.listFiles()[i].getName().substring(0,userID.length()) == userID){
                        File clientFil = new File(clientFolder.listFiles()[i].getName());
                        Scanner clientFilScanner = new Scanner(clientFil);
                        String passwordSrv = clientFilScanner.nextLine();
                        clientFilScanner.close();
                        PrintWriter pw = new PrintWriter(s.getOutputStream());
                        if(passwordSrv.equals(passwordUsr)){
                            System.out.println("LoginOkay");
                            pw.println("LoginOkay");
                            pw.flush();
                            ClientConnectionThread g = new ClientConnectionThread(s, userID);
                            if (g.threadOK) {
                                g.start();
                            }
                            new ActiveClient(userID, s);
                        }else{
                            System.out.println("LoginFailed");
                            pw.println("LoginFailed");
                            pw.flush();
                        }
                    }
                }
            }
        }
    }
}

