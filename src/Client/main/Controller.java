package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import universalClasses.Message;
import javafx.scene.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField loginUserInputText;
    @FXML
    private TextField loginPassInputText;
    @FXML
    private TextField chatInputText;


    @FXML
    public void chatSubmitButtonAction(ActionEvent actionEvent) {
        Message message = new Message(Client.main.Main.getCurrentChat(), Client.main.Main.getUserID(), chatInputText.getText());
        Main.addMessage(message);
        try {


            PrintWriter pwS = new PrintWriter(Client.main.Main.getSocket().getOutputStream());
            pwS.println(message.toString());
            pwS.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void loginSubmitButtonAction(ActionEvent actionEvent) {
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
        catch (Exception e) {
             login = "LoginFailed";
        }
        if(login.equals("LoginFailed") || login.equals("LoginAlready")){
            Alert loginFail = new Alert(Alert.AlertType.INFORMATION);
            loginFail.setHeaderText("Log ind Fejlede programmet lukker");
            loginFail.showAndWait();
            loginPassInputText.setText("");
            //Main.getPrimaryWindow().close();
        }
        else if(login.equals("LoginOkay")) {
            try {
                Main.getPrimaryWindow().setScene(new Scene(Main.getRoot(), 600, 475));
                Main.getLogFromServer();
                Thread listener = new ListenerThread(Main.getSocket());
                listener.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    protected void newChatButtonAction(ActionEvent actionEvent) throws IOException {
        Main.getPw().println("GetUsers");
        Main.getPw().flush();
    }
}
