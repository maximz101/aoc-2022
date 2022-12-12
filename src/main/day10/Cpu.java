package day10;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

public class Cpu {
    private int currentCycle;
    private Instruction currentInstruction;
    private int currentInstructionStartCycle;
    private int x = 1;

    private final Queue<Instruction> instructionsQueue;

    private Cpu() {
        instructionsQueue = new ArrayDeque<>();
    }

    public static Cpu create() {
        return new Cpu();
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

            if (currentInstructionDone()) {
                applyInstruction();
                currentInstruction = instructionsQueue.poll();
                currentInstructionStartCycle = currentCycle + 1;
            }

            if (isSignalCycle()) {
                builder.add(currentCycle * x);
            }
        }

    }

    private boolean isSignalCycle() {
        return currentCycle == 20 || Set.of(60, 100, 140, 180, 220).contains(currentCycle);
    }

    private void applyInstruction() {
        if (currentInstruction != null) {
            x = currentInstruction.execute(x);
        }
    }

    private void tick() {
        currentCycle++;
    }

    private boolean currentInstructionDone() {
        return currentInstruction == null || currentCycle == currentInstructionStartCycle + currentInstruction.durationInCycles() - 1;
    }
}
