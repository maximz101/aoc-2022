package day9;

import java.util.Comparator;

public record Position(int x, int y) implements Comparable<Position> {
    @Override
    public int compareTo(Position o) {
        return Comparator.comparingInt(Position::x)
                .thenComparingInt(Position::y)
                .compare(this, o);
    }

    public Position getNextForMove(RopeMove ropeMove) {
        return switch (ropeMove.direction()) {
            case U -> new Position(x(), y() + ropeMove.steps());
            case R -> new Position(x() + ropeMove.steps(), y());
            case D -> new Position(x(), y() - ropeMove.steps());
            case L -> new Position(x() - ropeMove.steps(), y());
        };
    }

    public boolean isAdjacent(Position position) {
        return Math.abs(position.x() - x()) <= 1 && Math.abs(position.y() - y()) <= 1;
    }

    public Position nextPositionToBecomeAdj(Position to) {
        int toX = to.x() < x() ? x() - 1 : x() + 1;
        if (y() == to.y()) {
            return new Position(toX, to.y());
        }

        int toY = to.y() < y() ? y() - 1 : y() + 1;
        if (x() == to.x()) {
            return new Position(to.x(), toY);
        }
        return new Position(toX, toY);
    }
}
