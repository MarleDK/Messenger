package sample;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

import java.net.Socket;

public class ListenerThread extends Thread {
    Socket socket;

    public ListenerThread(){

    }

    public void run(){
        //Håndtering af motagelse af besked

    }
}
