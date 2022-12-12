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
        RopeGrid ropeGrid = RopeHeadMovesParser.parseToGrid(INPUT.lines(), 2);
        Assertions.assertEquals(13, ropeGrid.getTailVisitedPosCount());
    }

    @Test
    void ropeBridge_2() {
        RopeGrid ropeGrid = RopeHeadMovesParser.parseToGrid(INPUT.lines(), 10);
        Assertions.assertEquals(1, ropeGrid.getTailVisitedPosCount());
    }
    @Test
    void ropeBridge_3() {
        var in = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20""";
        RopeGrid ropeGrid = RopeHeadMovesParser.parseToGrid(in.lines(), 10);
        Assertions.assertEquals(36, ropeGrid.getTailVisitedPosCount());
    }
}