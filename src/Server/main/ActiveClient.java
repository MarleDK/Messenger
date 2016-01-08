package Server.main;


import java.net.Socket;
import java.util.ArrayList;

public class ActiveClient {
    public static ArrayList<ActiveClient> ActiveClients = new ArrayList<>();
    private String ClientID;
    private Socket socket;

    public ActiveClient(String ID, Socket socket){
        this.ClientID = ID;
        this.socket = socket;
    }

    public Socket getSocket(){
        return this.socket;
    }

    public static Socket getSocket(String Client) {
        for (int i = 0; ActiveClients.size() > i; i++) {
            if (ActiveClients.get(i).getID() == Client) {
                return ActiveClients.get(i).getSocket();
            }
        }
        return null;
    }

    public String getID(){
        return this.ClientID;
    }

}
