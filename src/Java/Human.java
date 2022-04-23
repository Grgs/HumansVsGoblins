import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Human extends Player {

    public Human() {
        super();
    }

    public void move(Scanner scanner) {
        char key;
        try {
            key = scanner.next().strip().toLowerCase(Locale.ROOT).charAt(0);
        } catch (Exception e) {
            key = 'n';
        }
        switch (key) {
            case 'w':
                this.moveNorth();
                break;
            case 'a':
                this.moveWest();
                break;
            case 's':
                this.moveSouth();
                break;
            case 'd':
                this.moveEast();
                break;
        }
    }

    public LandLoot absorbLoot(Land land, ArrayList<Piece> lootList) {
        String message = "";
        List<Piece> capturedLootList = lootList.stream().filter(l -> l.coordinates.
                equals(this.getCoordinates())).collect(Collectors.toList());
        if (capturedLootList.size() == 0)
            return new LandLoot(land, lootList, message);
        Loot capturedLoot = (Loot) capturedLootList.get(0);
        this.inventory.add(capturedLoot);
        if (capturedLoot.health > 0) {
            this.setHealth(this.getHealth() + capturedLoot.getHealth());
            message = String.format("+%d Health%nHealth is now %d%n", capturedLoot.getHealth(), this.getHealth());
        }
        if (capturedLoot.attack > 0) {
            this.setAttack(this.getAttack() + capturedLoot.getAttack());
            message = String.format("+%d Attack%nAttack is now %d%n", capturedLoot.getAttack(), this.getAttack());
        }
        if (capturedLoot.defence > 0) {
            this.setDefence(this.getDefence() + capturedLoot.getDefence());
            message = String.format("+%d Defence%nDefence is now %d%n", capturedLoot.getDefence(), this.getDefence());
        }
        land.setGrid(this.getCoordinates(), null);
        lootList.removeIf(l -> l.coordinates.equals(this.getCoordinates()));
        return new LandLoot(land, lootList, message);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC64"; //👤
    }
}
