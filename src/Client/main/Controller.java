package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import universalClasses.Message;
import javafx.scene.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.control.TextField;

public class Controller {
    @FXML private TextField chatInputText;

    @FXML
    public void chatSubmitButtonAction(ActionEvent actionEvent) {
        Message message = new Message(Client.main.Main.getCurrentChat(), Client.main.Main.getUserID(), chatInputText.getText());

        File folder = new File("clientdatabase/log");
        for(int i=0; i<folder.listFiles().length; i++) {
            if(folder.listFiles()[i].getName().equals(Client.main.Main.getCurrentChat())) {
                try {
                    PrintWriter pwL = new PrintWriter(folder.listFiles()[i]);
                    pwL.println(message.toString());

                    PrintWriter pwS = new PrintWriter(Client.main.Main.getSocket().getOutputStream());
                    pwS.println("message");
                    pwS.println(message.toString());
                    pwS.flush();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    @FXML
    protected void newChatButtonAction(ActionEvent actionEvent) {
    }
}
