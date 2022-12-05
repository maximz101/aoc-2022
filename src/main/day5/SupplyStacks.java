package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SupplyStacks {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {

            var supplyStackManger = SupplyStackManager.parseInput(lines);
            System.out.println(supplyStackManger.getTopCreatesOrdered());
        }
    }


    record Crate(String name) {
        @Override
        public String toString() {
            return name.substring(1, 2);
        }
    }

    private record StackAction(String fromStack, String toStack, int count) {
    }

    static class SupplyStackManager {
        private final Map<String, Deque<Crate>> stacks;

        private SupplyStackManager(Map<String, Deque<Crate>> stacks, List<StackAction> actions) {
            this.stacks = stacks;
            replay(actions);
        }

        private SupplyStackManager() {
            stacks = new HashMap<>();
        }

        private void replay(List<StackAction> actions) {
            for (var action : actions) {
                var origStack = stacks.get(action.fromStack());
                var dstStack = stacks.get(action.toStack());
                for (int i = 0; i < action.count(); i++) {
                    dstStack.push(origStack.pop());
                }
            }
        }

        public static SupplyStackManager parseInput(Stream<String> input) {
            var iterator = input.iterator();
            if (!iterator.hasNext()) {
                return new SupplyStackManager();
            }

            var stacks = parseStacks(iterator);
            var actions = parseActions(iterator);

            return new SupplyStackManager(stacks, actions);
        }

        private static Map<String, Deque<Crate>> parseStacks(Iterator<String> iterator) {
            var m = new HashMap<String, Deque<Crate>>();
            while (iterator.hasNext()) {
                var line = iterator.next();
                if (line.startsWith(" 1")) {
                    iterator.next();
                    return m;
                }

                int crateLength = 3;
                int sepLength = 1;
                int stackId = 1;

                for (int i = 0; i < line.length(); i += crateLength + sepLength) {
                    var crateStr = line.substring(i, i + crateLength);
                    if (!"   ".equals(crateStr)) {
                        String key = String.valueOf(stackId);
                        Deque<Crate> orDefault = m.getOrDefault(key, new ArrayDeque<>());
                        orDefault.offer(new Crate(crateStr));
                        m.put(key, orDefault);
                    }
                    stackId++;
                }
            }
            return m;
        }

        private static List<StackAction> parseActions(Iterator<String> iterator) {
            // move count from id  to id
            var pattern = Pattern.compile("^move\\s+(?<count>\\d+)\\s+from\\s+(?<fromId>\\d)+\\sto\\s+(?<toId>\\d)+$");
            var list = new ArrayList<StackAction>();
            while (iterator.hasNext()) {
                var actionLine = iterator.next();
                var matcher = pattern.matcher(actionLine);
                if (matcher.matches()) {
                    list.add(new StackAction(matcher.group("fromId"), matcher.group("toId"), Integer.parseInt(matcher.group("count"))));
                }
            }
            return list;
        }

        public List<Crate> getTopCreatesOrdered() {
            Iterator<Map.Entry<String, Deque<Crate>>> iterator = stacks.entrySet().iterator();
            var r = new TreeMap<Integer, Crate>();
            while (iterator.hasNext()) {
                Map.Entry<String, Deque<Crate>> next = iterator.next();
                r.put(Integer.parseInt(next.getKey()), next.getValue().peekFirst());
            }
            return r.values().stream().toList();
        }
    }
}
