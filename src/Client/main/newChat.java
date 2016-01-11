package Client.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created by jakob on 11-01-2016.
 */
public class newChat {
    @FXML
    private static VBox userListVBox;
    private static ArrayList<String> chatUsers = null;


    public static void makeChat(String clients){

        ListView<Button> users = new ListView<Button>();

        String client ="";
        ArrayList<String> chatUsers = null;
        for(int i=0; i<clients.length(); i++){
            if(clients.charAt(i) != '§'){
                client += clients.charAt(i);
            } else if(clients.charAt(i) == '§'){
                users.getItems().add(new Button(client));
                users.getItems().get(i).setStyle("-fx-background-color: white; -fx-text-fill: white");
                users.getItems().get(i).setOnAction(e -> {
                    if (chatUsers == null){
                        chatUsers.add(e.getSource().toString());
                        users.getItems().get(users.getItems().indexOf(e.getSource().toString())).setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white");
                    } else if(chatUsers.contains(e.getSource().toString())){
                        chatUsers.remove(e.getSource().toString());
                        users.getItems().get(users.getItems().indexOf(e.getSource().toString())).setStyle("-fx-background-color: white; -fx-text-fill: black");
                    } else {
                        chatUsers.add(e.getSource().toString());
                        users.getItems().get(users.getItems().indexOf(e.getSource().toString())).setStyle("-fx-background-color: deepskyblue; -fx-text-fill: white");
                    }

                });
                client ="";
            }
        }
        userListVBox.getChildren().add(users);
        Main.getPrimaryWindow().setScene(new Scene(Main.getNewChatScene()));

    }

    @FXML
    public void makeNewChat(ActionEvent actionEvent) {

    }

    @FXML
    public void cancelNewChat(ActionEvent actionEvent) {

    }
}
