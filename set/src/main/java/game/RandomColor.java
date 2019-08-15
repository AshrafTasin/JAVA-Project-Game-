package game;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

public  class RandomColor {

    static int wow=0;
    public static Color color(){

        ArrayList<Color> mycol = new ArrayList<> () ;
        mycol.add(Color.LIME);
        mycol.add(Color.PALETURQUOISE);
        mycol.add(Color.LIGHTBLUE);

        Random r=new Random();
        wow++;
        return mycol.get(wow%3);
    }
}
