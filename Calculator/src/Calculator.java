import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {

    private JFrame frame = new JFrame("Calculator");

    private JTextArea textScreen = new JTextArea();

    //Buttons
    private JButton button0 = new JButton("0");
    private JButton button1 = new JButton("1");
    private JButton button2 = new JButton("2");
    private JButton button3 = new JButton("3");
    private JButton button4 = new JButton("4");
    private JButton button5 = new JButton("5");
    private JButton button6 = new JButton("6");
    private JButton button7 = new JButton("7");
    private JButton button8 = new JButton("8");
    private JButton button9 = new JButton("9");

    private JButton divideButton = new JButton("/");
    private JButton multiplyButton = new JButton("*");
    private JButton subtractButton = new JButton("-");
    private JButton addButton = new JButton("+");

    //Variables
    String firstNum = "";
    String secondNum = "";
    String[] statement;
    int numOne = 0;
    int numTwo = 0;
    String result;

    public Calculator() {
        //Frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);

        //Text screen
        textScreen.setSize(420, 100);
        textScreen.setLocation(6, 5);
        textScreen.setEditable(false);

        //Add objects to screen
        frame.add(textScreen);
        frame.add(button0);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);
        frame.add(button7);
        frame.add(button8);
        frame.add(button9);

        frame.add(divideButton);
        frame.add(multiplyButton);
        frame.add(subtractButton);
        frame.add(addButton);

        //button0
        button0.setSize(100,50);
        button0.setLocation(115, 305);
        button0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("0");
            }
        });

        //button1
        button1.setSize(100, 50);
        button1.setLocation(10, 250);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("1");
            }
        });

        //button2
        button2.setSize(100, 50);
        button2.setLocation(115, 250);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("2");
            }
        });

        //button3
        button3.setSize(100, 50);
        button3.setLocation(220, 250);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("3");
            }
        });

        //button4
        button4.setSize(100, 50);
        button4.setLocation(10, 190);
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("4");
            }
        });

        //button5
        button5.setSize(100, 50);
        button5.setLocation(115, 190);
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("5");
            }
        });

        //button6
        button6.setSize(100, 50);
        button6.setLocation(220, 190);
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("6");
            }
        });

        //button7
        button7.setSize(100, 50);
        button7.setLocation(10, 130);
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("7");
            }
        });

        //button8
        button8.setSize(100, 50);
        button8.setLocation(115, 130);
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("8");
            }
        });

        //button9
        button9.setSize(100, 50);
        button9.setLocation(220, 130);
        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("9");
            }
        });

        //buttonDivide
        divideButton.setSize(100, 50);
        divideButton.setLocation(325, 130);
        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("/");
            }
        });

        //buttonMultiply
        multiplyButton.setSize(100, 50);
        multiplyButton.setLocation(325, 190);
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("*");
            }
        });

        //buttonSubtract
        subtractButton.setSize(100, 50);
        subtractButton.setLocation(325, 250);
        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("-");
            }
        });

        //buttonAdd
        addButton.setSize(100, 50);
        addButton.setLocation(325, 305);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textScreen.append("+");
            }
        });
    }

    public static void main(String[] args) {
        new Calculator();
    }
}