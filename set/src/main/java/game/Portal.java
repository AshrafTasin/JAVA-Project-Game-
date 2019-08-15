package game;

public class Portal {

    public int x,y;
    Portal(){
        x=y=0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean matchPort(double x, double y){

        x=Math.abs(this.x-x);
        y=Math.abs(this.y-y);
        if( x<=20 && y<=20  )
        {
            return true;
        }
        return  false;
    }
}
