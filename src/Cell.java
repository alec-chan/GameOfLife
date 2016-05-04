d
import java.util.ArrayList;

/**
 * Created by alec on 3/14/16.
 */
public class Cell extends javafx.scene.control.Button{
    public boolean state;
    public boolean editable;
    final static String RED = "-fx-background-color: #ff0000;";
    final static String WHITE = "-fx-background-color: #ffffff;";
    int index;

    public Cell(int index) {
        setStyle("-fx-background-color: #ffffff;");
        state = false;
        editable = true;
        setIndex(index);
        setOnAction(event -> {
            if (editable)
                setState(!state);


        });
        getStylesheets().clear();
        getStylesheets().add("style.css");
        System.out.println(index);
        //setMinHeight(20);
        //setMinWidth(20);
        setPrefHeight(20);
        setPrefWidth(20);
        //setMaxWidth(20);
        //setMaxHeight(20);

    }

    public void setState(boolean state)
    {
        this.state = state;
        onStateChanged();
    }
    public void setEditable(boolean val)
    {
        editable=val;
        setDisable(!editable);
    }

    public void onStateChanged()
    {

        String choice = state? RED:WHITE;
        setStyle(choice);

    }

    public void setIndex(int index)
    {
        this.index = index;
    }


    public void step(ArrayList<Cell> v)
    {

        int alive=0;

        ArrayList<Cell> c = GameBoard.getNeighbors(index, v);
        for(Cell x : c)
        {

            if(x.state)
            {
                alive++;
            }

        }

        if(alive<2)
        {
            setState(false);
        }
        if(alive==2||alive==3)
        {
            setState(true);
        }
        if(alive>3)
        {
            setState(false);
        }
        if(!state&&alive==3)
        {
            setState(true);
        }
    }


}
