import java.util.ArrayList;

public class Battleship {

    public ArrayList<ArrayList<Cell>> gameGrid = new ArrayList<ArrayList<Cell>>();

    public ArrayList<Ship> ships = new ArrayList<>();

    public Battleship(){
        generateGrid();
    }

    public void generateGrid(){
        for (int i = 0; i < 10; i++) {
            gameGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                gameGrid.get(i).add(new Cell());
            }
        }
    }

}
