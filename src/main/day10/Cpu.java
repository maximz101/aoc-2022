package day10;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Cpu {
    private int currentCycle;
    private Instruction currentInstruction;
    private int currentInstructionStartCycle;
    private int x = 1;

    private final Queue<Instruction> instructionsQueue;
    private final List<Consumer<Integer>> consumers;

    private Cpu() {
        this.instructionsQueue = new ArrayDeque<>();
        this.consumers = new ArrayList<>();
    }

    private Cpu(List<Consumer<Integer>> consumers) {
        this.instructionsQueue = new ArrayDeque<>();
        this.consumers = new ArrayList<>(consumers);
    }

    public static Cpu create() {
        return new Cpu();
    }

    public static Cpu create(List<Consumer<Integer>> consumers) {
        return new Cpu(consumers);
    }

    public void push(List<Instruction> instructions) {
        instructionsQueue.addAll(instructions);
    }

    public IntStream signal() {
        IntStream.Builder builder = IntStream.builder();

        while (true) {
            if (instructionsQueue.isEmpty()) {
                return builder.build();
            }

            tick();

            if (noInstructionOrDone()) {
                applyIfInstruction();
                currentInstruction = instructionsQueue.poll();
                currentInstructionStartCycle = currentCycle;
            }

            consumers.forEach(integerConsumer -> integerConsumer.accept(x));

            if (isSignalCycle()) {
                builder.add(currentCycle * x);
            }
        }

    }

    private boolean isSignalCycle() {
        return currentCycle == 20 || Set.of(60, 100, 140, 180, 220).contains(currentCycle);
    }

    private void applyIfInstruction() {
        if (currentInstruction != null) {
            x = currentInstruction.execute(x);
        }
    }

    private void tick() {
        currentCycle++;
    }

    private boolean noInstructionOrDone() {
        return currentInstruction == null || currentCycle == currentInstructionStartCycle + currentInstruction.durationInCycles();
    }
}
