package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import universalClasses.Message;
import universalClasses.TimeStamp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private static String currentChat;
    private static String userID;
    private static Socket socket;
    private static ArrayList<String> chatIDs;
    private static ArrayList<ArrayList<Message>> chatlogs;

    @Override
    public void start(Stage primaryWindow) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Structure.fxml"));
        primaryWindow.setTitle("Messenger+++");

        //Først skal der oprettes forbindelse til serveren
        //åben ListenerThread


        socket = new Socket();




        primaryWindow.setOnCloseRequest(e ->{
            //deleteLog();

        });
        primaryWindow.setScene(new Scene(root, 600, 475));
        root.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void deleteLog(){
        File folder = new File("clientdatabase/log");
        for(int i=0; i<folder.listFiles().length; i++) {
            folder.listFiles()[i].delete();
        }
    }


    private static void getLogFromServer() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        br.readLine();
        chatlogs = new ArrayList<>();
        int chatLogIndex = 0;

        Boolean hasMoreChats = true;
        while(hasMoreChats){
            String chatID = br.readLine();
            chatIDs.add(chatID);
            chatlogs.add(new ArrayList<Message>());

            if (chatID.equals("§")) {
                hasMoreChats = false;
            } else {
                Boolean hasMoreMessages = true;
                while(hasMoreMessages){
                    if (br.readLine().equals("§")) {
                        hasMoreMessages = false;
                    } else {
                        chatlogs.get(chatLogIndex).add(Message.toMessage(br.readLine()));
                    }
                }
            }
            chatLogIndex++;
        }

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
}
