package day10;

public final class AddX implements Instruction {
    public static final String ADDX = "addx";
    private final int v;

    public AddX(int v) {
        this.v = v;
    }

    @Override
    public String operation() {
        return ADDX;
    }

    @Override
    public int durationInCycles() {
        return 2;
    }

    @Override
    public int execute(int x) {
        return x + v;
    }
}
