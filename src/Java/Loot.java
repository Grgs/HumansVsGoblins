public class Loot extends Piece {
    public int attack;
    public int health;
    public int defence;

    public Loot(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String toString() {
        return " +";
    }
}
