import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Land {
    ArrayList<ArrayList<String>> grid;

    public Land(int columns, int rows) {
        grid = new ArrayList<>();
        String[] emptyColumns = new String[columns];
        Arrays.fill(emptyColumns, "  ");
        for (int i = 0; i <= rows - 1; i++) {
            grid.add(new ArrayList<>(List.of(emptyColumns)));
        }
    }

    public void setGrid(int row, int column, String value) {
        grid.get(row).set(column, value);
    }

    public void setGrid(Player player) {
        setGrid(player.oldCoordinates.y, player.oldCoordinates.x, "  ");
        setGrid(player.newCoordinates.y, player.newCoordinates.x, player.toString());
    }

    public void update(Human human, Goblin goblin) {
        setGrid(human);
        setGrid(goblin);
    }

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b);
    }
}
