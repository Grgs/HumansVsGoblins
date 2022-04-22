import java.util.ArrayList;

public class Player {
    Coordinates coordinates;

    ArrayList<Loot> inventory;

    int health;
    int attack;


    public Player(int maxX, int maxY, int x, int y) {
        this.coordinates = new Coordinates(maxY, maxX, x, y);
    }

    public Player(int maxX, int maxY) {
        this.coordinates = new Coordinates(maxY, maxX, 0, 0);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setNewCoordinates(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }

    public void teleport(Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    public void moveNorth() {
        coordinates.setXY(coordinates.x, coordinates.y - 1);
    }

    public void moveSouth() {
        coordinates.setXY(coordinates.x, coordinates.y + 1);
    }

    public void moveEast() {
        coordinates.setXY(coordinates.x + 1, coordinates.y);
    }

    public void moveWest() {
        coordinates.setXY(coordinates.x - 1, coordinates.y);
    }

    public ArrayList<Loot> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Loot> inventory) {
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
