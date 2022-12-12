package day10;

public final class NoOp implements Instruction {

    public static final String NOOP = "noop";

    @Override
    public String operation() {
        return NOOP;
    }

    @Override
    public int durationInCycles() {
        return 1;
    }

    @Override
    public int execute(int x) {
        return x;
    }
}
