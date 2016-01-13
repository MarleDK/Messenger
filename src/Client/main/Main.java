package Client.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import universalClasses.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {
    private static String currentChat;
    private static String userID = "Jakob";
    private static Socket socket;
    private static TextField loginPassInputText;
    private static TextField loginUserInputText;
    public static ArrayList<String> chatIDs = new ArrayList<>();
    public static ArrayList<ArrayList<Message>> chatlogs;
    private static String serverAdr = "127.0.0.1";
    private static int serverPort = 1102;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static Parent root;
    private static Parent loginScene;
    private static Parent newChatScene;
    private static Stage primaryWindow;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryWindow = primaryStage;
        Platform.setImplicitExit(false);

        GridPane loginScene = new GridPane();
        loginScene.setAlignment(Pos.CENTER);
        loginScene.setHgap(10);
        loginScene.setVgap(10);

        GridPane felter = new GridPane();
        felter.setAlignment(Pos.CENTER);

        Button loginbtn = new Button();
        loginbtn.setText("Login");
        loginbtn.setOnAction(e -> {
        // Login metode her
        String login;
        try {
            Main.getPw().print("ExistingUser§");
            Main.getPw().print(loginUserInputText.getText()+"§");
            Main.getPw().println(loginPassInputText.getText());
            Main.getPw().flush();
            System.out.println("Venter på svar..");
            login = Main.getBr().readLine();
            System.out.println("Modtaget: " + login);
        }
        catch (Exception ex) {
            login = "LoginFailed";
        }
        if(login.equals("LoginFailed")){
            Alert loginFail = new Alert(Alert.AlertType.INFORMATION);
            loginFail.setHeaderText("Log ind Fejlede prøv igen");
            loginFail.showAndWait();
            loginPassInputText.setText("");
        }
        else if(login.equals("LoginOkay")) {
            try {
                Main.getLogFromServer();
                if (Main.getCurrentChat() == null) {
                    System.out.println("Sender new chat request");
                    Main.getPw().println("GetUsers§");
                    Main.getPw().flush();
                }
                else {
                    new MainScene(Main.getPrimaryWindow());
                }
                Thread listener = new ListenerThread(Main.getSocket());
                listener.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        });
        loginUserInputText = new TextField();
        loginPassInputText = new PasswordField();

        felter.add(loginUserInputText,0,0);
        felter.add(loginPassInputText,0,1);
        loginScene.add(felter,0,0);
        loginScene.add(loginbtn,0,1);


        /// FUUUUUU DIS!!!! (7 timer mistet pga. dette!)
        //root = FXMLLoader.load(getClass().getResource("StructureRoot.fxml"));
        // loginScene = FXMLLoader.load(getClass().getResource("StructureLogin.fxml"));
        // newChatScene = FXMLLoader.load(getClass().getResource("StructureNCS.fxml"));

        primaryWindow.setTitle("Messenger+++");

        //Først skal der oprettes forbindelse til serveren
        //åben ListenerThread

        try {
            socket = new Socket(serverAdr, serverPort);
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }
        catch (Exception e) {
            System.out.println("No connection to server! Is it running?");
        }

        primaryWindow.setOnCloseRequest(e ->{
            //deleteLog();

        });
        primaryWindow.setScene(new Scene(loginScene, 600, 475));
        //root.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        //loginScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        //newChatScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        primaryWindow.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void getLogFromServer() throws IOException {
        System.out.println("Venter på type...");
        String input = br.readLine();
        if (input.startsWith("ChatLogs§")) {
            System.out.println("Fik " + input);
            chatlogs = new ArrayList<>();
            int chatLogIndex = 0;

            Boolean hasMoreChats = true;
            while (hasMoreChats) {
                String chatID = br.readLine();
                System.out.println(chatID);
                if (chatID.equals("§")) {
                    hasMoreChats = false;
                } else {
                    chatIDs.add(chatID);
                    chatlogs.add(new ArrayList<>());
                    Boolean hasMoreMessages = true;
                    while (hasMoreMessages) {
                        String message = br.readLine();
                        if (message.equals("§")) {
                            hasMoreMessages = false;
                        } else {
                            chatlogs.get(chatLogIndex).add(Message.toMessage(message));
                        }
                    }
                }
                chatLogIndex++;
            }
            System.out.println("Afsluttede ChatLogs");

        }
        else {
            System.out.println("Should have gotten ChatLogs... but got " + input);
        }

    }

    public static Parent getRoot() {
        return root;
    }

    public static void addMessage(Message message){
        chatlogs.get(chatIDs.indexOf(message.samtaleID)).add(message);
    }

    public static String getCurrentChat(){
        if (currentChat == null && !chatIDs.isEmpty()) {
            currentChat = chatIDs.get(0);
        }
        return currentChat;
    }

    public static String getUserID() {
        return userID;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void newChat (String IDs) {
        chatIDs.add(IDs);
        chatlogs.add(new ArrayList<>());
        //
    }

    public static Stage getPrimaryWindow() {
        return primaryWindow;
    }

    public static BufferedReader getBr() {
        return br;
    }

    public static PrintWriter getPw() {
        return pw;
    }

    public static void setUserID(String userID) {
        Main.userID = userID;
    }


    public static Parent getNewChatScene() {
        return newChatScene;
    }
}

