package day9;

import java.util.stream.Stream;

public final class RopeHeadMovesParser {

    private RopeHeadMovesParser() {
    }

    public static RopeGrid parseToGrid(Stream<String> lines, int size) {
        var ropeGrid = RopeGrid.create(size);
        lines.map(line -> line.split(" "))
                .map(tokens -> new RopeMove(Direction.valueOf(tokens[0]), Integer.parseInt(tokens[1])))
                .forEach(ropeGrid::moveHead);

        return ropeGrid;
    }
}
