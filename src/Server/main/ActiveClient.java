package Server.main;


import java.net.Socket;

public class ActiveClient {
    private String ID;
    private Socket socket;

    public ActiveClient(String ID, Socket socket){
        this.ID = ID;
        this.socket = socket;
    }

    public Socket getSocket(){
        return this.socket;
    }

    public String getID(){
        return this.ID;
    }

}
