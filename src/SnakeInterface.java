import java.util.*;

/****
 * Description: This is the Interface for
 * the Snake Game.
 ****/

public interface SnakeInterface {

    /**
     * Initialize a New Game. Makes the original Board and
     * Clears if a new game is played
     * @param h Height of game
     * @param w Width of game
     */
    void initGame(int h, int w);

    /**
     * This is to check if the game is over.
     * @param s Snake being checked
     * @param w Wall being checked
     * @return true if game is over, false if otherwise
     */
    boolean isGameOver(Snake s, Wall w);

    /**
     * This is to get a String representation of the board.
     * @param s Snake being input
     * @param w Walls being input
     * @param f Food being input
     * @return Returns a string representation of the board.
     */
    String getMap(Snake s, Wall w, Food f);

    /**
     * This is to get the current snake size.
     * @param s Snake being checked
     * @return Integer value of snake size.
     */
    Integer snakeSize(Snake s);

    /**
     * This is to get the snake positions on the board.
     * @param s Snake being checked
     * @return a Deque of the Snakes position.
     */
    Deque snakePosition(Snake s);

    /**
     * This is to get the wall positions on the board.
     * @param w Walls being checked
     * @return a list of the walls positions.
     */
    LinkedList wallPosition(Wall w);

    /**
     * This is to get the food position on the board.
     * @param f Food being checked
     * @return an Integer value of where the food is at.
     */
    Integer foodPosition(Food f);

    /**
     * This is to check if the snake has collided with the food.
     * @param s Snake being checked
     * @param f Food being checked
     */
    void collisionFood(Snake s, Food f, Wall w);

    /**
     * This is to check if the snake has collided with a wall
     * @param s Snake being checked
     * @param w Wall being checked
     * @return True if the snake has collided with the wall, false if otherwise.
     */
    boolean collisionWall(Snake s, Wall w);

    /**
     * This is to check if the snake has collided with itself.
     * @param s Snake being checked
     * @return True if the snake has collided with itself, false if otherwise.
     */
    boolean collisionSelf(Snake s);

    /**
     * This is to check if either snake in the two player game mode have
     * collided with each other at any point and makes the game end.
     * @param s1 This is the first player snake
     * @param s2 This is the second player snake
     * @return True if the snakes have collided, false if otherwise.
     */
    boolean collisionSecondSnake (Snake s1, Snake s2);
}
