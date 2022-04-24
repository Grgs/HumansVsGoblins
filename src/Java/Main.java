import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

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

    public static void combat(Goblin goblin, Human human, Random random, float randomness) {
        System.out.println("combat");
        int oldHumanHealth = human.getHealth();
        int oldGoblinHealth = goblin.getHealth();
        human.setHealth(oldHumanHealth + Math.min(-goblin.getAttack() -
                (int) (randomness * random.nextGaussian()) + human.getDefence(), 0));
        goblin.setHealth(oldGoblinHealth + Math.min(-human.getAttack() -
                (int) (randomness * random.nextGaussian()) + goblin.getDefence(), 0));
        System.out.printf("%s health has been reduced by %d%n%s health has been reduced by %d%n", human,
                oldHumanHealth - human.getHealth(), goblin, oldGoblinHealth - goblin.getHealth());
    }

    public static GameState determineGameState(int turnsLeft, Goblin goblin, Human human, GameState gameState) {
        if (human.getHealth() <= 0) {
            gameState = GameState.LOST;
        } else if (goblin.getHealth() <= 0) {
            gameState = GameState.WON;
        } else if (turnsLeft <= 0) {
            gameState = GameState.DRAW;
        }
        return gameState;
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

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("game.properties");
        Properties properties = new Properties();
        properties.load(fileReader);
        fileReader.close();

        MaxCoordinates.maxCols = Integer.parseInt((String) properties.get("maxCols"));
        MaxCoordinates.maxRows = Integer.parseInt((String) properties.get("maxRows"));

        int turnsLeft = Integer.parseInt((String) properties.get("maxTurns"));
        Land land = new Land();
        Goblin goblin = new Goblin();
        Human human = new Human();
        System.out.printf("Human\tVs\tGoblin%n%s\t\tVs\t%s%n", human, goblin);

        initializePlayers(properties, goblin, human);
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        ArrayList<Piece> lootList = getLootList(random);
        GameState gameState = GameState.PLAYING;
        land.update(new ArrayList<>(List.of(new Player[]{human, goblin})), lootList);
        System.out.println(land);
        System.out.println("type 'q' to quit or\n" +
                "type 'w', 'a', 's' or 'd' to move up, left, down or right \nthen press enter:");

        while (gameState == GameState.PLAYING) {
            human.move(scanner);
            goblin.move(human, turnsLeft);
            if (land.getGrid(human.getCoordinates()).piece != null) {
                lootList = human.absorbLoot(lootList);
                land.setGrid(human.getCoordinates(), null);
            }
            if (human.getCoordinates().collidesWith(goblin.getCoordinates())) {
                combat(goblin, human, random, Float.parseFloat((String) properties.get("combatRandomness")));
                Loot lootDrop = new Loot(new Coordinates(goblin.getCoordinates()));
                while (lootDrop.getCoordinates().equals(human.getCoordinates()) ||
                        lootDrop.getCoordinates().equals(goblin.getCoordinates())) {
                    lootDrop.moveEast();
                }
                lootDrop.setDefence(5);
                lootList.add(lootDrop);
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

            land.removeLosingPlayerFromLand(goblin, human, gameState);
            System.out.println(land);

            printEndGameMessage(gameState);
        }
    }
}
