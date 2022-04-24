import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class GoblinTest {

    @Test
    void move() {
        MaxCoordinates.maxCols = 100;
        MaxCoordinates.maxRows = 100;
        Goblin goblin = new Goblin();
        Human human = new Human();
        goblin.setCoordinates(3, 3);
        human.setCoordinates(90, 90);
        double initialDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        goblin.move(human, 99);
        double currentDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        assertTrue(initialDistance > currentDistance);
        goblin.setCoordinates(10, 10);
        human.setCoordinates(20, 20);
        initialDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        goblin.move(human, 1);
        currentDistance = Math.sqrt(Math.pow(goblin.getCoordinates().x - human.getCoordinates().x, 2) +
                Math.pow(goblin.getCoordinates().y - human.getCoordinates().y, 2));
        assertTrue(initialDistance > currentDistance);
    }
}