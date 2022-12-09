package day8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreeTopTreeHouseTest {
    private final static String INPUT = """
            30373
            25512
            65332
            33549
            35390""";

    @Test
    void testTreeTop_1() {
        TreeGrid treeGrid = new TreeGridParser().parse(INPUT.lines());
        Assertions.assertNotNull(treeGrid);
        var count = treeGrid.visibleFromOutsideCount();
        Assertions.assertEquals(21, count);
    }

    @Test
    void testTreeTop_1_2() {
        var input = """
                9999
                9119
                9199
                9999
                """;
        TreeGrid treeGrid = new TreeGridParser().parse(input.lines());
        Assertions.assertNotNull(treeGrid);
        var count = treeGrid.visibleFromOutsideCount();
        Assertions.assertEquals(12, count);
    }

    @Test
    void testTreeTop_2() {
        TreeGrid treeGrid = new TreeGridParser().parse(INPUT.lines());
        Assertions.assertNotNull(treeGrid);
        var score = treeGrid.getMaxScenicScore();
        Assertions.assertEquals(8, score);
    }

}