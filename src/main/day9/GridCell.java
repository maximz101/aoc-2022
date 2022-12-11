package day9;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public final class GridCell implements Comparable<GridCell> {
    private final Position position;
    private final List<RopeComponent> items;

    public GridCell(Position position) {
        this.position = position;
        items = new ArrayList<>();
    }

    public Position position() {
        return position;
    }

    public List<RopeComponent> items() {
        return List.copyOf(items);
    }


    public void add(RopeComponent ropeComponent) {
        items.add(ropeComponent);
    }

    public RopeComponent removeHead() {
        return removeAndGet(Head.class::isInstance);
    }

    public RopeComponent removeTail() {
        return removeAndGet(Tail.class::isInstance);
    }

    private RopeComponent removeAndGet(Predicate<RopeComponent> isInstance) {
        Optional<RopeComponent> first = items.stream().filter(isInstance).findFirst();
        if (first.isPresent()) {
            RopeComponent ropeComponent = first.get();
            items.remove(ropeComponent);
            return ropeComponent;
        }
        return null;
    }

    public boolean isEmpty() {
        return items.isEmpty();
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
