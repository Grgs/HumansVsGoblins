public class Main {
    public static void main(String[] args) {
        int maxCols = 16;
        int maxRows = 9;
        Land land = new Land(maxCols, maxRows);
        Human human = new Human(maxCols, maxRows);
        Goblin goblin = new Goblin(maxCols, maxRows);
        for (int i = 0; i <= 20; i++) {
            human.moveSouth();
            goblin.moveEast();
            land.update(human, goblin);
            System.out.println(land);
        }
    }
}
