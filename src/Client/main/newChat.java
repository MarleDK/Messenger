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


        Button submitbtn = new Button();
        submitbtn.setText("Submit");
        submitbtn.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;");
        submitbtn.setPrefWidth(250);

        Button cancelbtn = new Button();
        cancelbtn.setText("Cancel");
        cancelbtn.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;");
        cancelbtn.setPrefWidth(250);

        users = new ListView<>();
        ObservableList<String> useritems = FXCollections.observableArrayList ();
        users.setItems(useritems);
        users.setPrefHeight(448-cancelbtn.getHeight());
        users.setStyle("-fx-border-color: black; -fx");
        // users.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        chatUsers = new ListView<>();
        ObservableList<String> chatitems = FXCollections.observableArrayList ();
        chatUsers.setItems(chatitems);
        chatUsers.setPrefHeight(448-cancelbtn.getHeight());
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
        System.out.println(Main.getUserID() + " is getUserID");
        submitbtn.setOnAction(e -> {
            if (!chatitems.isEmpty()) {
                System.out.print("NewChat§" + Main.getUserID() + "§");

                Main.getPw().print("NewChat§" + Main.getUserID() + "§");
                for (int i = 0; i < chatUsers.getItems().size(); i++) {
                    if (!chatUsers.getItems().get(i).equals("null")) {
                        Main.getPw().print(chatUsers.getItems().get(i) + "§");
                    }
                }
                System.out.println();
                Main.getPw().println();
                Main.getPw().flush();
                Main.getMainScene().setToChatArea();
            }
        });


        cancelbtn.setOnAction(event ->Main.getMainScene().setToChatArea());
        NewChatScene.add(users,0,0);
        NewChatScene.add(chatUsers,1,0);
        NewChatScene.add(cancelbtn,0,1);
        NewChatScene.add(submitbtn,1,1);

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
        useritems.remove(Main.getUserID());
        System.out.println("Should switch scene");
        Main.getMainScene().setRightSide(NewChatScene);

    }

}
