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
        System.out.println("Server Socket Established.");
        while (true) {
            Socket s = null;
            try {
                s = srv.accept();
                System.out.println("Client connection established");
            } catch (IOException ex) {
                System.out.println("Error accepting from socket");
                System.exit(0);
            }

            ClientConnectionThread g = new ClientConnectionThread(s);
            if (g.threadOK) {
                g.start();
            }

        }
    }
}

