import java.util.Random;
import java.util.Scanner;

public class SimpleDotComGame {
    private Random random;
    private int[] numsToGuess;

    public SimpleDotComGame() {
        random = new Random();
        numsToGuess = new int[3];
    }

    public void run(Scanner scanner) {
        int countOfGuesses = 0;
        fillArray(numsToGuess);

        while (countOfGuesses < 3) {
            System.out.println("Enter number:");
            int currGuess = Integer.parseInt(scanner.nextLine());
            boolean isGuessed = false;

            for (int i = 0; i < 3; i++) {
                int toGuess = numsToGuess[i];
                if (currGuess == toGuess) {
                    numsToGuess[i] = -1;
                    countOfGuesses++;

                    if (countOfGuesses == 3) {
                        System.out.println("You win!");
                        return;
                    } else {
                        System.out.println("hit");
                    }

                    isGuessed = true;
                    break;
                }
            }

            if (!isGuessed) {
                System.out.println("Try again!");
            }

        }
    }

    private void fillArray(int[] numsToGuess) {
        int randomNum = random.nextInt(6);

        while (randomNum == 0) {
            randomNum = random.nextInt(6);
        }

        numsToGuess[0] = randomNum - 1;
        numsToGuess[1] = randomNum;
        numsToGuess[2] = randomNum + 1;
    }


}
