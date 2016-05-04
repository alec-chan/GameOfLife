import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.*;

/**
 * The entry point for the game of life.  Contains 2 comboboxes and 1 button to setup a new gameboard
 * Created by alec on 3/14/16.
 */
public class Entry extends Application
{
    public void start(Stage mainStage)
    {

        GridPane rootNode = new GridPane();     //create new gridpane
        rootNode.setPadding(new Insets(30));    //set padding to 30px
        rootNode.setHgap(5);                    //set H and V gap to 5px
        rootNode.setVgap(5);


        Scene mainScene = new Scene(rootNode, 200, 150);    //create new scene with the gridpane as root node

        //Creates 2 comboboxes and populates them with an ObservableList of Integer range from 1-100//
        ComboBox c = new ComboBox<Integer>();
        ComboBox c2 = new ComboBox<Integer>();

        ArrayList<Integer> f = new ArrayList<Integer>();
        for(int i = 0; i<100; i++)
        {
            f.add(i,i+1);
        }

        ObservableList<Integer> ints = FXCollections.observableArrayList(f);

        c.setItems(ints);
        c2.setItems(ints);

        Label l1 = new Label("X:");
        Label l2 = new Label("Y:");
        rootNode.add(l1,0,0);
        rootNode.add(l2,1,0);
        rootNode.add(c,0,1);
        rootNode.add(c2, 1,1);
        //End Combobox setup//



        Button b = new Button("Create");

        //Init new gameboard on button click
        b.setOnAction(event-> {
            //mainStage.setScene(new GameBoard(rootNode, 100,100,2,2));
            GameBoard game = new GameBoard((Integer)c.getValue(), (Integer)c2.getValue());
            game.show();
            mainStage.close();
        });


        rootNode.add(b,0,2);

        mainStage.setScene(mainScene);

        mainStage.show();
    }
}
