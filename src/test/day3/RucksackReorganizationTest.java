package day3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RucksackReorganizationTest {

    @Test
    void misplacedSumTest() {
        var input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """;
        int sum = RucksackReorganization.misplacedSum(input.lines());
        Assertions.assertEquals(157, sum);
    }

    @Test
    void sumItemBadgeTest() {
        var input = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """;
        int sum = RucksackReorganization.sumItemBadge(input.lines());
        Assertions.assertEquals(70, sum);
    }
}