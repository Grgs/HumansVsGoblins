public class Coordinates {
    int maxY;
    int maxX;
    int y;
    int x;

    public Coordinates(int maxY, int maxX) {
        this.maxY = maxY;
        this.maxX = maxX;
    }

    public void setXY(int x, int y) {
        this.x = x % maxX;
        this.y = y % maxY;
    }

}
