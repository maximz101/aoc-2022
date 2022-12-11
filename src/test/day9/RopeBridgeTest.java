package day9;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RopeBridgeTest {
    private final static String INPUT = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2""";

    @Test
    void ropeBridge_1() {
        RopeGrid ropeGrid = RopeHeadMovesParser.parseToGrid(INPUT.lines());
        Assertions.assertEquals(13, ropeGrid.getTailVisitedPosCount());
    }
}