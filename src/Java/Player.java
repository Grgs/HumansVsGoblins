public class Player {
    Coordinates oldCoordinates;
    Coordinates newCoordinates;

    public Player(int maxX, int maxY) {
        this.newCoordinates = new Coordinates(maxY, maxX);
        this.oldCoordinates = new Coordinates(maxY, maxX);
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
}
