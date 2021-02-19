import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    //variables
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private static int delay = 130;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten;
    private int appleXCoordinates;
    private int appleYCoordinates;
    private String direction = "Right";
    private boolean isRunning = false;
    private Timer timer;
    private Random random;


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        StartGame();
    }

    //method to start the game
    public void StartGame() {
        addApple();
        isRunning = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    //method to paint the components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //method to draw components
    public void draw(Graphics g) {

        if (isRunning) {
            //draw grid
            /*
            for (int i = unitSize; i < 600; i += unitSize) {
                g.drawLine(i, 0, i, 600);
            }
            for (int i = unitSize; i < 600; i += unitSize) {
                g.drawLine(0, i, 600, i);
            }
           */

            //draw apple
            if (applesEaten % 7 == 0 && applesEaten != 0) {
                g.setColor(Color.yellow);
                g.fillOval(appleXCoordinates, appleYCoordinates, UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(Color.red);
                g.fillOval(appleXCoordinates, appleYCoordinates, UNIT_SIZE, UNIT_SIZE);
            }

            //draw snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 255, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(130, 255, 185));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            //show score during game
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    SCREEN_HEIGHT - 560);

        } else {
            gameOver(g);
        }
    }

    //method to add apple
    public void addApple() {
        boolean wrongCoordinates = false;
        do {
            //creates random X and Y coordinates
            appleXCoordinates = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleYCoordinates = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            //validates X coordinates
            for (int i = 0; i < x.length; i++) {
                int currX = x[i];

                if (currX == appleXCoordinates) {
                    wrongCoordinates = true;
                    break;
                } else {
                    wrongCoordinates = false;
                }
            }

            //validates Y coordinates
            for (int i = 0; i < y.length; i++) {
                int currY = y[i];

                if (currY == appleYCoordinates) {
                    wrongCoordinates = true;
                    break;
                } else {
                    wrongCoordinates = false;
                }
            }

        } while (wrongCoordinates);
    }

    //method to move the components
    public void move() {

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case "Up":
                y[0] = y[0] - UNIT_SIZE;
                break;
            case "Down":
                y[0] = y[0] + UNIT_SIZE;
                break;
            case "Right":
                x[0] = x[0] + UNIT_SIZE;
                break;
            case "Left":
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    //method to check for eaten apple
    public void checkApple() {
        if ((x[0] == appleXCoordinates) && (y[0] == appleYCoordinates)) {
            if (applesEaten % 7 == 0 && applesEaten != 0) {
                applesEaten += 2;
                bodyParts++;
                addApple();
            } else {
                applesEaten++;
                bodyParts++;
                addApple();
            }

            if (applesEaten % 10 == 0 && applesEaten != 0) {
                delay -= 5;
            }
        }
    }

    //method to check for collision
    public void checkCollision() {

        //collision with the body -> gameOver
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                isRunning = false;
            }
        }

        //collision with border -> gameOver
        if (x[0] > SCREEN_WIDTH) {
            isRunning = false;
        }
        if (x[0] < 0) {
            isRunning = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            isRunning = false;
        }
        if (y[0] < 0) {
            isRunning = false;
        }

        if (!isRunning) {
            timer.stop();
        }
    }

    //method to end the game
    public void gameOver(Graphics g) {
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);

        //Score text
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2,
                SCREEN_HEIGHT - 560);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isRunning) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    //method for pressed keys
    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!direction.equals("Right")) {
                        direction = "Left";
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!direction.equals("Left")) {
                        direction = "Right";
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!direction.equals("Down")) {
                        direction = "Up";
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!direction.equals("Up")) {
                        direction = "Down";
                    }
                    break;
            }
        }
    }
}