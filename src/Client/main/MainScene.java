package Client.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScene {

    public void MainScene(Stage primaryWindow){
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        VBox leftSidebar = new VBox();
        root.add(leftSidebar, 0,0);

        GridPane rightSide = new GridPane();
        root.add(rightSide, 1,0);

        VBox chatArea = new VBox();
        rightSide.add(chatArea, 0,0);

        HBox chatInput = new HBox();
        rightSide.add(chatInput, 0,1);


    }


}
