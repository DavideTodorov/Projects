import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    static final int delay = 100;
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

    
    GamePanel(){

    }

    //method to start the game
    public void StartGame(){

    }

    //method to paint the components
    public void paintComponent(Graphics g){

    }

    //method to draw components
    public void draw(Graphics g){

    }

    //method to move the components
    public void move(){

    }

    //method to check for eaten apple
    public void checkApple(){

    }

    //method to check for collision
    public void checkCollision(){

    }

    //method to end the game
    public void GameOver(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    //method for pressed keys
    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
