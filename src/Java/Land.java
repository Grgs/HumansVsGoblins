import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Land {
    ArrayList<ArrayList<Tile>> grid;
    int maxColumns, maxRows;

    public Land() {
        this.maxColumns = MaxCoordinates.maxCols;
        this.maxRows = MaxCoordinates.maxRows;
        this.grid = emptyGrid();
    }

    public ArrayList<ArrayList<Tile>> emptyGrid(int maxColumns, int maxRows) {
        ArrayList<ArrayList<Tile>> newGrid = new ArrayList<>();
        Tile[] emptyColumns = new Tile[maxColumns];
        Arrays.fill(emptyColumns, new Tile());
        for (int i = 0; i <= maxRows - 1; i++) {
            newGrid.add(new ArrayList<>(List.of(emptyColumns)));
        }
        return newGrid;
    }

    public ArrayList<ArrayList<Tile>> emptyGrid() {
        return emptyGrid(this.maxColumns, this.maxRows);
    }

    public void setGrid(Coordinates coordinates, Piece piece) {
        grid.get(coordinates.y).set(coordinates.x, new Tile(piece));
    }

    public void setGrid(Piece piece) {
        setGrid(piece.getCoordinates(), piece);
    }

    public Tile getGrid(Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    public void addPieces(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            this.setGrid(p);
        }
    }

    public void update(ArrayList<Piece> players, ArrayList<Piece> lootList) {
        this.grid = emptyGrid();
        addPieces(lootList);
        addPieces(players);
    }

    public void removeLosingPlayerFromLand(Goblin goblin, Human human, GameState gameState) {
        if (gameState.equals(GameState.WON)) {
            this.setGrid(goblin.getCoordinates(), null);
        } else if (gameState.equals(GameState.LOST)) {
            this.setGrid(human.getCoordinates(), null);
        }
    }

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
