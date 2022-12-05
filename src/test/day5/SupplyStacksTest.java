package day5;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplyStacksTest {

    @Test
    void getTopCreatesTest() {
        var input = """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3\s
                                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2""";

        var ssm = SupplyStacks.SupplyStackManager.parseInput(input.lines());
        assertEquals("[C][M][Z]", ssm.getTopCreatesOrdered().stream().map(SupplyStacks.Crate::name).collect(Collectors.joining()));
    }
}