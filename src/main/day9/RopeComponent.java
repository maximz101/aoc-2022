package day9;

public sealed interface RopeComponent permits Head, Tail {
}

record Head() implements RopeComponent {
    @Override
    public String toString() {
        return "H";
    }
}

record Tail() implements RopeComponent {
    @Override
    public String toString() {
        return "T";
    }
}