import java.util.ArrayList;

public class Player {
    Coordinates oldCoordinates;

    Coordinates newCoordinates;

    ArrayList<Equipment> inventory;

    int health;
    int attack;


    public Coordinates getNewCoordinates() {
        return newCoordinates;
    }

    public Player(int maxX, int maxY, int x, int y) {
        this.newCoordinates = new Coordinates(maxY, maxX, x, y);
        this.oldCoordinates = new Coordinates(maxY, maxX, x, y);
    }

    public Player(int maxX, int maxY) {
        this.newCoordinates = new Coordinates(maxY, maxX, 0, 0);
        this.oldCoordinates = new Coordinates(maxY, maxX, maxX - 1, maxY - 1);
    }

    public void setNewCoordinates(int x, int y) {
        this.newCoordinates.x = x;
        this.newCoordinates.y = y;
    }

    public void teleport(Coordinates coordinates) {
        this.newCoordinates = coordinates;
    }


    public void moveNorth() {
        oldCoordinates.setXY(newCoordinates.x, newCoordinates.y);
        newCoordinates.setXY(newCoordinates.x, newCoordinates.y - 1);
    }

    public void moveSouth() {
        oldCoordinates.setXY(newCoordinates.x, newCoordinates.y);
        newCoordinates.setXY(newCoordinates.x, newCoordinates.y + 1);
    }

    public void moveEast() {
        oldCoordinates.setXY(newCoordinates.x, newCoordinates.y);
        newCoordinates.setXY(newCoordinates.x + 1, newCoordinates.y);
    }

    public void moveWest() {
        oldCoordinates.setXY(newCoordinates.x, newCoordinates.y);
        newCoordinates.setXY(newCoordinates.x - 1, newCoordinates.y);
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Equipment> inventory) {
        this.inventory = inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
