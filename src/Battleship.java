import java.util.ArrayList;
import java.util.Random;

public class Battleship {

    public ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();

    public ArrayList<Ship> ships = new ArrayList<>();

    public Battleship(){
        generateGrid();
    }

    public void generateGrid(){
        for (int i = 0; i < 10; i++) {
            cells.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                cells.get(i).add(new Cell());
            }
        }
    }
}
