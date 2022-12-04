package day4;

import org.junit.jupiter.api.Test;

import static day4.CampCleanup.fullyContainedRangesCount;
import static day4.CampCleanup.overlappingRangesCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CampCleanupTest {

    @Test
    void fullyContainedRangesCountTest() {
        var input = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8""";
        assertEquals(2, fullyContainedRangesCount(input.lines()));
    }

    @Test
    void overlappingRangesCountTest() {
        var input = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8""";
        assertEquals(4, overlappingRangesCount(input.lines()));
    }
}