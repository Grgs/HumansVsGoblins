import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Land {
    ArrayList<ArrayList<String>> grid;
    int maxColumns, maxRows;

    public Land(int maxColumns, int maxRows) {
        this.maxColumns = maxColumns;
        this.maxRows = maxRows;
        this.grid = emptyGrid();
    }

    public ArrayList<ArrayList<String>> emptyGrid(int maxColumns, int maxRows) {
        ArrayList<ArrayList<String>> newGrid = new ArrayList<>();
        String[] emptyColumns = new String[maxColumns];
        Arrays.fill(emptyColumns, "  ");
        for (int i = 0; i <= maxRows - 1; i++) {
            newGrid.add(new ArrayList<>(List.of(emptyColumns)));
        }
        return newGrid;
    }

    public ArrayList<ArrayList<String>> emptyGrid(){
        return emptyGrid(this.maxColumns, this.maxRows);
    }

    public void setGrid(Coordinates coordinates, String string) {
        grid.get(coordinates.y).set(coordinates.x, string);
    }

    public void setGrid(Player player) {
        setGrid(player.newCoordinates, player.toString());
    }
    public void addPlayers(ArrayList<Player> players){
        for (Player p: players){
            this.setGrid(p);
        }
    }

    public String getGrid(Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    public void update(Human human, Goblin goblin) {
        this.grid = emptyGrid();
        addPlayers(new ArrayList<>(List.of(new Player[]{human, goblin})));
    }

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
