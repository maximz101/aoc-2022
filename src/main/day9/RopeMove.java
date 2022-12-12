package day9;

import java.util.List;
import java.util.stream.IntStream;

public record RopeMove(Direction direction, int steps) {
    public List<RopeMove> atomicMoves() {
        if (steps == 1) {
            return List.of(this);
        }
        return IntStream.range(1, steps() + 1)
                .mapToObj(value -> new RopeMove(direction, 1))
                .toList();
    }
}
