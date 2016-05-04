import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.TimerTask;

/**
 * The GameBoard class contains most of the action for the simulation.  TODO: Probably should refactor out some of the logic to separate classes later.
 * Most of the action happens in the constructor.
 *
 * Created by alec on 3/14/16.
 */
public class GameBoard extends Stage
{

    static ArrayList<Cell> cells = new ArrayList<>();   //the GameBoard's cell array; size determined by the x and y values passed to the ctor.
    int count;      //......
    public Timer timer; //Timer to control the lifetime of each generation.  TODO: Could make the time a variable controllable by the user.
    boolean isEdit;     //flag to check wether the GameBoad is in editMode of not.
    GridPane grid;      //GridPane as the rootNode

    Scene theScene;     //scene for the stage.


    static int xCells;  //static dimensions of the board so other classes can access them.
    static int yCells;


    public GameBoard(int x, int y)
    {
        xCells=x;
        yCells=y;


        count=0;    //reset counter

        isEdit=true;    //set the gameBoard to editable initially
        grid = new GridPane();
        theScene = new Scene(grid, 25*x+150,20*y+60);   //sets the rootNode and the dimensions of the scene.  TODO: Fix the way the scene is scaled.  The scene never fits to the elements on the gridpane.
        setScene(theScene);

        grid.setPadding(new Insets(20));    //set padding to 30px
        grid.setHgap(0);    //set 0 h and v gap to create a tight grid of cells
        grid.setVgap(0);



        //safely shut down simulation on close request.
        setOnCloseRequest(we-> {

                timer.stop();
                close();

        });


        //nested for loops to populate rows and columns of gridpane with cells based on x and y values passed by user.
        for(int i = 0; i<y; i++)
        {
            for(int j = 0; j<x; j++)
            {
                cells.add(new Cell(count));
                grid.add(cells.get(count), j,i);
                count++;    //counts total # of cells created

            }
        }

        //generation timer.  ActionEvent executes every tick, where tick=500ms, when timer is started.
        timer = new Timer(500, ae-> {


            ArrayList<Cell> myState = getCurrentGameBoard(cells);
            for (int i=0;i<cells.size(); i++)
            {
                cells.get(i).setEditable(false);
                cells.get(i).step(myState);
            }
        });





        Button start = new Button("Start");
        Button stop = new Button("Stop");

        stop.setOnAction(event->{
            timer.stop();
            for(int i =0; i<cells.size(); i++)
            {
                cells.get(i).setState(false);
                cells.get(i).setEditable(true);
            }
        });



        Slider gen = new Slider();
        gen.setMin(1);
        gen.setMax(200);
        gen.setValue(1);
        Label l = new Label("1 Generations");



        gen.valueProperty().addListener((observable, oldValue, newValue) ->{
                //System.out.println(newValue.doubleValue());
                l.setText(newValue.intValue()+" Generations");

        });



        start.setOnAction(event -> {

            int generation=0;

            timer.setRepeats(true);

            timer.start();

            generation++;

            while(generation<gen.getValue())
            {
                if(!timer.isRunning()) {
                    timer.restart();
                    generation++;
                    l.setText("Generation " + generation);
                }
            }
            generation=0;
            System.out.println("Done");



        });


        grid.add(start, x+2,0);
        grid.add(gen, x+2,2);
        grid.add(l,x+2,3);
        grid.add(stop, x+2,1);

    }


    public static ArrayList<Cell> getNeighbors(int index, ArrayList<Cell> current)
    {

        ArrayList<Cell> c = new ArrayList<Cell>();
        if(index-1>0&&index-1<cells.size())
            c.add(current.get(index-1));
        if(index+1>0&&index+1<cells.size())
            c.add(current.get(index+1));
        if(index-xCells>0&&index-xCells<cells.size())
            c.add(current.get(index-xCells));
        if(index+xCells>0&&index+xCells<cells.size())
            c.add(current.get(index+xCells));
        if(index-xCells+1>0&&index-xCells+1<cells.size())
            c.add(current.get(index-xCells+1));
        if(index-xCells-1>0&&index-xCells-1<cells.size())
            c.add(current.get(index-xCells-1));
        if(index+xCells+1>0&&index+xCells+1<cells.size())
            c.add(current.get(index+xCells+1));
        if(index+xCells-1>0&&index+xCells-1<cells.size())
            c.add(current.get(index+xCells-1));

        return c;
    }


    public static ArrayList<Cell> getCurrentGameBoard(ArrayList<Cell> d)
    {
        ArrayList<Cell> t = (ArrayList<Cell>)d.clone();
        return t;
    }






}
