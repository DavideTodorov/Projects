import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameHelper {
    private static final String alphabet = "abcdefg";
    private int gridLength = 7;
    private int gridSize = 49;
    private int[] grid = new int[gridSize];
    private int comCount = 0;

    public String getUserInput(String prompt) {
        String inputLine = null;
        System.out.println(prompt + " ");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            inputLine = reader.readLine();

            if (inputLine.length() == 0) {
                return null;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return inputLine.toLowerCase();
    }

    public ArrayList<String> placeDotCom(int comSize) {
        ArrayList<String> alphaCells = new ArrayList<>();
        String[] alphaCords = new String[comSize];
        String temp = null;
        int[] cords = new int[comSize];
        int attempts = 0;
        boolean success = false;
        int location = 0;

        comCount++;
        int incr = 1;
        if (comCount % 2 == 1) {
            incr = gridLength;
        }

        while (!success && attempts++ < 200) {
            location = (int) (Math.random() * gridSize);
//            System.out.println("try " + location); //print the location
            int x = 0;
            success = true;
            while (success && x < comSize) {
                if (grid[location] == 0) {
                    cords[x++] = location;
                    location += incr;

                    if (location >= gridSize) {
                        success = false;
                    }

                    if (x > 0 && location % gridLength == 0) {
                        success = false;
                    }
                } else {
//                    System.out.println("used " + location);
                    success = false;
                }
            }
        }

        int x = 0;
        int row = 0;
        int column = 0;

        while (x < comSize) {
            grid[cords[x]] = 1;
            row = (int) (cords[x] / gridLength);
            column = cords[x] % gridLength;
            temp = String.valueOf(alphabet.charAt(column));

            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;
//            System.out.println("Cords: " + alphaCells.get(x - 1));
        }

        return alphaCells;
    }
}
