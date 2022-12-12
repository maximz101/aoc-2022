package day10;

public sealed interface Instruction permits AddX, NoOp {
    String operation();

    int durationInCycles();

    int execute(int x);
}
