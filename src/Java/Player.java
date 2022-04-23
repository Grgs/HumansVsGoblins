import java.util.ArrayList;

public class Player extends Piece {
    ArrayList<Loot> inventory;


    public Player(Coordinates coordinates) {
        super(coordinates);
        inventory = new ArrayList<>();
    }

    public Player() {
        super(new Coordinates(0, 0));
        inventory = new ArrayList<>();
    }


    public ArrayList<Loot> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Loot> inventory) {
        this.inventory = inventory;
    }

}
