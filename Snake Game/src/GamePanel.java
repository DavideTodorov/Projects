import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.IllegalFormatCodePointException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    static final int delay = 120;
    final int x[] = new int[gameUnits];
    final int y[] = new int[gameUnits];
    int bodyParts = 6;
    int applesEaten;
    int appleXCoordinates;
    int appleYCoordinates;
    String direction = "Right";
    boolean isRunning = false;
    Timer timer;
    Random random;


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
            for (int i = unitSize; i < 600; i += unitSize) {
                g.drawLine(i, 0, i, 600);
            }
            for (int i = unitSize; i < 600; i += unitSize) {
                g.drawLine(0, i, 600, i);
            }

            //draw apple
            g.setColor(Color.red);
            g.fillOval(appleXCoordinates, appleYCoordinates, unitSize, unitSize);

            //draw snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 255, 0));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    g.setColor(new Color(130, 255, 185));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
        } else {
            gameOver(g);
        }
    }

    //method to add apple
    public void addApple() {
        appleXCoordinates = random.nextInt((int) (screenWidth / unitSize)) * unitSize;
        appleYCoordinates = random.nextInt((int) (screenHeight / unitSize)) * unitSize;
    }

    //method to move the components
    public void move() {

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case "Up":
                y[0] = y[0] - unitSize;
                break;
            case "Down":
                y[0] = y[0] + unitSize;
                break;
            case "Right":
                x[0] = x[0] + unitSize;
                break;
            case "Left":
                x[0] = x[0] - unitSize;
                break;
        }
    }

    //method to check for eaten apple
    public void checkApple() {
        if ((x[0] == appleXCoordinates) && (y[0] == appleYCoordinates)) {
            applesEaten++;
            bodyParts++;
            addApple();
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
        if (x[0] > screenWidth) {
            isRunning = false;
        }
        if (x[0] < 0) {
            isRunning = false;
        }
        if (y[0] > screenHeight) {
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
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over"))/2,
                screenHeight / 2);
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