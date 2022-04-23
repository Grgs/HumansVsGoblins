import java.util.Locale;
import java.util.Scanner;

public class Human extends Player {

    public Human() {
        super();
    }

    public void move(Scanner scanner) {
        char key;
        try {
            key = scanner.next().strip().toLowerCase(Locale.ROOT).charAt(0);
        } catch (Exception e) {
            key = 'n';
        }
        switch (key) {
            case 'w':
                this.moveNorth();
                break;
            case 'a':
                this.moveWest();
                break;
            case 's':
                this.moveSouth();
                break;
            case 'd':
                this.moveEast();
                break;
        }
    }

    @Override
    public String toString() {
        return "\uD83D\uDC64"; //👤
    }
}
