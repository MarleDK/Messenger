package Client.main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class newChat {
    private static VBox userListVBox;

    private static ListView<String> users;
    private static ListView<String> chatUsers;

    private static int indexChatUser;

    public static void makeChat(String clients){

        System.out.println("Starter makeChat i newChat...");


        GridPane NewChatScene = new GridPane();
        NewChatScene.setAlignment(Pos.CENTER);
        NewChatScene.setHgap(10);
        NewChatScene.setVgap(10);

        GridPane Buttons = new GridPane();
        NewChatScene.setAlignment(Pos.CENTER);
        NewChatScene.setHgap(10);
        NewChatScene.setVgap(10);

        Button submitbtn = new Button();
        submitbtn.setText("Submit");

        Button cancelbtn = new Button();
        cancelbtn.setText("Cancel");

        users = new ListView<>();
        ObservableList<String> useritems = FXCollections.observableArrayList ();
        users.setItems(useritems);
        // users.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        chatUsers = new ListView<>();
        ObservableList<String> chatitems = FXCollections.observableArrayList ();
        chatUsers.setItems(chatitems);
        // chatUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        chatUsers.setOnMouseClicked(e -> {
            if (chatUsers.getSelectionModel().getSelectedItem() != null) {
                useritems.add(chatUsers.getSelectionModel().getSelectedItem());
                chatitems.remove(chatUsers.getSelectionModel().getSelectedItem());
            }
        });
        users.setOnMouseClicked(e -> {
            if (users.getSelectionModel().getSelectedItem() != null) {
                chatitems.add(users.getSelectionModel().getSelectedItem());
                useritems.remove(users.getSelectionModel().getSelectedItem());
            }
        });
        useritems.remove(Main.getUserID());
        submitbtn.setOnAction(e -> {
            useritems.add(Main.getUserID());
            Main.getPw().print("NewChat§");
            for(int i=0; i<chatUsers.getItems().size(); i++){
                Main.getPw().print(chatUsers.getItems().get(i)+"§");
            }
            Main.getPw().println();
            Main.getPw().flush();
        });

        NewChatScene.add(users,0,0);
        NewChatScene.add(chatUsers,1,0);
        NewChatScene.add(Buttons,0,1);
        Buttons.add(cancelbtn,0,0);
        Buttons.add(submitbtn,1,0);

        String client ="";
        indexChatUser = 0;
        for(int i=0; i < clients.length(); i++) {
            if (clients.charAt(i) != '§') {
                client += clients.charAt(i);
            } else {
                System.out.println(client);
                useritems.add(client);
                client = "";
                indexChatUser ++;
            }
        }
        Main.getPrimaryWindow().setScene(new Scene(NewChatScene));

    }


    public void cancelNewChat(ActionEvent actionEvent) {
        chatUsers = null;

    }
}
