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
        setUpGame();
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

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;

        String result = "miss";

        for (DotCom dotCom : dotComList) {
            result = dotCom.checkLocation(userGuess);

            if (result.equals("hit")) {
                break;
            } else if (result.equals("kill")) {
                dotComList.remove(dotCom);
                break;
            }
        }

        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("All Dot Coms are now dead!");
        if (numOfGuesses <= 18) {
            System.out.printf("It only took you %d guesses.%n", numOfGuesses);
        } else {
            System.out.printf("Took you long enough. %d guesses.%n", numOfGuesses);
        }
    }
}
