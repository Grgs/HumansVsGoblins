public class Coordinates {
    int maxY;
    int maxX;
    int y;
    int x;

    public Coordinates(int maxY, int maxX, int x, int y) {
        this.maxY = maxY;
        this.maxX = maxX;
        this.x = x;
        this.y = y;
    }

    public void setXY(int x, int y) {

        this.x = x % this.maxX;
        this.y = y % this.maxY;
        if (this.x < 0) {
            this.x += this.maxX;
        }
        if (this.y < 0) {
            this.y += this.maxY;
        }
    }

}
