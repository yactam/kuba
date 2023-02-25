package observerpattern;
import java.util.EventObject;
import model.plateau.Cell;
public class BoardChangedEvent extends EventObject{
    private Cell[][] newcells;
    
    public BoardChangedEvent(Object source, Cell[][] c){
        super(source);
        this.newcells = c;
    }
    public Cell[][] getNewcells() {
        return newcells;
    }
}
