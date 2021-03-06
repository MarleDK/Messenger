package Client.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import universalClasses.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {
    private static String currentChat;
    private static String userID;
    private static Socket socket;
    private static TextField loginPassInputText;
    private static TextField loginUserInputText;
    public static final ArrayList<String> chatIDs = new ArrayList<>();
    public static final ArrayList<String> chatNames = new ArrayList<>();
    private static ArrayList<ArrayList<Message>> chatlogs;
    private static PrintWriter pw;
    private static BufferedReader br;
    private static Stage primaryWindow;
    private static MainScene mainScene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryWindow = primaryStage;
        Platform.setImplicitExit(false);

        GridPane loginScene = new GridPane();
        loginScene.setAlignment(Pos.CENTER);
        loginScene.setHgap(10);
        loginScene.setVgap(10);

        GridPane felter = new GridPane();
        felter.setAlignment(Pos.CENTER_RIGHT);
        TextField ip = new TextField("127.0.0.1");

        HBox knapper = new HBox();
        HBox knapper1 = new HBox();

        Button newUserBtn = new Button("New user");

        Button loginbtn = new Button();
        loginbtn.setText("Login");
        loginbtn.setOnAction(e -> {

            setServerAdr(ip.getText());
            // Login metode her
            String login;
            try {
                Main.getPw().print("ExistingUser§");
                Main.getPw().print(loginUserInputText.getText() + "§");
                Main.getPw().println(loginPassInputText.getText());
                Main.getPw().flush();
                System.out.println("Venter på svar..");
                login = Main.getBr().readLine();
                System.out.println("Modtaget: " + login);
            } catch (Exception ex) {
                login = "LoginFailed";
            }
            if (login.equals("LoginFailed")) {
                Alert loginFail = new Alert(Alert.AlertType.INFORMATION);
                loginFail.setHeaderText("Log ind Fejlede prøv igen");
                loginFail.showAndWait();
                loginPassInputText.setText("");
            } else if (login.equals("LoginOkay")) {
                try {
                    setUserID(loginUserInputText.getText());
                    Main.getLogFromServer();
                    if (Main.getCurrentChat() == null) {
                        System.out.println("Sender new chat request");
                        mainScene = new MainScene(Main.getPrimaryWindow());
                        Main.getPw().println("GetUsers§");
                        Main.getPw().flush();
                    } else {
                        mainScene = new MainScene(Main.getPrimaryWindow());
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
        Button registerUser = new Button("Register");
        Button cancel = new Button("cancel");
        Label confirmPassLbl = new Label("ConfirmPassword:  ");
        PasswordField confirmPassField = new PasswordField();

        Label username = new Label("Username:  ");
        Label password = new Label("Password:  ");
        Label ipLabel = new Label("IP adresse: ");
        Text warning = new Text();
        username.setAlignment(Pos.CENTER_RIGHT);

        felter.add(loginUserInputText, 1, 0);
        felter.add(loginPassInputText, 1, 1);
        felter.add(ip, 1, 2);
        felter.add(username,0,0);
        felter.add(password,0,1);
        felter.add(ipLabel, 0,2);
        loginScene.add(warning, 0,1);

        loginScene.add(felter, 0, 0);
        felter.add(knapper, 1, 4);
        knapper.getChildren().add(loginbtn);
        knapper.getChildren().add(newUserBtn);
        knapper1.getChildren().setAll(registerUser, cancel );

        newUserBtn.setOnAction(event -> {

            felter.getChildren().remove(ip);
            felter.getChildren().remove(ipLabel);
            felter.getChildren().remove(knapper);
            felter.add(confirmPassField,1,2);
            felter.add(confirmPassLbl,0,2);
            felter.add(ip, 1,3);
            felter.add(ipLabel, 0,3);
            felter.add(knapper1, 1,4);

            cancel.setOnAction(event1 -> {
                felter.getChildren().remove(ip);
                felter.getChildren().remove(ipLabel);
                felter.getChildren().remove(confirmPassField);
                felter.getChildren().remove(confirmPassLbl);
                felter.getChildren().remove(knapper1);
                felter.add(ip,1,2);
                felter.add(ipLabel,0,2);
                felter.add(knapper, 1,3);
            });

            registerUser.setOnAction(event1 -> {
                if(loginUserInputText.getText().contains("§") || loginPassInputText.getText().contains("§") ){
                    warning.setText("Illegal username og password \nNeither must contain §");
                }else if(loginUserInputText.getText().isEmpty() || loginPassInputText.getText().isEmpty()){
                    warning.setText("Please input new username and password");
                }
                else if(!loginPassInputText.getText().equals(confirmPassField.getText())){
                    warning.setText("Password does not match confirmation password\nPlease input new password");
                }else {
                    setServerAdr(ip.getText());
                    String isNewUser;
                    try {
                        Main.getPw().print("NewUser§");
                        Main.getPw().print(loginUserInputText.getText() + "§");
                        Main.getPw().println(loginPassInputText.getText());
                        Main.getPw().flush();
                        System.out.println("Venter på svar..");
                        isNewUser = br.readLine();
                        System.out.println("Modtaget: " + isNewUser);
                    } catch (java.io.IOException ex) {
                        isNewUser = "RegisteringFailed";
                        System.out.println("RegisteringFailed");
                    }
                    if(isNewUser.equals("True§")){
                        setUserID(loginUserInputText.getText());
                        try {
                            Main.getLogFromServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Sender new chat request");
                        mainScene = new MainScene(Main.getPrimaryWindow());
                        Main.getPw().println("GetUsers§");
                        Main.getPw().flush();
                        Thread listener = new ListenerThread(Main.getSocket());
                        listener.start();
                    } else if(isNewUser.equals("UsernameTaken§")){
                        warning.setText("Username already taken");
                    }
                }
            });

        });

        primaryWindow.setTitle("Messenger+++");

        primaryWindow.setOnCloseRequest(e -> {
            try {
                pw.println("Quit§UserClosedProgram");
                pw.flush();
                pw.close();
                br.close();
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();

            } catch (Exception e1) {
                System.out.println("Error: Connection is already closed!");
            }
            System.exit(0);
        });
        primaryWindow.setScene(new Scene(loginScene, 600, 475));
        primaryWindow.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void getLogFromServer() throws IOException {
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
                            if (message.startsWith("Message§")) {
                                chatlogs.get(chatLogIndex).add(Message.toMessage(message));
                            }
                            else {
                                // Change the name to this
                                chatNames.add(message);
                            }
                        }
                    }
                }
                chatLogIndex++;
            }
            System.out.println("Afsluttede ChatLogs");
            getCurrentChat();

        } else {
            System.out.println("Should have gotten ChatLogs... but got " + input);
        }

    }

    public static ArrayList<Message> getMessagesFromCurrentChat() {
        if(!chatIDs.isEmpty()) {
            int index = chatIDs.indexOf(currentChat);
            return chatlogs.get(index);
        }
        return null;
    }


    public static void addMessage(Message message) {
        chatlogs.get(chatIDs.indexOf(message.samtaleID)).add(message);
        if(currentChat.equals(message.samtaleID)) {
            mainScene.addMessage(message);
        }
    }

    public static String getCurrentChat() {
        if (currentChat == null && !chatIDs.isEmpty()) {
            currentChat = chatIDs.get(0);
        }
        return currentChat;
    }

    public static void setCurrentChat(String chat){currentChat = chat;}

    public static String getUserID() {
        return userID;
    }

    private static Socket getSocket() {
        return socket;
    }

    public static void newChat(String ID) {
        chatIDs.add(ID.substring(0,17));
        chatlogs.add(new ArrayList<>());
        chatNames.add(ID.substring(18));
        System.out.println("Finalizing new chat..." + chatIDs.get(chatNames.size()-1));
        mainScene.addChat(chatNames.get(chatNames.size()-1));
        //
    }

    private static Stage getPrimaryWindow() {
        return primaryWindow;
    }

    private static BufferedReader getBr() {
        return br;
    }

    public static PrintWriter getPw() {
        return pw;
    }

    private static void setUserID(String userID) {
        Main.userID = userID;
    }

    public static MainScene getMainScene(){return mainScene;}

    private static void setServerAdr(String serverAdress) {
        String serverAdr;
        int serverPort = 1302;
        try {
            if (socket.isClosed()) {
                serverAdr = serverAdress;
                try {
                    socket = new Socket(serverAdr, serverPort);
                    pw = new PrintWriter(socket.getOutputStream());
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                } catch (java.io.IOException ex) {
                    System.out.println("No connection to server! Is it running?");
                }
            }
        }
        catch (java.lang.NullPointerException ex) {
            serverAdr = serverAdress;
            try {
                socket = new Socket(serverAdr, serverPort);
                pw = new PrintWriter(socket.getOutputStream());
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            } catch (java.io.IOException e) {
                System.out.println("No connection to server! Is it running?");
            }
        }

    }
}

