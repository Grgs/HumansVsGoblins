import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Properties properties;
    Human human;
    Goblin goblin;
    Random random;

    @BeforeEach
    void setUp() {
        FileReader fileReader;
        try {
            fileReader = new FileReader("game.properties");
            properties = new Properties();
            properties.load(fileReader);
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Failed to open file");
            throw new RuntimeException(e);
        }
        MaxCoordinates.maxCols = 10;
        MaxCoordinates.maxRows = 20;
        human = new Human();
        goblin = new Goblin();
        random = new Random();
    }

    @Test
    void initializePlayers() {
        assertDoesNotThrow(() -> Main.initializePlayers(properties, goblin, human));
        Main.initializePlayers(properties, goblin, human);
        assertNotNull(goblin.getCoordinates());
        assertNotNull(human.getCoordinates());
        assertNotEquals(0, goblin.getHealth());
        assertNotEquals(0, human.getHealth());
    }

    @Test
    void getLootList() {
        ArrayList<Piece> lootList = Main.getLootList(random);
        assertNotNull(lootList);
        assertNotEquals(0, lootList.size());
        assertTrue(lootList.size() < (MaxCoordinates.maxRows * MaxCoordinates.maxCols));
    }

    @Test
    void combat() {
        assertDoesNotThrow(() -> Main.combat(goblin, human, random, 1.5F));
        goblin.setAttack(10);
        human.setAttack(10);
        int initialHumanHealth = human.getHealth();
        Main.combat(goblin, human, random, 0F);
        assertTrue(initialHumanHealth > human.getHealth());
    }

    @Test
    void determineGameState() {
        GameState gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(10);
        assertEquals(GameState.PLAYING, Main.determineGameState(10, goblin, human, gameState));
        human.setHealth(-10);
        assertEquals(GameState.LOST, Main.determineGameState(10, goblin, human, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        assertEquals(GameState.DRAW, Main.determineGameState(0, goblin, human, gameState));
        gameState = GameState.PLAYING;
        human.setHealth(10);
        goblin.setHealth(-10);
        assertEquals(GameState.WON, Main.determineGameState(10, goblin, human, gameState));
    }

}