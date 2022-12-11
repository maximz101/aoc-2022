package day9;

import java.util.stream.Stream;

public final class RopeHeadMovesParser {

    private RopeHeadMovesParser() {
    }

    public static RopeGrid parseToGrid(Stream<String> lines) {
        var ropeGrid = RopeGrid.create();
        lines.map(line -> line.split(" "))
                .map(tokens -> new RopeHeadMove(Direction.valueOf(tokens[0]), Integer.parseInt(tokens[1])))
                .forEach(ropeGrid::moveHead);

        return ropeGrid;
    }
}
