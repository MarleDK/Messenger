package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionThread extends Thread{
    boolean threadOK;
    private Socket socket;
    //private PrintWriter pw;
    private BufferedReader br;
    private String ID;
    private String Input;

    public ClientConnectionThread(Socket socket, String ID) {
        try {
            this.socket = socket;
            this.socket.setSoTimeout(5000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ID = ID;

            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run(){

        try {
            Input = br.readLine();
        }
        catch (Exception ex) {
            try {
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
            catch (Exception ex1) {
                return;
            }
        }

        System.out.println(Input);
    }

}
