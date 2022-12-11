package day9;

import java.util.*;
import java.util.stream.IntStream;

/**
 * .....
 * <p>
 * * .....
 * .....
 * (0,0)
 */
public class RopeGrid {
    public static final Position START = new Position(0, 0);
    private final Map<Position, GridCell> grid = new HashMap<>();
    private Set<Position> tailPositions = new HashSet<>();
    private GridCell currentHeadCell;
    private GridCell currentTailCell;


    private RopeGrid(Position startingPosition) {
        var cell = new GridCell(startingPosition);
        cell.add(new Head());
        cell.add(new Tail());
        currentTailCell = cell;
        currentHeadCell = cell;
        grid.put(startingPosition, cell);
        tailPositions.add(startingPosition);
    }

    public static RopeGrid create() {
        return new RopeGrid(START);
    }

    public void add(Position position, RopeComponent ropeComponent) {
        getCell(position).add(ropeComponent);
    }

    public void moveHead(RopeHeadMove ropeHeadMove) {
        var position = currentHeadCell.position();
        var stepsPositions = stepsPositions(ropeHeadMove, position);
        var newPosition = stepsPositions.get(ropeHeadMove.steps());
        GridCell newCell = getCell(newPosition);
        newCell.add(currentHeadCell.removeHead());
        currentHeadCell = newCell;
        updateTail(stepsPositions);
    }

    private void updateTail(List<Position> headPositions) {
        for (int i = 1; i < headPositions.size(); i++) {
            Position tailPosition = currentTailCell.position();
            if (!tailPosition.isAdjacent(headPositions.get(i))) {
                Position prevHeadPosition = headPositions.get(i - 1);
                moveTailTo(prevHeadPosition);
                tailPositions.add(prevHeadPosition);
            }
        }
    }

    private void moveTailTo(Position prevHeadPosition) {
        var currentTailPos = currentTailCell.position();
        RopeComponent tail = getCell(currentTailPos).removeTail();
        GridCell gridCell = getCell(prevHeadPosition);
        gridCell.add(tail);
        currentTailCell = gridCell;
    }

    private GridCell getCell(Position position) {
        if (grid.containsKey(position)) {
            return grid.get(position);
        }
        return grid.computeIfAbsent(position, GridCell::new);
    }

    private static List<Position> stepsPositions(RopeHeadMove ropeHeadMove, Position position) {
        return IntStream.range(0, ropeHeadMove.steps() + 1)
                .mapToObj(step -> position.getNext(ropeHeadMove.direction(), step))
                .toList();
    }

    public int getTailVisitedPosCount() {
        return tailPositions.size();
    }

    public void print() {
        System.out.println();
        var highestX = grid.keySet().stream().mapToInt(Position::x).max().orElse(0);
        var minX = grid.keySet().stream().mapToInt(Position::x).min().orElse(0);
        var highestY = grid.keySet().stream().mapToInt(Position::y).max().orElse(0);
        var minY = grid.keySet().stream().mapToInt(Position::y).min().orElse(0);

        for (int y = highestY; y >= minY; y--) {
            for (int x = minX; x <= highestX; x++) {
                if (new Position(x, y).equals(START)) {
                    System.out.print("s");
                } else {
                    var gridCell = getCell(new Position(x, y));
                    if (gridCell.isEmpty()) {
                        System.out.print(".");
                    } else {
                        System.out.print(gridCell.items().get(0));
                    }
                }
            }
            System.out.println();
        }
    }

    public void print2() {
        System.out.println();
        var highestX = grid.keySet().stream().mapToInt(Position::x).max().orElse(0);
        var minX = grid.keySet().stream().mapToInt(Position::x).min().orElse(0);
        var highestY = grid.keySet().stream().mapToInt(Position::y).max().orElse(0);
        var minY = grid.keySet().stream().mapToInt(Position::y).min().orElse(0);

        for (int y = highestY; y >= minY; y--) {
            for (int x = minX; x <= highestX; x++) {
                Position p = new Position(x, y);
                if (p.equals(START)) {
                    System.out.print("s");
                } else {
                    var gridCell = getCell(p);
                    if (tailPositions.contains(p)) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
    }
}
