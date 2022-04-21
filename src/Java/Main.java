import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxCols = 4;
        int maxRows = 3;
        Land land = new Land(maxCols, maxRows);
        Goblin goblin = new Goblin(maxCols, maxRows, 0, 0);
        goblin.setHealth(10);
        goblin.setAttack(5);
        Human human = new Human(maxCols, maxRows, maxCols / 2, maxRows / 2);
        human.setHealth(10);
        human.setAttack(5);
        Scanner scanner = new Scanner(System.in);

        land.update(human, goblin);
        System.out.println(land);

        for (int i = 0; i <= 5; i++) {
            char key;
            try {
                key = scanner.next().strip().charAt(0);
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
                default:
                    continue;
            }
            goblin.moveEast();
            land.update(human, goblin);
            if (Math.abs(human.newCoordinates.x - goblin.newCoordinates.x) +
                    Math.abs(human.newCoordinates.y - goblin.newCoordinates.y) < 2) {
                System.out.println("combat");
                human.setHealth(human.getHealth() - goblin.getAttack());
                goblin.setHealth(goblin.getHealth() - human.getAttack());
                System.out.printf("Human health: %d%nGoblin health: %d%n",
                        human.getHealth(), goblin.getHealth());
            }
            System.out.println(land);
        }
    }
}
