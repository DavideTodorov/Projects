import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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
        //Frame details
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(40, 40, 40));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.add(textPanel, BorderLayout.NORTH);

        //TextField details
        textField.setBackground(new Color(20, 20, 20));
        textField.setForeground(Color.green);
        textField.setFont(new Font("Ink Free", Font.BOLD, 80));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        //TextPanel details
        textPanel.setLayout(new BorderLayout());
        textPanel.setBounds(0, 0, 800, 100);
        textPanel.add(textField);


    }


    //creates the first move
    public void firstTurn() {

    }


    //checks if there are win conditions
    public void checkForWin() {

    }


    //method to execute if X wins
    public void xWins(int a, int b, int c) {

    }


    //method to execute if O wins
    public void oWins(int a, int b, int c) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
