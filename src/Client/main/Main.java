package Client.main;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import universalClasses.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {
    private static String currentChat;
    private static String userID;
    private static Socket socket;
    private static ArrayList<String> chatIDs = new ArrayList<String>();
    private static ArrayList<ArrayList<Message>> chatlogs = new ArrayList<ArrayList<Message>>();
    private static String serverAdr = "127.0.0.1";
    private static int serverPort = 1102;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static Parent root;
    private static Parent loginScene;
    private static Parent newChatScene;
    private static Stage primaryWindow;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryWindow = primaryStage;
        root = FXMLLoader.load(getClass().getResource("StructureRoot.fxml"));
        loginScene = FXMLLoader.load(getClass().getResource("StructureLogin.fxml"));
        newChatScene = FXMLLoader.load(getClass().getResource("StructureNCS.fxml"));
        primaryWindow.setTitle("Messenger+++");

        //Først skal der oprettes forbindelse til serveren
        //åben ListenerThread

        try {
            socket = new Socket(serverAdr, serverPort);
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }
        catch (Exception e) {
            System.out.println("No connection to server! Is it running?");
        }

        primaryWindow.setOnCloseRequest(e ->{
            //deleteLog();

        });
        primaryWindow.setScene(new Scene(loginScene, 600, 475));
        root.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        loginScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        newChatScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryWindow.show();

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


    public static void getLogFromServer() throws IOException {
        System.out.println("Venter på type...");
        String input = br.readLine();
        if (input.startsWith("ChatLogs§")) {
            System.out.println("Fik " + input);
            chatlogs = new ArrayList<>();
            int chatLogIndex = 0;

            Boolean hasMoreChats = true;
            while (hasMoreChats) {
                String chatID = br.readLine();
                System.out.println(chatID);
                if (chatID.equals("§")) {
                    System.out.println("No more chats");
                    hasMoreChats = false;
                } else {
                    chatIDs.add(chatID);
                    chatlogs.add(new ArrayList<Message>());
                    Boolean hasMoreMessages = true;
                    while (hasMoreMessages) {
                        String message = br.readLine();
                        System.out.println(message);
                        if (message.equals("§")) {
                            System.out.println("No more messages");
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

    public static Parent getRoot() {
        return root;
    }

    public static void addMessage(Message message){
        chatlogs.get(chatIDs.indexOf(message.samtaleID)).add(message);
    }

    public static String getCurrentChat(){
        if (currentChat == null && chatIDs.get(0) != null) {
            currentChat = chatIDs.get(0);
        }
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

    public static Stage getPrimaryWindow() {
        return primaryWindow;
    }

    public static BufferedReader getBr() {
        return br;
    }

    public static PrintWriter getPw() {
        return pw;
    }

    public static void setUserID(String userID) {
        Main.userID = userID;
    }


    public static Parent getNewChatScene() {
        return newChatScene;
    }
}

