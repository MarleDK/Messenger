package Client.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import universalClasses.Message;

import java.util.ArrayList;

public class MainScene {
    private final ObservableList<HBox> chat;
    //public static String chatNames;
    private final ObservableList<String> chatIDs;
    private final VBox chatArea;
    private final GridPane rightSide2;
    private final GridPane rightSide;
    private final TextField chatInput;

    public MainScene(Stage primaryWindow) {

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setStyle("-fx-background-color: white");

        VBox leftSidebar = new VBox();
        root.add(leftSidebar, 0, 0);
        leftSidebar.setPrefWidth(150);

        rightSide2 = new GridPane();
        root.add(rightSide2, 1, 0);

        rightSide = new GridPane();
        rightSide2.add(rightSide, 0, 0);
        rightSide.setPrefWidth(450);
        rightSide.setPrefHeight(475);

        chatArea = new VBox();
        chatArea.setStyle("-fx-border-color: black");
        chatArea.setPrefHeight(475);
        rightSide.add(chatArea, 0, 0);

        HBox chatBottom = new HBox();
        rightSide.add(chatBottom, 0, 1);

        chatInput = new TextField();
        chatBottom.getChildren().add(0, chatInput);
        chatInput.setStyle("-fx-border-color: black");

        Button submitChat = new Button("Send");
        chatBottom.getChildren().add(1, submitChat);
        submitChat.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;");
        submitChat.setMinWidth(70);

        chatInput.setPrefWidth(450);

        ListView<String> chats = new ListView<>();
        chatIDs = FXCollections.observableArrayList();
        chats.setItems(chatIDs);
        chats.setStyle("-fx-border-color: black");
        chats.setPrefHeight(448);


        chatInput.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.ENTER) {
                this.submitChat();
            }
        });

        leftSidebar.getChildren().add(chats);


        chats.setOnMouseClicked(e -> {
            String chat = chats.getSelectionModel().getSelectedItems().get(0);
            setRightSide(rightSide);
            System.out.println();
            System.out.println(Main.chatIDs.get(chatIDs.indexOf(chat)));
            Main.setCurrentChat(Main.chatIDs.get(chatIDs.indexOf(chat)));

            addAllMessages();
        });

        Button newChat = new Button("New Chat");
        leftSidebar.getChildren().add(0, newChat);
        newChat.setPrefWidth(150);
        newChat.setStyle("-fx-background-color: white;" +
                "-fx-border-color: black;");
        newChat.setOnAction(event -> {
            System.out.println("Sending new chat request");
            Main.getPw().println("GetUsers§");
            Main.getPw().flush();

        });

        chat = FXCollections.observableArrayList();
        setRightSide(rightSide);
        System.out.println("current chat " + Main.getCurrentChat());


        submitChat.setOnAction(event1 -> this.submitChat());


        ListView<HBox> chatList = new ListView<>();
        chatArea.getChildren().add(chatList);
        chatList.setItems(chat);
        chatList.setPrefWidth(450);
        chatList.setPrefHeight(470);

        primaryWindow.setScene(new Scene(root, primaryWindow.getWidth(), primaryWindow.getHeight()));
        if (!(Main.getCurrentChat() == null)) {
            addAllMessages();
        }
        for (int i = 0; i < Main.chatNames.size(); i++) {
            addChat(Main.chatNames.get(i));
        }
    }

    private void addAllMessages() {
        ArrayList<Message> messages = Main.getMessagesFromCurrentChat();
        System.out.println("adding messages");
        chat.clear();
        if (!(messages != null && messages.isEmpty()) && (messages != null ? messages.get(0) : null) == null) {
            assert messages != null;
            messages.remove(0);
        }
        messages.forEach(this::addMessage);
    }

    public void addMessage(Message message) {
        System.out.println(message);
        Text field = new Text(message.afsenderID + ":\n" + message.text);
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

        hbox.setPrefWidth(430);
        hbox.setMaxWidth(chatArea.getWidth());
    }

    public void addChat(String ID) {
        System.out.println("ChatID før user er fjernet "+ID);
        System.out.println(Main.getUserID());
        ID.replace((Main.getUserID()+"§"), " ");
        System.out.println("ChatID efter user er fjernet "+ID);
        int j = 0;
        while (true) {
            System.out.println("adding chats"+j);
            if (!(chatIDs.contains(ID.replaceAll("§", " ") + " " + j))) {
                chatIDs.add(ID.replaceAll("§", " ") + " " + j);
                break;
            }
            j++;
        }
    }



    public void setRightSide(GridPane rightSide) {
        this.rightSide2.getChildren().setAll(rightSide);
    }

    public void setToChatArea() {
        this.rightSide2.getChildren().setAll(rightSide);
    }

    private void submitChat() {
        if (!chatInput.getText().isEmpty()) {
            Message message = new Message(Main.getCurrentChat(), Main.getUserID(), chatInput.getText());
            Main.addMessage(message);
            Main.getPw().println(message.toString());
            Main.getPw().flush();
            chatInput.clear();
        }
    }
}
