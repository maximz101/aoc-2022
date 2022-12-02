package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class RockPaperScissors {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            int sum = lines.map(RoundParser::parse).mapToInt(RoundParser.Round::score).sum();
            System.out.println("my score is : " + sum);
        }
    }

    private static class RoundParser {

        private RoundParser() {
        }

        public static Round parse(String s) {
            String[] items = s.split(" ");
            var player1 = Item.from(items[0]);
            return new Round(player1, ElfStrategy.nextMove(player1, Result.from(items[1])));
        }

        private sealed interface Item permits Rock, Paper, Scissors {
            static Item from(String item) {
                return switch (item) {
                    case "A" -> new Rock();
                    case "B" -> new Paper();
                    case "C" -> new Scissors();
                    default -> throw new RuntimeException();
                };
            }

            int value();

            Set<Character> symbols();

            Set<Item> predators();

            Set<Item> preys();

            default Result fight(Item other) {
                if (other.equals(this)) {
                    return Result.DRAW;
                } else if (preys().contains(other)) {
                    return Result.WIN;
                } else if (predators().contains(other)) {
                    return Result.LOOSE;
                }
                throw new RuntimeException();
            }
        }

        enum Result {
            WIN, LOOSE, DRAW;

            public static Result from(String item) {
                return switch (item.charAt(0)) {
                    case 'Z' -> WIN;
                    case 'X' -> LOOSE;
                    case 'Y' -> DRAW;
                    default -> throw new RuntimeException();
                };
            }
        }

        private record Rock() implements Item {

            @Override
            public int value() {
                return 1;
            }

            @Override
            public Set<Character> symbols() {
                return Set.of('A');
            }

            @Override
            public Set<Item> predators() {
                return Set.of(new Paper());
            }

            @Override
            public Set<Item> preys() {
                return Set.of(new Scissors());
            }


        }

        private record Paper() implements Item {

            @Override
            public int value() {
                return 2;
            }

            @Override
            public Set<Character> symbols() {
                return Set.of('B');
            }

            @Override
            public Set<Item> predators() {
                return Set.of(new Scissors());
            }

            @Override
            public Set<Item> preys() {
                return Set.of(new Rock());
            }

        }

        private record Scissors() implements Item {

            @Override
            public int value() {
                return 3;
            }

            @Override
            public Set<Character> symbols() {
                return Set.of('C');
            }

            @Override
            public Set<Item> predators() {
                return Set.of(new Rock());
            }

            @Override
            public Set<Item> preys() {
                return Set.of(new Paper());
            }

        }

        private record Round(Item player1, Item player2) {

            public int score() {
                var fight = player2.fight(player1);
                var match = switch (fight) {
                    case WIN -> 6;
                    case LOOSE -> 0;
                    case DRAW -> 3;
                };
                return player2.value() + match;
            }
        }

        private static class ElfStrategy {

            public static Item nextMove(Item player1, Result from) {
                return switch (from) {
                    case WIN -> player1.predators().iterator().next();
                    case LOOSE -> player1.preys().iterator().next();
                    case DRAW -> player1;
                };
            }
        }
    }
}
