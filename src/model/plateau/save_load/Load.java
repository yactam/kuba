package model.plateau.save_load;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import model.plateau.Board;

public class Load {
    public static Board load(String path){
        try {
            FileInputStream fileInputStream
                = new FileInputStream(path);
            ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
            Board b = (Board) objectInputStream.readObject();
            objectInputStream.close();
            return b;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
