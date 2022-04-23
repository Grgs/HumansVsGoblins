import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void combat(Goblin goblin, Human human, Random random) {
        System.out.println("combat");
        int oldHumanHealth = human.getHealth();
        int oldGoblinHealth = goblin.getHealth();
        human.setHealth(oldHumanHealth + Math.min(goblin.getAttack() * -1 +
                (int) (-2.0 * random.nextGaussian()) + human.getDefence(), 0));
        goblin.setHealth(oldGoblinHealth + Math.min(human.getAttack() * -1 +
                (int) (-2.0 * random.nextGaussian()) + goblin.getDefence(), 0));
        System.out.printf("%s health has been reduced by %d%n%s health has been reduced by %d%n", human,
                oldHumanHealth - human.getHealth(), goblin, oldGoblinHealth - goblin.getHealth());
    }

    public static void printEndGameMessage(GameState gameState) {
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

    public static void initializePlayers(Properties properties, Goblin goblin, Human human) {
        goblin.setCoordinates(0, 0);
        goblin.setHealth(Integer.parseInt((String) properties.get("initialGoblinHealth")));
        goblin.setAttack(Integer.parseInt((String) properties.get("initialGoblinAttack")));
        human.setCoordinates(MaxCoordinates.maxCols / 2, MaxCoordinates.maxRows / 2);
        human.setHealth(Integer.parseInt((String) properties.get("initialHumanHealth")));
        human.setAttack(Integer.parseInt((String) properties.get("initialHumanAttack")));
    }

    public static ArrayList<Piece> getLootList(Random random) {
        int numberOfLoot = Math.max(MaxCoordinates.maxCols, MaxCoordinates.maxRows);
        PrimitiveIterator.OfInt randRows = random.ints(numberOfLoot,
                0, MaxCoordinates.maxRows).iterator();
        PrimitiveIterator.OfInt randCols = random.ints(numberOfLoot,
                0, MaxCoordinates.maxCols).iterator();
        PrimitiveIterator.OfInt randValue = random.ints(numberOfLoot,
                1, 10).iterator();
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
                    loot.defence = randValue.nextInt() / 2;
                    break;
            }
            lootList.add(loot);
        }
        return lootList;
    }

    public static void absorbLoot(Land land, Human human, ArrayList<Piece> lootList) {
        List<Piece> capturedLootList = lootList.stream().filter(l -> l.coordinates.
                equals(human.getCoordinates())).collect(Collectors.toList());
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
        lootList.removeIf(l -> l.coordinates.equals(human.getCoordinates()));
    }

    private static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) {
            gameState = GameState.LOST;
        } else if (goblin.getHealth() <= 0) {
            gameState = GameState.WON;
        } else if (turnsLeft <= 0) {
            gameState = GameState.DRAW;
        }
        return gameState;
    }

    private static void removeLosingPlayerFromLand(Land land, Goblin goblin, Human human, GameState gameState) {
        if (gameState.equals(GameState.WON)) {
            land.setGrid(goblin.getCoordinates(), null);
        } else if (gameState.equals(GameState.LOST)) {
            land.setGrid(human.getCoordinates(), null);
        }
    }

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("game.properties");
        Properties properties = new Properties();
        properties.load(fileReader);
        fileReader.close();

        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));

        int turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));
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
            human.move(scanner);
            goblin.move(human, turnsLeft);
            if (land.getGrid(human.getCoordinates()).piece != null) {
                absorbLoot(land, human, lootList);
            }
            if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
                combat(goblin, human, random);
            }

            if (human.getCoordinates().equals(goblin.getCoordinates())) {
                goblin.moveEast();
            }

            turnsLeft--;
            gameState = determineGameState(turnsLeft, goblin, human, gameState);

            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", human,
                    human.getHealth(), human.getAttack(), human.getDefence());
            System.out.printf("%s: Health = %d\t Attack = %d\t Defence = %d%n", goblin,
                    goblin.getHealth(), goblin.getAttack(), goblin.getDefence());
            System.out.printf("%d turns left%n", turnsLeft);

            land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);

            removeLosingPlayerFromLand(land, goblin, human, gameState);
            System.out.println(land);

            printEndGameMessage(gameState);
        }
    }
}
