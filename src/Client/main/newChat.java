package Client.main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class newChat {
    @FXML
    private static VBox userListVBox;

    @FXML
    private static ListView<String> users;
    @FXML
    private static ListView<String> chatUsers;

    @FXML
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

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        list.setItems(items);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        NewChatScene.add(list,0,0);
        NewChatScene.add(Buttons,0,1);
        Buttons.add(cancelbtn,0,0);
        Buttons.add(submitbtn,1,0);



//        String client ="";
//        ArrayList<String> chatUsers = null;
//        int indexChatUser = 0;
//        for(int i=0; i < clients.length(); i++) {
//            if (clients.charAt(i) != '§') {
//                client += clients.charAt(i);
//            } else {
//                System.out.println(client);
//                users.getItems().add(client);
//                users.getItems().get(indexChatUser);
//                users.setOnMouseClicked(e -> {
//                    if (chatUsers == null) {
//                        chatUsers.add(e.getSource().toString());
//                        users.getItems().remove(e.getSource().toString());
//                    } else if (chatUsers.contains(e.getSource().toString())) {
//                        chatUsers.remove(e.getSource().toString());
//                        users.getItems().add(e.getSource().toString());
//                    } else {
//                        chatUsers.add(e.getSource().toString());
//                        users.getItems().remove(e.getSource().toString());
//                    }
//
//                });
//                client = "";
//                indexChatUser ++;
//            }
//        }
        Main.getPrimaryWindow().setScene(new Scene(NewChatScene));
        Alert noget = new Alert(Alert.AlertType.CONFIRMATION);
        noget.showAndWait();
        items.add("Kage");

    }

    @FXML
    public void makeNewChat(ActionEvent actionEvent) {
        Main.getPw().print("NewChat§");
        for(int i=0; i<chatUsers.getItems().size(); i++){
            Main.getPw().print(chatUsers.getItems().get(i)+"§");
        }
        Main.getPw().println();
        Main.getPw().flush();
    }

    @FXML
    public void cancelNewChat(ActionEvent actionEvent) {
        chatUsers = null;

    }
}
