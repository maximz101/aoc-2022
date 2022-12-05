package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupplyStacks {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {

//            var supplyStackManger = SupplyStackManager.parseInput(lines, new CrateMover9000());
            var supplyStackManger = SupplyStackManager.parseInput(lines, new CrateMover9001());
            System.out.println(supplyStackManger.getTopCreatesOrdered().stream().map(Crate::toString).collect(Collectors.joining()));
        }
    }


    record Crate(String name) {
        @Override
        public String toString() {
            return name.substring(1, 2);
        }
    }

    record MovingAction(String fromStack, String toStack, int count) {
    }

    static class SupplyStackManager {
        private final Map<String, Deque<Crate>> stacks;

        private SupplyStackManager(Map<String, Deque<Crate>> stacks) {
            this.stacks = stacks;
        }

        private SupplyStackManager() {
            stacks = new HashMap<>();
        }

        public void move(CrateMover crateMover, List<MovingAction> actions) {
            crateMover.move(actions, stacks);
        }

        public static SupplyStackManager parseInput(Stream<String> input, CrateMover crateMover) {
            var iterator = input.iterator();
            if (!iterator.hasNext()) {
                return new SupplyStackManager();
            }

            var stacks = parseStacks(iterator);
            var actions = parseActions(iterator);

            SupplyStackManager supplyStackManager = new SupplyStackManager(stacks);
            supplyStackManager.move(crateMover, actions);
            return supplyStackManager;
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

        private static List<MovingAction> parseActions(Iterator<String> iterator) {
            // move count from id  to id
            var pattern = Pattern.compile("^move\\s+(?<count>\\d+)\\s+from\\s+(?<fromId>\\d)+\\sto\\s+(?<toId>\\d)+$");
            var list = new ArrayList<MovingAction>();
            while (iterator.hasNext()) {
                var actionLine = iterator.next();
                var matcher = pattern.matcher(actionLine);
                if (matcher.matches()) {
                    list.add(new MovingAction(matcher.group("fromId"), matcher.group("toId"), Integer.parseInt(matcher.group("count"))));
                }
            }
            return list;
        }

        public List<Crate> getTopCreatesOrdered() {
            var iterator = stacks.entrySet().iterator();
            var r = new TreeMap<Integer, Crate>();
            while (iterator.hasNext()) {
                Map.Entry<String, Deque<Crate>> next = iterator.next();
                r.put(Integer.parseInt(next.getKey()), next.getValue().peekFirst());
            }
            return r.values().stream().toList();
        }
    }

    interface CrateMover {
        void move(List<MovingAction> movingAction, Map<String, Deque<Crate>> stacks);
    }

    static class CrateMover9000 implements CrateMover {

        @Override
        public void move(List<MovingAction> movingActions, Map<String, Deque<Crate>> stacks) {
            for (var action : movingActions) {
                var origStack = stacks.get(action.fromStack());
                var dstStack = stacks.get(action.toStack());
                for (int i = 0; i < action.count(); i++) {
                    dstStack.push(origStack.pop());
                }
            }
        }
    }

    static class CrateMover9001 implements CrateMover {

        @Override
        public void move(List<MovingAction> movingActions, Map<String, Deque<Crate>> stacks) {
            for (var action : movingActions) {
                var origStack = stacks.get(action.fromStack());
                var dstStack = stacks.get(action.toStack());
                var tmpStack = new ArrayDeque<Crate>();
                for (int i = 0; i < action.count(); i++) {
                    tmpStack.push(origStack.pop());
                }
                for (int i = 0; i < action.count(); i++) {
                    dstStack.push(tmpStack.pop());
                }
            }
        }
    }
}
