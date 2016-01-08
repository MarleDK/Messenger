package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryWindow) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Structure.fxml"));
        primaryWindow.setTitle("Messenger+++");

        //Først laver vi en scanner til at finde de samtaler



        primaryWindow.setOnCloseRequest(e ->{

        });
        primaryWindow.setScene(new Scene(root, 600, 475));
        primaryWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void deleteLog(){
        //Scanner log = new Scanner(Client.database.log);
    }
}
