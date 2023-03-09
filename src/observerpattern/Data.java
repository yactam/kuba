package observerpattern;
import model.plateau.*;
import model.*;
public interface Data{
    public Data getData();
    boolean libre(int i,int j);
    Bille obtenirBille(int i,int j);
    Cell board(int i, int j);        
    int size();
}
