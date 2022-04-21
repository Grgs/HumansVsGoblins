import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxCols = 3;
        int maxRows = 3;
        Land land = new Land(maxCols, maxRows);
        Goblin goblin = new Goblin(maxCols, maxRows);
        goblin.setNewCoordinates(0, 0);
        goblin.setHealth(10);
        goblin.setAttack(7);
        Human human = new Human(maxCols, maxRows);
        human.setNewCoordinates(maxCols / 2, maxRows / 2);
        human.setHealth(10);
        human.setAttack(7);
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        GameState gameState = GameState.PLAYING;
        int turns = 10;

        land.update(human, goblin);
        System.out.println(land);

        while (gameState == GameState.PLAYING) {
            if (moveHuman(human, scanner)) continue;
            goblin.moveEast();
            land.update(human, goblin);
            if (human.newCoordinates.collidesWith(goblin.newCoordinates)) {
                combat(goblin, human, random);
            }
            if (human.newCoordinates.equals(goblin.newCoordinates)){
                goblin.moveEast();
            }
            turns--;
            if (human.getHealth() <= 0) {
                gameState = GameState.LOST;
            } else if (goblin.getHealth() <= 0) {
                gameState = GameState.WON;
            } else if (turns <= 0) {
                gameState = GameState.DRAW;
            }
            System.out.println(land);
            printTurnMessage(gameState);
        }
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

    private static void combat(Goblin goblin, Human human, Random random) {
        System.out.println("combat");
        human.setHealth(human.getHealth() - goblin.getAttack() + (int) (2.0 * random.nextGaussian()));
        goblin.setHealth(goblin.getHealth() - human.getAttack() + (int) (2.0 * random.nextGaussian()));
        System.out.printf("Human health: %d%nGoblin health: %d%n",
                human.getHealth(), goblin.getHealth());
    }

    private static boolean moveHuman(Human human, Scanner scanner) {
        char key;
        try {
            key = scanner.next().strip().charAt(0);
        } catch (Exception e) {
            return true;
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
            default:
                return true;
        }
        return false;
    }
}
