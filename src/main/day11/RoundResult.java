package day11;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RoundResult {
    private final Map<Monkey, Long> monkeyToInspectionCount;

    public RoundResult() {
        this.monkeyToInspectionCount = new HashMap<>();
    }

    private RoundResult(HashMap<Monkey, Long> map) {
        this.monkeyToInspectionCount = map;
    }

    public long monkeyBusinessLevel() {
        return monkeyToInspectionCount.values()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .mapToLong(value -> value)
                .reduce((left, right) -> left * right)
                .orElseThrow();
    }

    public void add(Monkey monkey, long count) {
        monkeyToInspectionCount.put(monkey, count);
    }

    public void print() {
        for (var m : monkeyToInspectionCount.entrySet()) {
            System.out.printf("Monkey %d inspected items %d times.%n", m.getKey().getId(), m.getValue());
        }
    }

    public RoundResult merge(RoundResult otherRound) {
        var map = new HashMap<>(monkeyToInspectionCount);
        for (var entry : otherRound.monkeyToInspectionCount.entrySet()) {
            map.merge(entry.getKey(), entry.getValue(), Long::sum);
        }
        return new RoundResult(map);
    }
}
