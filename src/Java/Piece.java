public class Piece {
    Coordinates coordinates;

    public Piece(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
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

    @Override
    public String toString() {
        return " P";
    }
}
