import java.util.ArrayList;

public class LandLoot {
    Land land;
    ArrayList<Piece> lootList;
    String message;

    public LandLoot(Land land, ArrayList<Piece> lootList, String message) {
        this.land = land;
        this.lootList = lootList;
        this.message = message;
    }
}
