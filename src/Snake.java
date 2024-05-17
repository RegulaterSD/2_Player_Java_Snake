import java.awt.*;
import java.util.*;

/****
 * Description: This is the Snake Object
 * File.
 ****/

/**
 * This is the Snake Object Class
 */
public class Snake {
    protected Color snakeHeadColor;
    protected Color snakeBodyColor;
    protected int lastKeyCode = 0;
    protected int width;
    protected int height;
    protected int up, down, left, right;
    protected Deque<Integer> snakePosition = new LinkedList();

    /**
     * This is the Snake constructor
     * @param snakeLength Snake Length desired to start
     * @param randomPosition Initial position of the snake
     * @param head Color of the Snake Head
     * @param body Color of the Snake Body
     * @param width Width of the map
     * @param height Height of the map
     */
    Snake(int snakeLength, int randomPosition,
          Color head, Color body, int width, int height,
          int up, int down, int left, int right) {
        this.snakeHeadColor = head;
        this.snakeBodyColor = body;
        this.width = width;
        this.height = height;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        for (int i = 1; i < snakeLength; i++) {
            this.snakePosition.addLast(randomPosition);
        }
        snakePosition.addFirst(randomPosition);
    }


    /**
     * This is for the snake to move on the map
     * @param keyCode This is the keycode of the key pressed
     * @param gameOver If the game is still active or not
     */
    void move(int keyCode, boolean gameOver, Snake s) {
        if (!gameOver) {
            int tempvar = 0;
            //Left Arrow
            if (keyCode == s.left && lastKeyCode != s.right) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar % width == 1) {
                    this.snakePosition.addFirst((tempvar - 1) + width);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.left;
                } else {
                    this.snakePosition.addFirst((tempvar - 1));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.left;
                }
                //Up Arrow
            } else if (keyCode == s.up && lastKeyCode != s.down) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar / width == 0) {
                    this.snakePosition.addFirst(
                            (width * height) - (20 - tempvar));
                    this.snakePosition.addFirst(150);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.up;
                } else {
                    this.snakePosition.addFirst((tempvar - width));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.up;
                }
                //Right Arrow
            } else if (keyCode == s.right && lastKeyCode != s.left) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar % width == 0) {
                    this.snakePosition.addFirst((tempvar + 1) - width);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.right;
                } else {
                    this.snakePosition.addFirst((tempvar + 1));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.right;
                }
                //Down Arrow
            } else if (keyCode == s.down && lastKeyCode != s.up) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar / width == (height - 1)) {
                    this.snakePosition.addFirst(
                            (20 + tempvar) - (width * height));
                    this.snakePosition.addFirst(tempvar + 20);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.down;
                } else {
                    this.snakePosition.addFirst((tempvar + width));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.down;
                }
            }
            //left with last keycode right continue going last
            // keycode direction which is right.
            else if (keyCode == s.left && lastKeyCode == s.right) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar % width == 0) {
                    this.snakePosition.addFirst((tempvar + 1) - width);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.right;
                } else {
                    this.snakePosition.addFirst((tempvar + 1));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.right;
                }
                //up with last keycode down continue going last
                //keycode direction which is down
            } else if (keyCode == s.up && lastKeyCode == s.down) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar / width == (height - 1)) {
                    this.snakePosition.addFirst(
                            (20 + tempvar) - (width * height));
                    this.snakePosition.addFirst(tempvar + 20);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.down;
                } else {
                    this.snakePosition.addFirst((tempvar + width));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.down;
                }
                //Right with last keycode left continue going last
                //keycode direction which is left
            } else if (keyCode == s.right && lastKeyCode == s.left) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar % width == 1) {
                    this.snakePosition.addFirst((tempvar - 1) + width);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.left;
                } else {
                    this.snakePosition.addFirst((tempvar - 1));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.left;
                }
                //Down with last keycode up continue going last
                //keycode direction which is up
            } else if (keyCode == s.down && lastKeyCode == s.up) {
                tempvar = (int) snakePosition.peekFirst();
                if (tempvar / width == 0) {
                    this.snakePosition.addFirst(
                            (width * height) - (20 - tempvar));
                    this.snakePosition.addFirst(150);
                    this.snakePosition.removeLast();
                    lastKeyCode = s.up;
                } else {
                    this.snakePosition.addFirst((tempvar - width));
                    this.snakePosition.removeLast();
                    lastKeyCode = s.up;
                }
            }
            else {

            }
        }
    }

    /**
     * This is to draw the snake on the next part. Currently
     * not needed for this lab.
     * @param g This is the graphics
     */
    void drawSnake(Graphics g) {
        Integer[] snakePos = new Integer[snakePosition.size()];
        for (int i = 0; i < snakePosition.size(); i++){
            snakePos[i] = snakePosition.peekFirst();
            snakePosition.pop();
            snakePosition.addLast(snakePos[i]);
        }
        g.setColor(snakeBodyColor);
        for (int i = 0; i < snakePos.length; i++) {
            g.fillRect((((snakePos[i].intValue() % width)) * 10),
                    ((snakePos[i].intValue() / width) * 10),
                    10, 10);
        }
    }
}
