package Client.main;

import com.sun.deploy.util.SessionState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import universalClasses.Message;
import javafx.scene.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.scene.control.TextField;

public class Controller {
    @FXML private TextField chatInputText;

    @FXML
    public void chatSubmitButtonAction(ActionEvent actionEvent) {
        Message message = new Message(Client.main.Main.getCurrentChat(), Client.main.Main.userID, chatInputText.getText());

        /*File folder = new File("clientdatabase/log");
        for(int i=0; i<folder.listFiles().length; i++) {
            if(folder.listFiles()[i].getName().equals(Client.main.Main.getCurrentChat())) {
                try {
                    PrintWriter pw = new PrintWriter(folder.listFiles()[i]);
                    pw.println("[" + message.time.toString() + "]" + message.afsenderID + ": " + message.text)
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }

    */
    }


    @FXML
    protected void newChatButtonAction(ActionEvent actionEvent) {
    }
}
