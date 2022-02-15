import acm.graphics.GRect;
import java.awt.Color;

public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public int hits;

    public Brick(double x, double y, Color color, int row){
        super(x,y,WIDTH, HEIGHT);
        this.setFillColor(color);
        this.setFilled(true);
        // give the bricks some sort of value and change how many hits it takes to destroy
        switch (row){
            case 9, 8:{
                hits =1;
                break;
            }
            case 7, 6:{
                hits = 2;
                break;
            }
            case 5,4:{
                hits = 3;
                break;
            }
            case 3,2:{
                hits = 4;
                break;

            }
            case 0,1:{
                hits = 5;
                break;
            }
        }

    }
    public void hits(){
        hits -=1;

    }


}