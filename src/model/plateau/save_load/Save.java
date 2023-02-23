package model.plateau.save_load;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import model.plateau.Board;

public abstract class Save {
    public static void save(Board board){
        try {
            FileOutputStream fileOutputStream 
                = new FileOutputStream("yourfile.txt");
            ObjectOutputStream objectOutputStream 
                = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(board);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
