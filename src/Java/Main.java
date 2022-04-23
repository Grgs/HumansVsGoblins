import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        int xDiff;
        int yDiff;
        do {
            xDiff = goblin.getCoordinates().x - human.getCoordinates().x;
            yDiff = goblin.getCoordinates().y - human.getCoordinates().y;
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
        } while (Math.max(Math.abs(xDiff), Math.abs(yDiff)) > 3);
    }

    private static void combat(Goblin goblin, Human human, Random random) {
        System.out.println("combat");
        human.setHealth(human.getHealth() - goblin.getAttack() +
                (int) (2.0 * random.nextGaussian()) - human.getDefence());
        goblin.setHealth(goblin.getHealth() - human.getAttack() +
                (int) (2.0 * random.nextGaussian()) - goblin.getAttack());
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

    private static void initializePlayers(Properties properties, Goblin goblin, Human human) {
        goblin.setCoordinates(0, 0);
        goblin.setHealth(Integer.parseInt((String) properties.get("initialGoblinHealth")));
        goblin.setAttack(Integer.parseInt((String) properties.get("initialGoblinAttack")));
        human.setCoordinates(MaxCoordinates.maxCols / 2, MaxCoordinates.maxRows / 2);
        human.setHealth(Integer.parseInt((String) properties.get("initialHumanHealth")));
        human.setAttack(Integer.parseInt((String) properties.get("initialHumanAttack")));
    }

    private static ArrayList<Piece> getLootList(Random random) {
        int numberOfLoot = Math.max(MaxCoordinates.maxCols, MaxCoordinates.maxRows);
        PrimitiveIterator.OfInt randRows = random.ints(numberOfLoot,
                0, MaxCoordinates.maxRows).iterator();
        PrimitiveIterator.OfInt randCols = random.ints(numberOfLoot,
                0, MaxCoordinates.maxCols).iterator();
        PrimitiveIterator.OfInt randValue = random.ints(numberOfLoot,
                0, 10).iterator();
        ArrayList<Piece> lootList = new ArrayList<>();
        for (int i = 0; i < numberOfLoot; i++) {
            Loot loot = new Loot(new Coordinates(randCols.nextInt(), randRows.nextInt()));
            switch (i % 3) {
                case 0:
                    loot.attack = randValue.nextInt();
                    break;
                case 1:
                    loot.health = randValue.nextInt();
                    break;
                case 2:
                    loot.defence = randValue.nextInt() / 4;
                    break;
            }
            lootList.add(loot);
        }
        return lootList;
    }

    private static void absorbLoot(Land land, Human human, ArrayList<Piece> lootList) {
        List<Piece> capturedLootList = lootList.stream().filter(l -> l.coordinates.
                collidesWith(human.getCoordinates())).collect(Collectors.toList());
        if (capturedLootList.size() == 0)
            return;
        Loot capturedLoot = (Loot) capturedLootList.get(0);
        human.inventory.add(capturedLoot);
        if (capturedLoot.health > 0) {
            human.setHealth(human.getHealth() + capturedLoot.getHealth());
            System.out.printf("+%d Health%nHealth is now %d%n", capturedLoot.getHealth(), human.getHealth());
        }
        if (capturedLoot.attack > 0) {
            human.setAttack(human.getAttack() + capturedLoot.getAttack());
            System.out.printf("+%d Attack%nAttack is now %d%n", capturedLoot.getAttack(), human.getAttack());
        }
        if (capturedLoot.defence > 0) {
            human.setDefence(human.getDefence() + capturedLoot.getDefence());
            System.out.printf("+%d Defence%nDefence is now %d%n", capturedLoot.getDefence(), human.getDefence());
        }
        land.setGrid(human.getCoordinates(), null);
        lootList.removeIf(l -> l.coordinates.collidesWith(human.getCoordinates()));
    }

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("game.properties");
        Properties properties = new Properties();
        properties.load(fileReader);
        fileReader.close();

        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));

        int maxTurns = Integer.parseInt((String) properties.get("maxTurns"));
        Land land = new Land(MaxCoordinates.maxCols, MaxCoordinates.maxRows);
        Goblin goblin = new Goblin();
        Human human = new Human();
        initializePlayers(properties, goblin, human);
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        ArrayList<Piece> lootList = getLootList(random);

        GameState gameState = GameState.PLAYING;

        System.out.printf("Human Vs Goblin%n%sVs%s%n", human, goblin);
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        System.out.println(land);
        System.out.println("type 'w', 'a', 's' or 'd' to move up, left, down or right \nthen press enter:");

        while (gameState == GameState.PLAYING) {
            moveHuman(human, scanner);
            moveGoblin(goblin, human);
            if (land.getGrid(human.getCoordinates()).piece != null) {
                absorbLoot(land, human, lootList);
            }
            if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
                combat(goblin, human, random);
            }
            maxTurns--;
            if (human.getHealth() <= 0) {
                gameState = GameState.LOST;
            } else if (goblin.getHealth() <= 0) {
                gameState = GameState.WON;
            } else if (maxTurns <= 0) {
                gameState = GameState.DRAW;
            }

            if (human.getCoordinates().equals(goblin.getCoordinates())) {
                goblin.moveEast();
            }
            land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
            System.out.printf("%s: Health = %d, Attack = %d and Defence = %d%n", human,
                    human.getHealth(), human.getAttack(), human.getDefence());
            System.out.printf("%s: Health = %d, Attack = %d and Defence = %d%n", goblin,
                    goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
            System.out.println(land);
            printTurnMessage(gameState);
        }
    }
}
