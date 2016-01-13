package Server.main;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

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
        for (ActiveClient activeClient : ActiveClients) {
            if (activeClient.getID().equals(client)) {
                return activeClient.getSocket();
            }
        }
        return null;
    }

    public String getID(){
        return this.ClientID;
    }

    public static void removeActiveClient(String client){
        for(int i= ActiveClients.size()-1; i>0; i--){
            if (Objects.equals(ActiveClients.get(i).getID(), client)) {
                ActiveClients.remove(i);
            }
        }
    }

    public static boolean ClientLogged(String ID) {
        return ActiveClients.contains(ID);
    }


}
