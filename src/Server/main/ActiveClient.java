package Server.main;


import java.net.Socket;
import java.util.ArrayList;

public class ActiveClient {
    private static ArrayList<ActiveClient> ActiveClients = new ArrayList<>();
    private String ClientID;
    private Socket socket;

    public ActiveClient(String ID, Socket socket){
        this.ClientID = ID;
        this.socket = socket;
        ActiveClients.add(this);
    }

    public Socket getSocket(){
        return this.socket;
    }

    public static Socket getSocket(String client) {
        for (int i = 0; ActiveClients.size() > i; i++) {
            if (ActiveClients.get(i).getID() == client) {
                return ActiveClients.get(i).getSocket();
            }
        }
        return null;
    }

    public String getID(){
        return this.ClientID;
    }

    public static void removeActiveClient(String client){
        for(int i=ActiveClients.size(); i>0; i--){
            if (ActiveClients.get(i).getID() == client) {
                ActiveClients.remove(i);
            }
        }
    }


}
