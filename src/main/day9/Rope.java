package day9;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Rope implements Iterable<Rope> {
    private Position position;

    public static final String H = "H";
    public static final String T = "T";
    private final String name;
    private Rope next;

    private Rope(int size, Position startPosition) {
        name = H;
        position = startPosition;
        var tail = new Rope(T, startPosition);
        next = tail;

        var current = this;
        for (int i = 1; i < size - 1; i++) {
            var newRope = new Rope(i + "", startPosition);
            current.next = newRope;
            newRope.next = tail;
            current = current.next;
        }
    }

    private Rope(String name, Position startPosition) {
        this.name = name;
        this.position = startPosition;
    }

    public static Rope create(int size, Position startPosition) {
        return new Rope(size, startPosition);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isHead() {
        return H.equals(name);
    }

    public boolean isTail() {
        return T.equals(name);
    }

    public boolean isAdjacent(Rope other) {
        return position.isAdjacent(other.getPosition());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Iterator<Rope> iterator() {
        return new RopeComponentIterator(this);
    }

    private static final class RopeComponentIterator implements Iterator<Rope> {
        private Rope rope;

        private RopeComponentIterator(Rope rope) {
            this.rope = rope;
        }

        @Override
        public boolean hasNext() {
            return rope != null;
        }

        @Override
        public Rope next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var n = rope;
            rope = rope.next;
            return n;
        }

        public Rope ropeComponent() {
            return rope;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (RopeComponentIterator) obj;
            return Objects.equals(this.rope, that.rope);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rope);
        }

        @Override
        public String toString() {
            return "RopeComponentIterator[" +
                   "ropeComponent=" + rope + ']';
        }

    }
}