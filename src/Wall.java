import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/****
 * Name: Sean Davies
 * Email: seandavies@unm.edu
 * Class: CS251L-003
 * Description: This is the Wall Object File.
 ****/

/**
 * This is the Wall Object Class
 */
public class Wall {
    protected LinkedList<Integer> wallPositions = new LinkedList();
    protected LinkedList<Integer> nonWalls = new LinkedList();
    protected static int width,height;

    /**
     * Wall constructor
     * @param wallPos this is the file of values from the
     *                original text file for the walls.
     */
    Wall(ArrayList<String> wallPos, int w, int h){
        if (wallPos.isEmpty()) {
            this.wallPositions = wallPositions;
        }
        else {
            makeWalls(wallPos, w, h);
            this.wallPositions = wallPositions;
            wallPositions.sort(Comparator.naturalOrder());
        }
        width = w;
        height = h;
        for (int i = 0; i < width * height; i++){
            if (!wallPositions.contains(i)){
                nonWalls.add(i);
            }
        }
    }

    /**
     * This is to make the walls for the game.
     * @param wallValues this is the original file of values from the text file.
     */
    void makeWalls(ArrayList<String> wallValues, int w, int h){
        Integer x1, x2, y1, y2;
        for (int i = 0; i < wallValues.size(); i = i + 4){
            x1 = Integer.valueOf(wallValues.get(i));
            x2 = Integer.valueOf(wallValues.get(i+2));
            y1 = Integer.valueOf(wallValues.get(i+1));
            y2 = Integer.valueOf(wallValues.get(i+3));
            //This is for horizontal walls with y = 0
            if (y1 == 0 && y2 == 0){
                for (int j = x1; j <= x2; j++){
                    wallPositions.add(j);
                }
            }
            //These are for horizontal Walls with y >= 1
            else if (y1 == y2){
                for (int j = 0; j <= (x2 - x1); j++){
                    if (wallPositions.contains((y1 * w) + (x1 + j))){
                    }
                    else {
                        wallPositions.add((y1 * w) + (x1 + j));
                    }
                }
            }
            //These are for Vertical Walls
            else if (x1 == x2){
                for (int j = y1; j <= (y2); j++){
                    if (wallPositions.contains((j * (w - 1)) + j + x1)){
                    }
                    else{
                        wallPositions.add((j * (w - 1)) + j + x1);
                    }
                }
            }
        }
    }


    /**
     * This is currently not needed for this lab. It is currently not used.
     * @param g Graphics to draw in the future.
     */
    void drawWall (Graphics g, int w, int h){
        Integer[] wallPos = new Integer[wallPositions.size()];
        for (int i = 0; i < wallPositions.size(); i++){
            wallPos[i] = wallPositions.get(i);
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < wallPositions.size();i++){
            g.fillRect(((wallPos[i]%w) * 10),
                    ((wallPos[i]/w) * 10),
                    10,10);
        }
    }

    /**
     * This is to find a random spot that isnt the walls.
     * @return An int that is a random spot not containing the wall.
     */
    int randomSpot (){
        int var1;
        Random rand = new Random();
        var1 = nonWalls.get(rand.nextInt(nonWalls.size()));
        return var1;
    }
}