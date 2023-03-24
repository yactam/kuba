package com.kuba.observerpattern;
import com.kuba.model.plateau.*;

public interface Data{
    public Data getData();
    boolean libre(int i,int j);
    Bille obtenirBille(int i,int j);
    Cell board(int i, int j);        
    int size();
}
