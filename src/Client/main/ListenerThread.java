package Client.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListenerThread extends Thread {
    public boolean threadOK;
    private Socket socket;
    //private PrintWriter pw;
    private BufferedReader br;
    private String ID;
    private String Input;

    public ListenerThread(Socket socket, String ID){
        try {
            this.socket = socket;
            this.socket.setSoTimeout(50000);
            //pw = new PrintWriter(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.ID = ID;

            this.threadOK = true;
        } catch (Exception ex) {
            this.threadOK = false;
        }
    }

    public void run(){
        //Håndtering af motagelse af besked
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
        // Udnyt Input efter modtagelse
    }
}
