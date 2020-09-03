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
        frame.add(buttonPanel);

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

        //ButtonPanel details
        buttonPanel.setLayout(new GridLayout(3, 3));
//        buttonPanel.setBackground(Color.RED);

        //Buttons creation
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Ink Free", Font.BOLD, 120));
            buttons[i].setBackground(new Color(40, 40, 40));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        firstTurn();
    }


    //creates the first move
    public void firstTurn() {

        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int randomNum = random.nextInt(2);
        if (randomNum == 0) {
            firstPlayerTurn = true;
            textField.setText("X Turn");
        } else {
            firstPlayerTurn = false;
            textField.setText("0 Turn");
        }
    }


    //checks if there are win conditions
    public void checkForWin() {
        //check if X wins
        if (
                buttons[0].getText().equals("X") &&
                        buttons[1].getText().equals("X") &&
                        buttons[2].getText().equals("X")
        ) {
            xWins(0, 1, 2);
        }

        if (
                buttons[3].getText().equals("X") &&
                        buttons[4].getText().equals("X") &&
                        buttons[5].getText().equals("X")
        ) {
            xWins(3, 4, 5);
        }

        if (
                buttons[6].getText().equals("X") &&
                        buttons[7].getText().equals("X") &&
                        buttons[8].getText().equals("X")
        ) {
            xWins(6, 7, 8);
        }

        if (
                buttons[0].getText().equals("X") &&
                        buttons[3].getText().equals("X") &&
                        buttons[6].getText().equals("X")
        ) {
            xWins(0, 3, 6);
        }

        if (
                buttons[1].getText().equals("X") &&
                        buttons[4].getText().equals("X") &&
                        buttons[7].getText().equals("X")
        ) {
            xWins(1, 4, 7);
        }

        if (
                buttons[2].getText().equals("X") &&
                        buttons[5].getText().equals("X") &&
                        buttons[8].getText().equals("X")
        ) {
            xWins(2, 5, 8);
        }

        if (
                buttons[0].getText().equals("X") &&
                        buttons[4].getText().equals("X") &&
                        buttons[8].getText().equals("X")
        ) {
            xWins(0, 4, 8);
        }

        if (
                buttons[2].getText().equals("X") &&
                        buttons[4].getText().equals("X") &&
                        buttons[6].getText().equals("X")
        ) {
            xWins(2, 4, 6);
        }


        //check if O wins
        if (
                buttons[0].getText().equals("O") &&
                        buttons[1].getText().equals("O") &&
                        buttons[2].getText().equals("O")
        ) {
            oWins(0, 1, 2);
        }

        if (
                buttons[3].getText().equals("O") &&
                        buttons[4].getText().equals("O") &&
                        buttons[5].getText().equals("O")
        ) {
            oWins(3, 4, 5);
        }

        if (
                buttons[6].getText().equals("O") &&
                        buttons[7].getText().equals("O") &&
                        buttons[8].getText().equals("O")
        ) {
            oWins(6, 7, 8);
        }

        if (
                buttons[0].getText().equals("O") &&
                        buttons[3].getText().equals("O") &&
                        buttons[6].getText().equals("O")
        ) {
            oWins(0, 3, 6);
        }

        if (
                buttons[1].getText().equals("O") &&
                        buttons[4].getText().equals("O") &&
                        buttons[7].getText().equals("O")
        ) {
            oWins(1, 4, 7);
        }

        if (
                buttons[2].getText().equals("O") &&
                        buttons[5].getText().equals("O") &&
                        buttons[8].getText().equals("O")
        ) {
            oWins(2, 5, 8);
        }

        if (
                buttons[0].getText().equals("O") &&
                        buttons[4].getText().equals("O") &&
                        buttons[8].getText().equals("O")
        ) {
            oWins(0, 4, 8);
        }

        if (
                buttons[2].getText().equals("O") &&
                        buttons[4].getText().equals("O") &&
                        buttons[6].getText().equals("O")
        ) {
            oWins(2, 4, 6);
        }
    }


    //method to execute if X wins
    public void xWins(int a, int b, int c) {

    }


    //method to execute if O wins
    public void oWins(int a, int b, int c) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (firstPlayerTurn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(Color.red);
                        buttons[i].setText("X");
                        textField.setText("O turn");
                        firstPlayerTurn = false;
                        checkForWin();
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(Color.cyan);
                        buttons[i].setText("O");
                        textField.setText("X turn");
                        firstPlayerTurn = true;
                        checkForWin();
                    }
                }
            }
        }
    }
}
