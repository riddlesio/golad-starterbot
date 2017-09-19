package field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * field.Board - Created on 24-2-17
 *
 * Stores all information about the game field and
 * contains methods to perform calculations on it
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class Field {

    private String myId;
    private String opponentId;
    private int width;
    private int height;

    private String[][] cells;

    public Field() {

    }

    /**
     * Parses the input string given by the engine
     * @param input String representation of the field
     */
    public void parseFromString(String input) {
        this.cells = new String[this.width][this.height];
        int x = 0;
        int y = 0;

        for (String cell : input.split(",")) {
            this.cells[x][y] = cell;

            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * Get the points on the field per cell type
     * @return A HashMap with points by cell type
     */
    public HashMap<String, ArrayList<Point>> getCellMapping() {
        HashMap<String, ArrayList<Point>> cellMap = new HashMap<>();

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                String cell = this.cells[x][y];
                cellMap.computeIfAbsent(cell, k -> new ArrayList<>());

                Point point = new Point(x, y);
                cellMap.get(cell).add(point);
            }
        }

        return cellMap;
    }

    public void setMyId(int id) {
        this.myId = id + "";
    }

    public void setOpponentId(int id) {
        this.opponentId = id + "";
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMyId() {
        return this.myId;
    }

    public String getOpponentId() {
        return this.opponentId;
    }
}
