package Client.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

        String client ="";
        ArrayList<String> chatUsers = null;
        int indexChatUser = 0;
        for(int i=0; i < clients.length(); i++) {
            if (clients.charAt(i) != '§') {
                client += clients.charAt(i);
            } else {
                System.out.println(client);
                users.getItems().add(client);
                users.getItems().get(indexChatUser);
                users.setOnMouseClicked(e -> {
                    if (chatUsers == null) {
                        chatUsers.add(e.getSource().toString());
                        users.getItems().remove(e.getSource().toString());
                    } else if (chatUsers.contains(e.getSource().toString())) {
                        chatUsers.remove(e.getSource().toString());
                        users.getItems().add(e.getSource().toString());
                    } else {
                        chatUsers.add(e.getSource().toString());
                        users.getItems().remove(e.getSource().toString());
                    }

                });
                client = "";
                indexChatUser ++;
            }
        }
        Platform.runLater(new Runnable() {
            @Override public void run() {
                // kald metoden her
                Main.getPrimaryWindow().setScene(new Scene(Main.getNewChatScene()));
            }
        });


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
