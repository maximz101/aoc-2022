package day9;

import java.util.Comparator;

public record Position(int x, int y) implements Comparable<Position> {
    @Override
    public int compareTo(Position o) {
        return Comparator.comparingInt(Position::x)
                .thenComparingInt(Position::y)
                .compare(this, o);
    }

    public Position getNext(Direction direction, int steps) {
        return switch (direction) {
            case U -> new Position(x(), y() + steps);
            case R -> new Position(x() + steps, y());
            case D -> new Position(x(), y() - steps);
            case L -> new Position(x() - steps, y());
        };
    }

    public boolean isAdjacent(Position position) {
        return Math.abs(position.x() - x()) <= 1 && Math.abs(position.y() - y()) <= 1;
    }
}
