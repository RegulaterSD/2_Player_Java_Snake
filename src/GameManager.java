/****
 * Name: Sean Davies
 * Email: seandavies@unm.edu
 * Class: CS251L-003
 * Description: This is the Game Manager for
 * the Snake Lab.
 ****/

import java.util.*;

/**
 * This is the Game Manager Class
 */
public class GameManager implements SnakeInterface {
    protected final String BLANK = "-";
    protected final String SNAKE = "S";
    protected final String WALL = "W";
    protected final String FOOD = "F";
    protected static int height;
    protected static int width;
    protected static StringBuilder mapString = new StringBuilder(
            width * height);
    protected static StringBuilder mapStringLined;
    protected static LinkedList<Integer> snakePosi = new LinkedList<>();


    /**
     * Initialize a New Game. Makes the original Board and Clears if a new game is played
     * @param h Height of game
     * @param w Width of game
     */
    public void initGame(int h, int w) {
        height = h;
        width = w;
        for (int i = 0; i < height * width; i++){
            mapString.append(BLANK);
        }
        clearBoard();
    }


    /**
     * This is to check if the game is over.
     * @param s Snake being checked
     * @param w Wall being checked
     * @return true if game is over, false if otherwise
     */
    public boolean isGameOver(Snake s, Wall w) {
        if (!collisionSelf(s) && !collisionWall(s,w)) {
            return false;
        }
        else {
            //System.out.println("Game is Over");
            return true;
        }
    }

    /**
     * This is to clear the board for a new game.
     */
    public void clearBoard(){
        mapString.delete(0,mapString.length());
        for (int i = 0; i < height * width; i++){
            mapString.append(BLANK);
        }
    }


    /**
     * This is to get a String representation of the board.
     * @param s Snake being input
     * @param w Walls being input
     * @param f Food being input
     * @return Returns a string representation of the board.
     */
    public String getMap(Snake s, Wall w, Food f) {
        clearBoard();
        Integer temp1;
        for (int i = 0; i < s.snakePosition.size(); i++){
            temp1 = Integer.valueOf(String.valueOf(s.snakePosition.pop()));
            snakePosi.addFirst(temp1);
            s.snakePosition.addLast(temp1);
        }
        for (int i = 0; i < snakePosi.size(); i++) {
            int temp = (int) snakePosi.get(i);
            mapString.replace(temp,temp+1,SNAKE);
        }
        snakePosi.clear();
        for (int i = 0; i < w.wallPositions.size(); i++) {
            int temp = w.wallPositions.get(i);
            mapString.replace(temp,temp+1,WALL);
        }
        mapString.replace(f.position,f.position + 1,FOOD);
        mapStringLined = mapString;
        for (int i = 0; i < height - 1; i++){
            mapStringLined.insert((((i*(width))
                    + (i*1))+(width)),"\n");
        }
        return mapStringLined.toString();
    }

    /**
     * This is to get the current snake size.
     * @param s Snake being checked
     * @return Integer value of snake size.
     */
    public Integer snakeSize(Snake s) {
        return s.snakePosition.size();
    }


    /**
     * This is to get the snake positions on the board.
     * @param s Snake being checked
     * @return a Deque of the Snakes position.
     */
    public Deque snakePosition(Snake s) {
        return s.snakePosition;
    }


    /**
     * This is to get the wall positions on the board.
     * @param w Walls being checked
     * @return a list of the walls positions.
     */
    public LinkedList wallPosition(Wall w) {
        return w.wallPositions;
    }


    /**
     * This is to get the food position on the board.
     * @param f Food being checked
     * @return an Integer value of where the food is at.
     */
    public Integer foodPosition(Food f) {
        return f.position;
    }


    /**
     * This is to check if the snake has collided with the food.
     * @param s Snake being checked
     * @param f Food being checked
     */
    public void collisionFood(Snake s, Food f, Wall w) {
        if (s.snakePosition.peekFirst() == f.position){
            int temp = 0;
            temp = (int) s.snakePosition.peekLast();
            for (int i = 0; i < 3; i++){
                s.snakePosition.addLast(temp);
            }
            f.spawnFood(w.randomSpot());
        }

    }

    /**
     * This is to check if the snake has collided with a wall
     * @param s Snake being checked
     * @param w Wall being checked
     * @return True if the snake has collided with the wall, false if otherwise.
     */
    public boolean collisionWall(Snake s, Wall w) {
        if (w.wallPositions.contains(s.snakePosition.peekFirst())){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This is to check if the snake has collided with itself.
     * @param s Snake being checked
     * @return True if the snake has collided with itself, false if otherwise.
     */
    public boolean collisionSelf(Snake s) {
        int tempvar = s.snakePosition.peekFirst();
        if (s.snakePosition.contains(tempvar) && s.snakePosition.size() != 3){
            int var1 = 0;
            for (Object o : s.snakePosition) {
                if (tempvar == Integer.valueOf(String.valueOf(o))){
                    var1++;
                }
            }
            if (var1 > 1){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * This is to check if either snake in the two player game mode have
     * collided with each other at any point and makes the game end.
     * @param s1 This is the first player snake
     * @param s2 This is the second player snake
     * @return True if the snakes have collided, false if otherwise.
     */
    public boolean collisionSecondSnake (Snake s1, Snake s2){
        int tempvar1 = s1.snakePosition.peekFirst();
        int tempvar2 = s2.snakePosition.peekFirst();
        if (s1.snakePosition.contains(tempvar2) || s2.snakePosition.contains(tempvar1)){
            return true;
        }
        else {
            return false;
        }
    }
}