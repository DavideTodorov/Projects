import javax.swing.*;

public class Calculator {

    private JFrame frame = new JFrame("Calculator");

    public Calculator(){
        //Frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);

    }

    public static void main(String[] args) {
        new Calculator();
    }
}
