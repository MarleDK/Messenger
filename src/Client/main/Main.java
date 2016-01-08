package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import universalClasses.TimeStamp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {
    private static String currentChat;
    public static String userID;

    @Override
    public void start(Stage primaryWindow) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Structure.fxml"));
        primaryWindow.setTitle("Messenger+++");

        //Først skal der oprettes forbindelse til serveren
        //åben ListenerThread


        Socket socket = new Socket();




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

    private static void getLog(Socket s){

    }

    public static String getCurrentChat(){
        return currentChat;
    }

    public static String getUserID() {
        return userID;
    }
}
