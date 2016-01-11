package Server.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
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
                passwordUsr = br.readLine();
                File clientFolder = new File("serverdatabase/client");
                for(int i=0; i<clientFolder.listFiles().length; i++) {
                    if(clientFolder.listFiles()[i].getName() == userID){
                        File clientFil = new File(clientFolder.listFiles()[i].getName());
                        Scanner clientFilScanner = new Scanner(clientFil);
                        String passwordSrv = clientFilScanner.nextLine();
                        if(passwordSrv.equals(passwordUsr)){

                        }
                    }
                }
            }

            ClientConnectionThread g = new ClientConnectionThread(s, userID);
            if (g.threadOK)
                g.start();
        }
    }
}

