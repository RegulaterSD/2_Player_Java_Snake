import java.awt.*;

/****
 * Name: Sean Davies
 * Email: seandavies@unm.edu
 * Class: CS251L-003
 * Description: This is the Food Object File.
 ****/

/**
 * This is the Food Object Class
 */
public class Food {
    protected int position;
    protected static int width, height;

    /**
     * This is the food constructor
     * @param random This is the number where the food spawns at
     */
    Food(int random, int w, int h){
        this.position = random;
        width = w;
        height = h;
    }

    /**
     * To spawn new food after previous is ate.
     * @param random This is the number where the food spawns at.
     */
    void spawnFood (int random){
        this.position = random;
    }


    /**
     * Currently not needed for this lab.
     * @param g Graphics to draw.
     */
    void drawFood(Graphics g){
        g.setColor(Color.pink);
        g.fillRect((((position%width))*10),
                ((position/width)*10),
                10,10);
    }
}