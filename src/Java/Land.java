import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Land {
    ArrayList<ArrayList<Tile>> grid;
    int maxColumns, maxRows;

    public Land(int maxColumns, int maxRows) {
        this.maxColumns = maxColumns;
        this.maxRows = maxRows;
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

    public void addPieces(ArrayList<Piece> pieces) {
        for (Piece p : pieces) {
            this.setGrid(p);
        }
    }
//    public void addPlayers(ArrayList<Player> players) {
//        for (Player p : players) {
//            this.setGrid(p);
//        }
//    }

    public Tile getTile(Coordinates coordinates) {
        return grid.get(coordinates.y).get(coordinates.x);
    }

    public void update(ArrayList<Piece> players, ArrayList<Piece> lootList) {
        this.grid = emptyGrid();
        addPieces(lootList);
        addPieces(players);
    }

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
