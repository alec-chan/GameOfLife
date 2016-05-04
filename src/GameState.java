import java.util.ArrayList;

/**
 * Currently unused..... Not sure what I was trying to do here -_-
 * Created by alec on 3/18/16.
 */
public class GameState {
    ArrayList<Cell> states = new ArrayList<>();
    public GameState(ArrayList<Cell> st)
    {
        states=st;
    }
    public ArrayList<Cell> getStates()
    {
        return states;
    }
    public void setStates(ArrayList<Cell> s)
    {
        states=s;
    }

}
