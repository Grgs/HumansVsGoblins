public class Goblin extends Player {

    public Goblin() {
        super();
    }

    public void move(Human human, int maxTurn) {
        int xDiff;
        int yDiff;
        do {
            xDiff = this.getCoordinates().x - human.getCoordinates().x;
            yDiff = this.getCoordinates().y - human.getCoordinates().y;
            if (Math.abs(xDiff) == Math.abs(yDiff)) {
                if (xDiff < 0) {
                    this.moveEast();
                } else {
                    this.moveWest();
                }
                if (yDiff < 0) {
                    this.moveSouth();
                } else {
                    this.moveNorth();
                }
            } else if (Math.abs(xDiff) > Math.abs(yDiff)) {
                if (xDiff < 0) {
                    this.moveEast();
                } else {
                    this.moveWest();
                }
            } else {
                if (yDiff < 0) {
                    this.moveSouth();
                } else {
                    this.moveNorth();
                }
            }
        } while (Math.max(Math.abs(xDiff), Math.abs(yDiff)) > Math.max(maxTurn, 2));
    }

    @Override
    public String toString() {
        return "\uD83D\uDC7A"; //👺
    }
}
