package game;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

public  class RandomColor2 {

    static int wow2=0;
    public static Color color(){

        ArrayList<Color> mycol = new ArrayList<> () ;
        mycol.add(Color.DARKORANGE);
        mycol.add(Color.DARKGOLDENROD);
        mycol.add(Color.DARKKHAKI);

        Random r=new Random();
        wow2++;
        return mycol.get(wow2%3);
    }
}
