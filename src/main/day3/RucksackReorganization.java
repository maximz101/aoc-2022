package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class RucksackReorganization {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
//            System.out.println(misplacedSum(lines));
            System.out.println(sumItemBadge(lines));
        }
    }

    public static int misplacedSum(Stream<String> lines) {
        return lines
                .flatMapToInt(line -> {
                    var middleIdx = line.length() / 2;
                    var substring1 = line.substring(0, middleIdx);
                    var substring2 = line.substring(middleIdx);

                    HashSet<Integer> collect = substring1.chars()
                            .distinct()
                            .collect(
                                    HashSet::new,
                                    HashSet::add,
                                    AbstractCollection::addAll
                            );

                    return substring2.chars()
                            .distinct()
                            .filter(value -> !collect.add(value));
                })
                .map(RucksackReorganization::itemToValue)
                .sum();
    }

    public static int sumItemBadge(Stream<String> lines) {
        var iterator = lines.iterator();
        var sets = new ArrayList<Set<Integer>>(3);
        var badges = new ArrayList<Integer>();
        while (iterator.hasNext()) {
            for (var i = 0; i < 3 && iterator.hasNext(); i++) {
                sets.add(iterator.next().chars()
                        .collect(HashSet::new,
                                HashSet::add,
                                AbstractCollection::addAll));
            }

            var integers = new HashSet<>(sets.get(0));
            integers.retainAll(sets.get(1));
            integers.retainAll(sets.get(2));
            badges.add(integers.stream().mapToInt(value -> value)
                    .map(RucksackReorganization::itemToValue)
                    .sum());
            sets.clear();
        }
        return badges.stream().mapToInt(value -> value).sum();
    }

    private static int itemToValue(int operand) {
        if (operand >= 'a' && operand <= 'z') {
            return operand - 'a' + 1;
        } else {
            return operand - 'A' + 27;
        }
    }
}
