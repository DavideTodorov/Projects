import java.util.ArrayList;

public class DotCom {
    private String name;
    private ArrayList<String> location;

    public DotCom(String name) {
        this.name = name;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public String checkLocation(String input) {
        String result = "miss";

        int index = location.indexOf(input);

        if (index >= 0) {
            location.remove(index);

            if (location.isEmpty()) {
                result = "kill";
            } else {
                result = "hit";
            }
        }

        return result;
    }
}
