package Client.main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class newChat {

    @FXML
    private static ListView<String> users;
    @FXML
    private static ListView<String> selectedUsers;



    @FXML
    public static void makeChat(String clients){

        System.out.println("Starter makeChat i newChat...");

        users = new ListView<>();
        selectedUsers = new ListView<>();
        
        String client ="";
        for(int i=0; i < clients.length(); i++) {
            if (clients.charAt(i) != '§') {
                client += clients.charAt(i);
            } else {
                users.getItems().add(client);
                client = "";
            }
        }
        users.setOnMouseClicked(e -> {
            selectedUsers.getItems().add(e.getSource().toString());
            users.getItems().remove(e.getSource().toString());

        });
        selectedUsers.setOnMouseClicked(e -> {
            selectedUsers.getItems().remove(e.getSource().toString());
            users.getItems().add(e.getSource().toString());

        });

        System.out.println(users.getItems().get(0));

        Platform.runLater(new Runnable() {
            @Override public void run() {
                // kald metoden her

                ObservableList<String> usersObs = users.getItems();
                ObservableList<String> selectedUsersObs = selectedUsers.getItems();
                Main.getPrimaryWindow().setScene(new Scene(Main.getNewChatScene()));
            }
        });


    }

    @FXML
    public void makeNewChat(ActionEvent actionEvent) {
        Main.getPw().print("NewChat§");
        for(int i=0; i<selectedUsers.getItems().size(); i++){
            Main.getPw().print(selectedUsers.getItems().get(i)+"§");
        }
        Main.getPw().println();
        Main.getPw().flush();
    }

    @FXML
    public void cancelNewChat(ActionEvent actionEvent) {
        selectedUsers = null;

    }
}
