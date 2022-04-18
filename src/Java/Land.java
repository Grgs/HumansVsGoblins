import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Land {
    ArrayList<ArrayList<String>> grid;
    Human human;
    Goblin goblin;
    public Land(int rows, int columns){
        human = new Human();
        goblin = new Goblin();
        grid = new ArrayList<>();
        String[] emptyRow = new String[rows];
        Arrays.fill(emptyRow, "  ");
        for (int i = 0; i <= columns; i ++){
            grid.add(new ArrayList<>(List.of(emptyRow)));
        }
//        grid.get(5).set(5, "X");
        this.setGrid(2, 3, human.toString());
        this.setGrid(8, 13, goblin.toString());
    }

    public void setGrid(int row, int column, String value){
        grid.get(row).set(column, value);
    }

    @Override
    public String toString() {
        return grid.stream().map(AbstractCollection::toString).
                collect(Collectors.toList()).stream().
                reduce("", (a, b) -> a + "\n" + b).toString();
    }
}
