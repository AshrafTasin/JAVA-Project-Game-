package game;

public class BigJump {

    public double x, y;
    public boolean Collides(double x, double y){

        x=Math.abs(this.x-x);
        y=Math.abs(this.y-y);

        if( x<=25   && y<=90  )
        {
            return true;
        }
        return  false;
    }
    public int PrintCollides(double x, double y){

        x=Math.abs(this.x-x);
        y=Math.abs(this.y-y);
        System.out.println(x+" AND "+y);

        return 1;
    }

}
