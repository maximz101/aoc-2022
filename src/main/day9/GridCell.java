package day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class GridCell implements Comparable<GridCell> {
    private final Position position;
    private final List<Rope> items;

    public GridCell(Position position) {
        this.position = position;
        items = new ArrayList<>();
    }

    public Position position() {
        return position;
    }

    public List<Rope> items() {
        return List.copyOf(items);
    }


    public void add(Rope rope) {
        items.add(rope);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Rope remove(Rope rope) {
        items.remove(rope);
        return rope;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GridCell) obj;
        return Objects.equals(this.position, that.position) &&
               Objects.equals(this.items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, items);
    }

    @Override
    public int compareTo(GridCell o) {
        return position.compareTo(o.position());
    }

    @Override
    public String toString() {
        return "GridCell[" +
               "position=" + position + ", " +
               "item=" + items + ']';
    }
}
