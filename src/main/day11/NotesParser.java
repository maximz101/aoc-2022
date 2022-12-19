package day11;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public final class NotesParser {
    public static MonkeyGame parseToMonkeyGame(String input) {
        Pattern patter = Pattern.compile("Monkey (?<id>\\d)+:\\s+Starting items: (?<items>(?:\\d+,\\s)*\\d+)\\s+Operation: (?<formula>.+)\\s+Test:(?<test>.*)\\s+If true:(?<ifTrue>.+)\\s+If false:(?<ifFalse>.+)");
        List<Monkey> monkeys = patter.matcher(input)
                .results()
                .map(
                        matchResult -> {
                            var monkeyId = Integer.parseInt(matchResult.group(1));
                            var items = parseWorryList(matchResult.group(2));
                            var operation = parseOperation(matchResult.group(3));
                            var test = parseTest(matchResult.group(4));
                            var ifTrue = parseActionAction(matchResult.group(5));
                            var ifFalse = parseActionAction(matchResult.group(6));
                            return new Monkey(monkeyId, items, operation, new DecisionTest(test.n(), test.test(), ifTrue, ifFalse));
                        }
                ).toList();
        return new MonkeyGame(monkeys);
    }

    private static List<Long> parseWorryList(String worryList) {
        return Arrays.stream(worryList.split(",")).map(String::trim).map(Long::parseLong).toList();
    }

    private static UnaryOperator<Long> parseOperation(String formula) {
        var pattern = Pattern.compile("new = old( (?<operator>[+\\-*]) (?<operand2>old|\\d+))?");
        var matcher = pattern.matcher(formula);
        if (matcher.matches()) {
            String operator = matcher.group("operator");
            if (operator.isEmpty()) {
                return x -> x;
            }
            String operand2 = matcher.group("operand2");
            boolean self = "old".equalsIgnoreCase(operand2);
            return switch (operator) {
                case "*" -> self ? x -> x * x : x -> Long.parseLong(operand2) * x;
                case "+" -> self ? x -> x + x : x -> Long.parseLong(operand2) + x;
                case "-" -> self ? x -> 0L : x -> x / Long.parseLong(operand2);
                case "/" -> self ? x -> 1L : x -> x / Long.parseLong(operand2);
                default -> throw new IllegalArgumentException();
            };
        }
        return null;
    }

    private record Test(long n, Predicate<Long> test) {
    }

    private static Test parseTest(String test) {
        var pattern = Pattern.compile(" divisible by (?<n>\\d+)");
        var matcher = pattern.matcher(test);
        if (matcher.matches()) {
            var n = Long.parseLong(matcher.group("n"));
            return new Test(n, x -> x % n == 0L);
        }
        throw new IllegalArgumentException();
    }

    private static LongSupplier parseActionAction(String action) {
        var pattern = Pattern.compile(" throw to monkey (?<monkeyId>\\d+)");
        var matcher = pattern.matcher(action);
        if (matcher.matches()) {
            return () -> Long.parseLong(matcher.group("monkeyId"));
        }
        throw new IllegalArgumentException();
    }

}
