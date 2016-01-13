package Client.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import universalClasses.Message;

import java.util.ArrayList;

public class MainScene {
    private ObservableList<HBox> chat;
    private ObservableList<String> chatIDs;
    private Stage window;
    private VBox chatArea;
    private GridPane rightSide2;
    private GridPane rightSide;

    public MainScene(Stage primaryWindow) {
        window = primaryWindow;

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(0,0,0,0));

        VBox leftSidebar = new VBox();
        root.add(leftSidebar, 0, 0);

        rightSide2 = new GridPane();
        root.add(rightSide2,1,0);

        rightSide = new GridPane();
        rightSide2.add(rightSide, 0, 0);

        chatArea = new VBox();
        rightSide.add(chatArea, 0, 0);

        HBox chatBottom = new HBox();
        rightSide.add(chatBottom, 0, 1);

        TextField chatInput = new TextField();
        chatBottom.getChildren().add(0, chatInput);

        Button submitChat = new Button("Send");
        chatBottom.getChildren().add(1, submitChat);

        ListView<String> chats = new ListView<>();
        chatIDs = FXCollections.observableArrayList();
        chats.setItems(chatIDs);
        for (int i = 0; i < Main.chatIDs.size(); i++) {
            chatIDs.add(Main.chatIDs.get(i));
        }

        leftSidebar.getChildren().add(chats);


        chats.setOnMouseClicked(e -> {
            String chat = chats.getSelectionModel().getSelectedItems().get(0);
            setRightSide(rightSide);
            Main.setCurrentChat(chat);

            addAllMessages();
        });

        Button newChat = new Button("New Chat");
        leftSidebar.getChildren().add(0, newChat);
        newChat.setOnAction(event -> {
            System.out.println("Sending new chat request");
            Main.getPw().println("GetUsers§");
            Main.getPw().flush();

        });

        chat = FXCollections.observableArrayList();
        setRightSide(rightSide);
        System.out.println("current chat " +Main.getCurrentChat());
        addAllMessages();


        submitChat.setOnAction(event1 -> {
            Message message = new Message(Main.getCurrentChat(), Main.getUserID(), chatInput.getText());
            Main.addMessage(message);
            Main.getPw().println(message.toString());
            Main.getPw().flush();
            chatInput.clear();
        });


        ListView<HBox> chatList = new ListView<>();
        chatArea.getChildren().add(chatList);
        chatList.setItems(chat);

        primaryWindow.setScene(new Scene(root, primaryWindow.getWidth(), primaryWindow.getHeight()));
    }

    public void addAllMessages(){
        ArrayList<Message> messages = Main.getMessagesFromCurrentChat();
        System.out.println("adding messages");
        chat.clear();
        if (!messages.isEmpty() && messages.get(0) == null) {
            messages.remove(0);
        }
        for (Message message : messages) {
            addMessage(message);
        }
    }

    public void addMessage(Message message){
        System.out.println(message);
        Text field = new Text(message.afsenderID +":\n"+ message.text);
        HBox hbox = new HBox();
        chat.add(hbox);
        if (message.afsenderID.equals(Main.getUserID())) {
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            field.setStyle("ID:MessageFieldThis");
        } else {
            hbox.setAlignment(Pos.BOTTOM_LEFT);
            field.setStyle("ID:MessageFieldOther");
        }
        field.setText(message.afsenderID + ":\n" + message.text);
        hbox.getChildren().add(field);
//        field.setMaxWidth(chatArea.getWidth()*0.7);
//        field.setEditable(false);
        hbox.setStyle("-fx-background-color: deepskyblue;");
        field.setStyle(" -fx-border-top-left-radius: 10px; -fx-border-top-right-radius: 10px; -fx-border-bottom-left-radius: 10px;-fx-border-bottom-right-radius: 10px ");
        hbox.setPrefWidth(500);
        hbox.setMaxWidth(chatArea.getWidth());
    }

    public void addChat(String ID){
        chatIDs.add(ID);
    }

    public void setRightSide(GridPane rightSide) {
        this.rightSide2.getChildren().setAll(rightSide);
    }

    public void setToChatArea() {
        this.rightSide2.getChildren().setAll(rightSide);
    }
}
