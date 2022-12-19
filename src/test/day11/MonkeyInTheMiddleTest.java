package day11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MonkeyInTheMiddleTest {
    private final static String INPUT = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
                    
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
                    
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
                    
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1""";

    @Test
    void day11() {
        MonkeyGame monkeyGame = NotesParser.parseToMonkeyGame(INPUT);
        Assertions.assertNotNull(monkeyGame);
        List<RoundResult> roundResults = monkeyGame.playRounds(20);
        var monkeyBusinessLevel = roundResults.stream()
                .reduce(RoundResult::merge).orElseThrow().monkeyBusinessLevel();

        Assertions.assertEquals(10605, monkeyBusinessLevel);
    }

    @Test
    void day11_2() {
        MonkeyGame monkeyGame = NotesParser.parseToMonkeyGame(INPUT);
        Assertions.assertNotNull(monkeyGame);
        List<RoundResult> roundResults = monkeyGame.playRounds(10_000);
        var monkeyBusinessLevel = roundResults.stream()
                .reduce(RoundResult::merge).orElseThrow().monkeyBusinessLevel();

        Assertions.assertEquals(2713310158L, monkeyBusinessLevel);
    }
}