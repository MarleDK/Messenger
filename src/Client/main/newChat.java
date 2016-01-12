package Client.main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class newChat {

    @FXML
    private static GridPane usersList;
    @FXML
    private static GridPane selectedUsersList;

    private static ArrayList<String> selectedUsers;

    private static ArrayList<String> users;

    public static Button[] btnlist;
    public static int ID;

    @FXML
    public static void makeChat(String clients){

        System.out.println("Starter makeChat i newChat...");

        usersList = new GridPane();
        usersList.setAlignment(Pos.CENTER);
        usersList.setHgap(10);
        usersList.setVgap(10);

        //TA = new TextArea[3];
        users = new ArrayList<>();
        selectedUsers = new ArrayList<>();

        String client ="";
        for(int i=0; i < clients.length(); i++) {
            if (clients.charAt(i) != '§') {
                client += clients.charAt(i);
            } else {
                users.add(client);
                client = "";
            }
        }

        int size = users.size();
        btnlist = new Button[size];

        // Setup Buttons

        for (int i = 0; i < size; i++) {
            btnlist[i] = new Button();
            btnlist[i].setText("+" + users.get(i));
            btnlist[i].setMinSize(200,200);
            btnlist[i].setId(i + "");
            btnlist[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    // call pause
                    System.out.println(btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText());
                    System.out.println(Integer.parseInt(""+e.getSource().toString().charAt(10)));
                    if (btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText().startsWith("+")) {
                        users.remove(btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText());
                        selectedUsers.add(btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText());
                    }
                    else if (btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText().startsWith("-")) {
                        users.add(btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText());
                        selectedUsers.remove(btnlist[Integer.parseInt(""+e.getSource().toString().charAt(10))].getText());
                    }
                    refreshUserLists(size);
                }
            });
            usersList.add(btnlist[i], 0, i);
            System.out.println(i);

        }

        // End setup buttons



       Platform.runLater(new Runnable() {
            @Override public void run() {
                // kald metoden her

                Main.getPrimaryWindow().setScene(new Scene(Main.getNewChatScene(), Main.getPrimaryWindow().getWidth(), Main.getPrimaryWindow().getHeight()));
            }
        });
    }

    @FXML
    public void makeNewChat(ActionEvent actionEvent) {
        Main.getPw().print("NewChat§");
        Main.getPw().println();
        Main.getPw().flush();
    }

    @FXML
    public void cancelNewChat(ActionEvent actionEvent) {
        selectedUsers = null;

    }

    public static void refreshUserLists(int amount) {
        usersList.getChildren().clear();
        selectedUsersList.getChildren().clear();
        for (int i = 0; i < amount; i++) {
            if (btnlist[i].getText().startsWith("+")) {
                usersList.add(btnlist[i], 0, users.size());
            }
            else if (btnlist[i].getText().startsWith("-")) {
                selectedUsersList.add(btnlist[i], 0, selectedUsers.size());
            }
        }
    }
}
