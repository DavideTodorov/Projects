import java.util.ArrayList;
import java.util.Scanner;

public class DotComBust {
    private final Scanner scanner = new Scanner(System.in);

    private GameHelper gameHelper;
    private ArrayList<DotCom> dotComList;
    private int numOfGuesses;

    public DotComBust() {
        gameHelper = new GameHelper();
        dotComList = new ArrayList<>();
        numOfGuesses = 0;
    }

    public void startPlaying() {
        while (!dotComList.isEmpty()) {
            String userGuess = scanner.nextLine();
            checkUserGuess(userGuess);
        }

        finishGame();
    }

    private void setUpGame() {
        dotComList.add(new DotCom("Pets.com"));
        dotComList.add(new DotCom("Toys.com"));
        dotComList.add(new DotCom("Cars.com"));


        System.out.println("Your goal is to sink three DotComs.");
        System.out.println("Pets.com, Toys.com, Cars.com");
        System.out.println("Try to sink them all in the fewest number of guesses");

        for (DotCom dotCom : dotComList) {
            //create dotCom coords from GameHelper
        }
    }

    private void finishGame() {


    }

    private void checkUserGuess(String userGuess) {

    }
}
