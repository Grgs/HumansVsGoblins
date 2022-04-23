import java.util.ArrayList;

public class Player extends Piece {
    ArrayList<Loot> inventory;


    public Player(Coordinates coordinates) {
        super(coordinates);
    }

    public Player() {
        super(new Coordinates(0, 0));
    }


    public ArrayList<Loot> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Loot> inventory) {
        this.inventory = inventory;
    }

}
