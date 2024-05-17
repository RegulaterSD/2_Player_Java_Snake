import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/****
 * Name: Sean Davies
 * Email: seandavies@unm.edu
 * Class: CS251L-003
 * Description: This is the Game Panel which allows
 * the game to be played.
 ****/

/**
 * This is the main class for Game Panel
 */
public class GamePanel extends JFrame implements KeyListener, ActionListener {
    protected static int keyCode1, keyCode2;
    protected static String fileName = null;
    protected static List<String> inputs = new ArrayList();
    protected static ArrayList<String> wallValues = new ArrayList();
    protected static Integer width;
    protected static Integer height;
    protected static Color snakeColor1 = Color.green;
    protected static Color snakeColor2 = Color.red;
    protected static boolean running = false;
    protected static boolean over = false;
    protected static String[] options1 = {"Hard", "Medium", "Easy"};
    protected static String[] options2 = {"Cyan", "Green", "Red"};
    protected static int delay = 400;
    protected static int[] speed = {40,100,250};
    protected static final SnakeInterface game1 = new GameManager();
    protected static Snake snake1,snake2;
    protected static Wall walls1;
    protected static Food food1;
    protected static JLabel label1 = new JLabel("You are Alive");
    protected static JLabel label3 = new JLabel("");
    protected static JLabel label4 = new JLabel("");
    protected static JButton button1 = new JButton("Play Game");
    protected static JOptionPane pane1 = new JOptionPane();
    protected static boolean twoPlayer = false;
    protected static String[] playerOptions = {"Two", "One"};
    protected static Color playerTwoColor = Color.lightGray;


    /**
     * This is the Game Panel method which allows the game to be made.
     * It displays the whole game and allows it to run along with interface
     * buttons and options.
     * @throws Exception
     */
    public GamePanel () throws Exception {
        super("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel frame1 = new JPanel(){
            public void paintComponent (Graphics frame1){
                super.paintComponent(frame1);
                game1.initGame(height,width);
                walls1.drawWall(frame1, width, height);
                snake1.drawSnake(frame1);
                snake2.drawSnake(frame1);
                food1.drawFood(frame1);
            }
        };
        Timer timer = new Timer(delay, this);
        frame1.setPreferredSize(new Dimension(width * 10,height * 10));
        frame1.setFocusable(true);
        frame1.addKeyListener(this);
        frame1.setBackground(Color.lightGray);

        //Adding Snake Noises while game is running
        URL file = this.getClass().getResource("SnakeSound.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);

        JLabel label2 = new JLabel("Game is Paused");

        //This is for the Play/Pause Button
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!running){
                    running = true;
                    timer.start();
                    button1.setText("Pause Game");
                    label2.setText("Game is Running");
                    frame1.requestFocus();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    repaint();
                }
                else if (running){
                    running = false;
                    timer.stop();
                    button1.setText("Resume Game");
                    label2.setText("Game is Paused");
                    clip.stop();
                    repaint();
                }
            }
        });

        //This is the restart button
        JButton button2 = new JButton("Restart Game");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int var1 = walls1.randomSpot();
                int var2 = walls1.randomSpot();
                game1.initGame(height,width);
                snake1.snakePosition.clear();
                snake1.snakePosition.addFirst(var1);
                snake1.snakePosition.addLast(var1);
                snake1.snakePosition.addLast(var1);
                snake2.snakePosition.clear();
                snake2.snakePosition.addFirst(var2);
                snake2.snakePosition.addLast(var2);
                snake2.snakePosition.addLast(var2);
                food1.spawnFood(walls1.randomSpot());
                repaint();
                keyCode1 = 0;
                snake1.lastKeyCode = 0;
                keyCode2 = 0;
                snake2.lastKeyCode = 0;
                label3.setText("");
                label4.setText("");
                frame1.requestFocus();
            }
        });

        //This is where I added a Two Player option
        //Used a method where the second snake is invisible until two Player
        //Mode is enabled and then when Two Player mode is enabled the second
        //Player snake becomes visible and playable.
        JPanel frame6 = new JPanel();
        frame6.setBorder(BorderFactory.createTitledBorder("Player Count"));
        ButtonGroup buttons6 = new ButtonGroup();
        for (int i = 0; i < playerOptions.length; i++){
            String s3 = playerOptions[i];
            JRadioButton rbutton6 = new JRadioButton(s3);
            frame6.add(rbutton6);
            buttons6.add(rbutton6);
            if (s3.equals(playerOptions[0])) {
                rbutton6.setSelected(true);
            } else if (s3.equals(playerOptions[1])) {
                rbutton6.setSelected(true);
            }
            rbutton6.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (s3.equals(playerOptions[0])) {
                        twoPlayer = true;
                        snake2.snakeBodyColor = Color.BLUE;
                        repaint();
                        frame1.requestFocus();
                    }
                    else if (s3.equals(playerOptions[1])){
                        twoPlayer = false;
                        snake2.snakeBodyColor = Color.lightGray;
                        repaint();
                        frame1.requestFocus();
                    }
                }
            });
        }

        //This is the difficulty which changes the speed the snake moves.
        JPanel frame2 = new JPanel();
        frame2.setBorder(BorderFactory.createTitledBorder("Select Difficulty"));
        ButtonGroup buttons1 = new ButtonGroup();
        for (int i = 0; i < 3; i++){
            String s1 = options1[i];
            JRadioButton rbutton1 = new JRadioButton(s1);
            frame2.add(rbutton1);
            buttons1.add(rbutton1);
            if (s1.equals(options1[0])) {
                rbutton1.setSelected(true);
            } else if (s1.equals(options1[1])){
                rbutton1.setSelected(true);
            } else if (s1.equals(options1[2])) {
                rbutton1.setSelected(true);
            }
            rbutton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (s1.equals(options1[0])) {
                        delay = speed[0];
                        timer.setDelay(delay);
                        frame1.requestFocus();
                    }
                    else if (s1.equals(options1[1])){
                        delay = speed[1];
                        timer.setDelay(delay);
                        frame1.requestFocus();
                    } else if (s1.equals(options1[2])) {
                        delay = speed[2];
                        timer.setDelay(delay);
                        frame1.requestFocus();
                    }
                }
            });
        }

        //This is to give the snake different colors.
        JPanel frame3 = new JPanel();
        frame3.setBorder(BorderFactory.createTitledBorder(
                "Select Snake Body Color"));
        ButtonGroup buttons2 = new ButtonGroup();
        for (int i = 0; i < 3; i++){
            String s2 = options2[i];
            JRadioButton rbutton2 = new JRadioButton(s2);
            frame3.add(rbutton2);
            buttons2.add(rbutton2);
            if (s2.equals(options2[0])) {
                rbutton2.setSelected(true);
            }
            else if (s2.equals(options2[1])){
                rbutton2.setSelected(true);
            } else if (s2.equals(options2[2])) {
                rbutton2.setSelected(true);
            }
            rbutton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (s2.equals(options2[0])) {
                        snake1.snakeBodyColor = Color.CYAN;
                        frame1.repaint();
                        frame1.requestFocus();
                    }
                    else if (s2.equals(options2[1])){
                        snake1.snakeBodyColor = Color.GREEN;
                        frame1.repaint();
                        frame1.requestFocus();
                    } else if (s2.equals(options2[2])) {
                        snake1.snakeBodyColor = Color.RED;
                        frame1.repaint();
                        frame1.requestFocus();
                    }
                }
            });
        }

        //To build the entire GUI
        JPanel frame5 = new JPanel();
        frame5.setBorder(BorderFactory.createEmptyBorder(
                20,20,20,20));
        frame5.setLayout(new BoxLayout(frame5,1));
        frame5.add(label3);
        frame5.add(label4);
        frame5.add(label1);
        frame5.add(label2);
        frame5.add(Box.createVerticalGlue());
        frame5.add(button1);
        frame5.add(button2);
        frame5.add(Box.createVerticalGlue());
        frame5.add(frame2);
        frame5.add(Box.createVerticalGlue());
        frame5.add(frame3);
        frame5.add(frame6);
        this.getContentPane().add(frame1,BorderLayout.CENTER);
        this.getContentPane().add(frame5, BorderLayout.AFTER_LINE_ENDS);
        this.pack();
    }

    /**
     * This is the main method for the Game Panel. It grabs the command
     * line argument and builds the game.
     * @param args The command line arguments to build the walls.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        URL fileName;
        String file;
        if (args.length == 1) {
            file = args[0];
            fileName = GamePanel.class.getResource(file);
        } else {
            file = "Simple.txt";
            fileName = GamePanel.class.getResource(file);
            System.out.println("No filename detected, will run default Simple");
        }
        try (
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(fileName.openStream()))) {
            String word;
            String[] words;
            while ((word = in.readLine()) != null) {
                inputs.add(word);
            }
            for (String str : inputs) {
                word = str;
                words = word.split(" ");
                for (int i = 0; i < words.length; i++) {
                    wallValues.add(words[i]);
                }
            }
            width = Integer.valueOf(wallValues.get(0));
            height = Integer.valueOf(wallValues.get(1));
            wallValues.remove(0);
            wallValues.remove(0);
        }


        //Spawn the walls, snake and Food.
        walls1 = new Wall(wallValues, width, height);
        snake1 = new Snake(3,walls1.randomSpot(),
                snakeColor1,snakeColor2,width,height, 38, 40, 37, 39);
        snake2 = new Snake(3,walls1.randomSpot(),
                playerTwoColor,playerTwoColor,width,height,87,83,65,69);
        food1 = new Food(walls1.randomSpot(), width, height);

        //Invoke later to use a GUI thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GamePanel panel = null;
                try {
                    panel = new GamePanel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                panel.setVisible(true);
                panel.setLocationRelativeTo(null);
                panel.setFocusable(true);
                panel.setResizable(false);
                panel.addKeyListener(this);
            }
        });
    }

    /**
     * This is the key listener which allows the snake to move.
     * @param runnable
     */
    private void addKeyListener(Runnable runnable) {
        super.addKeyListener(this);
    }

    /**
     * This is the key typed method to grab the keycode.
     * @param e Event
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //keyCode = e.getKeyCode();
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'a' ||
                e.getKeyChar() == 's' || e.getKeyChar() == 'd' ||
                e.getKeyChar() == 'W' || e.getKeyChar() == 'A' ||
                e.getKeyChar() == 'S' || e.getKeyChar() == 'D'){
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W'){
                keyCode2 = 87;
            }
            else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S'){
                keyCode2 = 83;
            }
            else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
                keyCode2 = 65;
            }
            else {
                keyCode2 = 69;
            }
        }
        else if (e.getKeyCode() == 37 || e.getKeyCode() == 38 ||
                e.getKeyCode() == 39 || e.getKeyCode() == 40){
            keyCode1 = e.getKeyCode();
        }
    }

    /**
     * This is the key pressed method to get the keycode.
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'a' ||
                e.getKeyChar() == 's' || e.getKeyChar() == 'd' ||
                e.getKeyChar() == 'W' || e.getKeyChar() == 'A' ||
                e.getKeyChar() == 'S' || e.getKeyChar() == 'D'){
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W'){
                keyCode2 = 87;
            }
            else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S'){
                keyCode2 = 83;
            }
            else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A'){
                keyCode2 = 65;
            }
            else {
                keyCode2 = 69;
            }
        }
        else if (e.getKeyCode() == 37 || e.getKeyCode() == 38 ||
                e.getKeyCode() == 39 || e.getKeyCode() == 40){
            keyCode1 = e.getKeyCode();
        }
    }

    /**
     * This is the key released method which isn't currently used.
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * This is the action performed method which is based upon the timer.
     * It moves the snake, checks for collisions, and has the death pop up
     * and death music.
     * @param e Event
     */
    public void actionPerformed(ActionEvent e) {
        if (!twoPlayer && running) {
            snake1.move(keyCode1, game1.isGameOver(snake1, walls1), snake1);
            game1.collisionFood(snake1, food1, walls1);
            label3.setText("Your Score: " + (game1.snakeSize(snake1) - 3));
            label4.setText("");
            this.pack();
            if (game1.isGameOver(snake1, walls1)) {
                over = true;
                label1.setText("Game is Over");

                //Was not allowing me to throw exceptions so had to use try catch.
                //This is the death noise when you collide.
                URL file1 = null;
                file1 = this.getClass().getResource("DeathSound.wav");
                AudioInputStream ais1 = null;
                try {
                    ais1 = AudioSystem.getAudioInputStream(file1);
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Clip clip1 = null;
                try {
                    clip1 = AudioSystem.getClip();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
                try {
                    clip1.open(ais1);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clip1.start();
                button1.doClick();
                JOptionPane.showMessageDialog(pane1,
                        "You have died! Your score is : "
                                + (game1.snakeSize(snake1) - 3),
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            repaint();
        }
        //Adding in two player movement
        else if (twoPlayer && running){
            snake1.move(keyCode1, game1.isGameOver(snake1, walls1), snake1);
            snake2.move(keyCode2,game1.isGameOver(snake2,walls1),snake2);
            game1.collisionFood(snake1, food1, walls1);
            game1.collisionFood(snake2,food1,walls1);
            label3.setText("Player One Score is: "
                    + (game1.snakeSize(snake1) - 3));
            label4.setText("Player Two Score is: " +
                    (game1.snakeSize(snake2) - 3));
            this.pack();
            if (game1.isGameOver(snake1, walls1) || game1.isGameOver(snake2,walls1) ||
                    game1.collisionSecondSnake(snake1,snake2)) {
                over = true;
                label1.setText("Game is Over");

                //Was not allowing me to throw exceptions so had to use try catch.
                //This is the death noise when you collide.
                URL file1 = null;
                file1 = this.getClass().getResource("DeathSound.wav");
                AudioInputStream ais1 = null;
                try {
                    ais1 = AudioSystem.getAudioInputStream(file1);
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Clip clip1 = null;
                try {
                    clip1 = AudioSystem.getClip();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
                try {
                    clip1.open(ais1);
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clip1.start();
                button1.doClick();
                JOptionPane.showMessageDialog(pane1,
                        "You have died!" + "\n" + "Player One Score is: "
                                + (game1.snakeSize(snake1) - 3) + "\n" + "Player Two Score is: " +
                                (game1.snakeSize(snake2) - 3),
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            repaint();
        }
    }

}
