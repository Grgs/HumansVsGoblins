import java.util.ArrayList;

public class LandLoot {
    /* This class is created to pass land and lootList from the absorbLoot function back to the main
     * class. If Java allowed returning multiple values of different types we would not need this class.*/
    Land land;
    ArrayList<Piece> lootList;
    String message;

    public LandLoot(Land land, ArrayList<Piece> lootList, String message) {
        this.land = land;
        this.lootList = lootList;
        this.message = message;
    }
}
