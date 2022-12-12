package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RopeBridge {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            RopeGrid ropeGrid = RopeHeadMovesParser.parseToGrid(lines, 10);
            System.out.println(ropeGrid.getTailVisitedPosCount());
        }
    }
}
