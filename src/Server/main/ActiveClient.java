package Server.main;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ActiveClient {
    private static ArrayList<ActiveClient> ActiveClients = new ArrayList<>();
    private String ClientID;
    private Socket socket;
    private ClientConnectionThread clientThread;

    public ActiveClient(String ID, Socket socket, ClientConnectionThread clientThread){
        this.ClientID = ID;
        this.socket = socket;
        this.clientThread = clientThread;
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

    public static ClientConnectionThread getClientThread(String client) {
        for (ActiveClient activeClient : ActiveClients) {
            if (activeClient.getID().equals(client)) {
                return activeClient.getClientThread();
            }
        }
        return null;
    }

    public String getID(){
        return this.ClientID;
    }

    public static void removeActiveClient(String client){
        for (ActiveClient activeClient : ActiveClients) {
            if (activeClient.getID().equals(client)) {
                ActiveClients.remove(activeClient);
            }
        }
    }

    public static boolean ClientLogged(String client) {
        for (ActiveClient activeClient : ActiveClients) {
            if (activeClient.getID().equals(client)) {
                return true;
            }
        }
        return false;
    }


    public ClientConnectionThread getClientThread() {
        return clientThread;
    }
}
