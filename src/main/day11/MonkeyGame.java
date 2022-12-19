package day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyGame {
    private final Long commonMultiplier;
    private final List<Monkey> monkeys;
    private final Map<Long, Monkey> monkeysById;

    public MonkeyGame(List<Monkey> monkeys) {
        this.monkeys = monkeys;
        monkeysById = monkeys
                .stream()
                .collect(Collectors.toUnmodifiableMap(Monkey::getId, monkey -> monkey));
        commonMultiplier = monkeys
                .stream().map(Monkey::getDecisionTest).map(DecisionTest::n).reduce((divisor1, divisor2) -> divisor1*divisor2).orElseThrow();
    }

    public List<RoundResult> playRounds(int n) {
        return IntStream.range(0, n).mapToObj(i -> playRound()).collect(Collectors.toCollection(ArrayList::new));
    }

    private RoundResult playRound() {
        RoundResult roundResult = new RoundResult();
        for (var monkey : monkeys) {
            List<Throw> throwz = monkey.play(commonMultiplier);
            roundResult.add(monkey, throwz.size());
            doThrows(throwz);
        }
        return roundResult;
    }

    private void printMonkeys() {
        monkeys.forEach(monkey -> System.out.printf("Monkey %d: %s%n", monkey.getId(), monkey.getItems()));
        System.out.println();
    }

    private void doThrows(List<Throw> throwz) {
        for (var throww : throwz) {
            monkeysById.get(throww.monkeyId()).receive(throww.worry());
        }
    }
}
