package Server.main;

//Husk at tråden ikke skal kunne køre i bagggrunden, hvis fx. socket er død.

public class ClientConnectionThread extends Thread{
    boolean threadOK;
    String ID;

    public ClientConnectionThread() {
        this.threadOK = true;
    }

    public void run(){

    }
}
