import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static void moveHuman(Human human, Scanner scanner) {
        char key;
        try {
            key = scanner.next().strip().toLowerCase(Locale.ROOT).charAt(0);
        } catch (Exception e) {
            key = 'n';
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
    }

    private static void moveGoblin(Goblin goblin, Human human) {
        int xDiff = goblin.getCoordinates().x - human.getCoordinates().x;
        int yDiff = goblin.getCoordinates().y - human.getCoordinates().y;
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff < 0) {
                goblin.moveEast();
            } else {
                goblin.moveWest();
            }
        } else {
            if (yDiff < 0) {
                goblin.moveSouth();
            } else {
                goblin.moveNorth();
            }
        }
    }

    private static void combat(Goblin goblin, Human human, Random random) {
        System.out.println("combat");
        human.setHealth(human.getHealth() - goblin.getAttack() + (int) (2.0 * random.nextGaussian()));
        goblin.setHealth(goblin.getHealth() - human.getAttack() + (int) (2.0 * random.nextGaussian()));
        System.out.printf("Human health: %d%nGoblin health: %d%n",
                human.getHealth(), goblin.getHealth());
    }

    private static void printTurnMessage(GameState gameState) {
        switch (gameState) {
            case WON:
                System.out.println("You won!");
                break;
            case LOST:
                System.out.println("You lost!");
                break;
            case DRAW:
                System.out.println("You survived!");
                break;
        }
    }

    public static void main(String[] args) {
        int maxCols = 5;
        int maxRows = 5;
        Land land = new Land(maxCols, maxRows);
        Goblin goblin = new Goblin(maxCols, maxRows);
        goblin.setNewCoordinates(0, 0);
        goblin.setHealth(15);
        goblin.setAttack(5);
        Human human = new Human(maxCols, maxRows);
        human.setNewCoordinates(maxCols / 2, maxRows / 2);
        human.setHealth(15);
        human.setAttack(5);
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        GameState gameState = GameState.PLAYING;
        int turns = 10;

        System.out.printf("Human Vs Goblin%n%sVs%s%n", human, goblin);
        land.update(human, goblin);
        System.out.println(land);
        System.out.println("type 'w', 'a', 's' or 'd' to move up, left, down or right \nthen press enter:");

        while (gameState == GameState.PLAYING) {
            moveHuman(human, scanner);
            moveGoblin(goblin, human);
            if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
                combat(goblin, human, random);
            }
            turns--;
            if (human.getHealth() <= 0) {
                gameState = GameState.LOST;
            } else if (goblin.getHealth() <= 0) {
                gameState = GameState.WON;
            } else if (turns <= 0) {
                gameState = GameState.DRAW;
            }

            if (human.getCoordinates().equals(goblin.getCoordinates())) {
                goblin.moveEast();
            }
            land.update(human, goblin);
            System.out.println(land);
            printTurnMessage(gameState);
        }
    }
}
