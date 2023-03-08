package observerpattern;
import model.plateau.*;

public interface Data{
    public Data getData();
    default Cell[][] getCellBoard(){
        return null;
    }
    default Cell board(int i, int j) {
        return null;
    }
    default int size() {
        return -1;
    }
    
}
