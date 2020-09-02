import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel textPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel textField = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean firstPlayerTurn;


    //Constructor
    TicTacToe() {

    }


    //creates the first move
    public void firstTurn(){

    }


    //checks if there are win conditions
    public void checkForWin(){

    }


    //method to execute if X wins
    public void xWins(int a, int b, int c){

    }


    //method to execute if O wins
    public void oWins(int a, int b, int c){

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
