package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import universalClasses.Message;
import universalClasses.TimeStamp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private static String currentChat;
    private static String userID = "Jakob";
    private static String password = "RydhofKage";
    private static Socket socket;
    private static ArrayList<String> chatIDs;
    private static ArrayList<ArrayList<Message>> chatlogs;
    private static String serverAdr = "127.0.0.1";
    private static int serverPort = 1102;
    private static PrintWriter pw;
    private static BufferedReader br;

    @Override
    public void start(Stage primaryWindow) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Structure.fxml"));
        primaryWindow.setTitle("Messenger+++");

        //Først skal der oprettes forbindelse til serveren
        //åben ListenerThread

        try {
            socket = new Socket(serverAdr, serverPort);
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw.println("ExistingUser");
            pw.println(userID);
            pw.println(password);
            pw.flush();
            if(br.readLine().equals("LoginFailed")){
                Alert loginFail = new Alert(Alert.AlertType.INFORMATION);
                loginFail.setHeaderText("Log ind Fejlede programmet lukker");
                loginFail.showAndWait();
                primaryWindow.close();
            }

            getLogFromServer();
            Thread listener = new ListenerThread(socket);
            listener.start();

            primaryWindow.setOnCloseRequest(e ->{
                //deleteLog();

            });
            primaryWindow.setScene(new Scene(root, 600, 475));
            root.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
            primaryWindow.show();
        }
        catch (Exception e) {
            System.out.println("No connection to server! Is it running?");
        }


        // Listener Thread



    }


    public static void main(String[] args) {
        launch(args);
    }

    //Denne metode bør ikke være nødvendig mere.
    private static void deleteLog(){
        File folder = new File("clientdatabase/log");
        for(int i=0; i<folder.listFiles().length; i++) {
            folder.listFiles()[i].delete();
        }
    }
    //Hej

    private static void getLogFromServer() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Venter på type...");
        String input = br.readLine();
        if (input.startsWith("ChatLogs")) {
            System.out.println("Fik " + input);
            chatlogs = new ArrayList<>();
            int chatLogIndex = 0;

            Boolean hasMoreChats = true;
            while (hasMoreChats) {
                String chatID = br.readLine();
                if (chatID.equals("§")) {
                    hasMoreChats = false;
                } else {
                    chatIDs.add(chatID);
                    chatlogs.add(new ArrayList<Message>());
                    Boolean hasMoreMessages = true;
                    while (hasMoreMessages) {
                        if (br.readLine().equals("§")) {
                            hasMoreMessages = false;
                        } else {
                            chatlogs.get(chatLogIndex).add(Message.toMessage(br.readLine()));
                        }
                    }
                }
                chatLogIndex++;
            }
            System.out.println("Afsluttede ChatLogs");
        }
        else {
            System.out.println("Should have gotten ChatLogs... but got " + input);
        }

    }

    public static void makeChat(String clients){
        Alert makeChatWindow = new Alert(Alert.AlertType.CONFIRMATION);
        makeChatWindow.setHeaderText("Choose users for chat");
        ListView users = new ListView();
        String client ="";
        for(int i=0; i<clients.length(); i++){
            if(clients.charAt(i) != '§'){
                client += clients.charAt(i);
            } else if(clients.charAt(i) == '§'){
                users.getItems().add(client);
                client ="";
            }
        }

    }

    public static void addMessage(Message message){
        chatlogs.get(chatIDs.indexOf(message.samtaleID)).add(message);
    }

    public static String getCurrentChat(){
        return currentChat;
    }

    public static String getUserID() {
        return userID;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void newChat (String IDs) {
        chatIDs.add(IDs);
        chatlogs.add(new ArrayList<Message>());
        //
    }

    public static BufferedReader getBr() {
        return br;
    }

    public static PrintWriter getPw() {
        return pw;
    }
}

