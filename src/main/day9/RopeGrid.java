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
    private final Set<Position> tailPositionsTrackerSet = new HashSet<>();
    private final Rope ropeHead;

    private RopeGrid(Position startingPosition, int size) {
        ropeHead = Rope.create(size, startingPosition);
        for (var ropeComponent : ropeHead) {
            add(startingPosition, ropeComponent);
        }
        tailPositionsTrackerSet.add(startingPosition);
    }

    public static RopeGrid create(int size) {
        return new RopeGrid(START, size);
    }

    public void add(Position position, Rope rope) {
        getCell(position).add(rope);
    }

    public void moveHead(RopeMove ropeMove) {
        var moves = ropeMove.atomicMoves();
        for (var nextMove : moves) {
            move(ropeHead, ropeHead.getPosition().getNextForMove(nextMove));
        }
    }

    private void move(Rope rope, Position toPosition) {
        handleCellMoving(rope, toPosition);

        handleNext(rope, toPosition);
    }

    private void handleNext(Rope rope, Position toPosition) {
        var ropeIt = rope.iterator();
        ropeIt.next();
        if (ropeIt.hasNext()) {
            var next = ropeIt.next();
            if (!next.isAdjacent(rope)) {
                move(next, next.getPosition().nextPositionToBecomeAdj(toPosition));
            }
        }
    }

    private void handleCellMoving(Rope rope, Position toPosition) {
        var fromCell = getCell(rope.getPosition());
        var toCell = getCell(toPosition);
        toCell.add(fromCell.remove(rope));
        rope.setPosition(toPosition);
        if (rope.isTail()) {
            tailPositionsTrackerSet.add(toPosition);
        }
    }

    private GridCell getCell(Position position) {
        if (grid.containsKey(position)) {
            return grid.get(position);
        }
        return grid.computeIfAbsent(position, GridCell::new);
    }

    private static List<Position> stepsPositions2(RopeMove ropeMove, Position starting) {
        return IntStream.range(0, ropeMove.steps() + 1)
                .mapToObj(step -> starting.getNextForMove(ropeMove))
                .toList();
    }

    public int getTailVisitedPosCount() {
        return tailPositionsTrackerSet.size();
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
                    if (tailPositionsTrackerSet.contains(p)) {
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
