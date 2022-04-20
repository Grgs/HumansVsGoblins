import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxCols = 4;
        int maxRows = 3;
        Land land = new Land(maxCols, maxRows);
        Goblin goblin = new Goblin(maxCols, maxRows, 0, 0);
        Human human = new Human(maxCols, maxRows, maxCols/2, maxRows/2);
        Scanner scanner = new Scanner(System.in);

        land.update(human, goblin);
        System.out.println(land);

        for (int i = 0; i <= 5; i++) {
            char key;
            try {
                key = scanner.next().strip().charAt(0);
                System.out.println(key);
            } catch (Exception e) {
                continue;
            }
            switch (key) {
                case 'w':
                    human.moveNorth();
                    break;
                case 'a':
                    human.moveWest();
                    break;
                case 's':
                    human.moveSouth();
                    break;
                case 'd':
                    human.moveEast();
                    break;
            }
            goblin.moveEast();
            land.update(human, goblin);
            System.out.println(land);
        }
    }
}
