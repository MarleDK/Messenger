package Client.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import universalClasses.Message;

public class MainScene {

    public MainScene(Stage primaryWindow){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        VBox leftSidebar = new VBox();
        root.add(leftSidebar, 0,0);

        GridPane rightSide = new GridPane();
        root.add(rightSide, 1,0);

        VBox chatArea = new VBox();
        rightSide.add(chatArea, 0,0);

        HBox chatBottom = new HBox();
        rightSide.add(chatBottom, 0,1);

        TextField chatInput = new TextField();
        chatBottom.getChildren().add(0, chatInput);

        Button submitChat = new Button("Send");
        chatBottom.getChildren().add(1, submitChat);
        submitChat.setOnAction(event1 -> {
            Message message = new Message(Main.getCurrentChat(), Main.getUserID(), chatInput.getText());
            Main.addMessage(message);
            Main.getPw().println(message.toString());
            Main.getPw().flush();

        });

        ListView<String> chats = new ListView<>();
        ObservableList<String> chatIDs = FXCollections.observableArrayList();
        chats.setItems(chatIDs);
        for(int i=0; i<Main.chatIDs.size(); i++){
            chatIDs.add(Main.chatIDs.get(i));
        }

        leftSidebar.getChildren().add(chats);

        chats.setOnMouseClicked(e -> {

        });

        Button newChat = new Button("New Chat");
        leftSidebar.getChildren().add(0, newChat);
        newChat.setOnAction(event -> {
            System.out.println("Sending new chat request");
            Main.getPw().println("GetUsers§");
            Main.getPw().flush();
        });





        primaryWindow.setScene(new Scene(root, primaryWindow.getWidth(), primaryWindow.getHeight()));
    }


}
