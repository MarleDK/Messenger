package Server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        ServerSocket srv = null;
        try {
            srv = new ServerSocket(1102);
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
            ClientConnectionThread g = new ClientConnectionThread(s, "Insert ID here");
            if (g.threadOK)
                g.start();
        }
    }
}
