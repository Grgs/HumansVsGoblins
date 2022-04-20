public class Goblin extends Player {

    public Goblin(int maxX, int maxY, int x, int y) {
        super(maxX, maxY, x, y);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC7A"; //👺
    }
}
