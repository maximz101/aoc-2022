package day11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

record DecisionTest(long n, Predicate<Long> decider, LongSupplier nextMonkeyIfTrue, LongSupplier nextMonkeyIfFalse) {
}

public class Monkey {
    private final int monkeyId;
    private final List<Long> worryList;
    private final UnaryOperator<Long> operation;

    private final DecisionTest decisionTest;

    public Monkey(int monkeyId, List<Long> worryList, UnaryOperator<Long> operation, DecisionTest decisionTest) {
        this.monkeyId = monkeyId;
        this.worryList = new ArrayList<>(worryList);
        this.operation = operation;
        this.decisionTest = decisionTest;
    }

    public long getId() {
        return monkeyId;
    }

    public DecisionTest getDecisionTest() {
        return decisionTest;
    }

    public void receive(long worry) {
        worryList.add(worry);
    }

    public List<Throw> play(long lcm) {
        var throwz = new ArrayList<Throw>();
        for (var worryItem : worryList) {
            var worryAfterOp = operation.apply(worryItem);
            var worryDiv3 = worryAfterOp % lcm;// / 3;
            if (decisionTest.decider().test(worryDiv3)) {
                throwz.add(new Throw(decisionTest.nextMonkeyIfTrue().getAsLong(), worryDiv3));
            } else {
                throwz.add(new Throw(decisionTest.nextMonkeyIfFalse().getAsLong(), worryDiv3));
            }
        }
        worryList.clear();
        return throwz;
    }

    public String getItems() {
        return worryList.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monkey monkey = (Monkey) o;

        return monkeyId == monkey.monkeyId;
    }

    @Override
    public int hashCode() {
        return monkeyId;
    }
}
