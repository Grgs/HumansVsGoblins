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

    public boolean collidesWith(Coordinates coordinates){
        return (Math.abs(this.x - coordinates.x) + Math.abs(this.y - coordinates.y) < 2);
    }
    public boolean equals(Coordinates coordinates) {
        return this.x == coordinates.x && this.y == coordinates.y;
    }
}
