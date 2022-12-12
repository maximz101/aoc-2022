package day10;

import java.util.List;
import java.util.stream.Stream;

import static day10.AddX.ADDX;
import static day10.NoOp.NOOP;

public final class InstructionParser {

    private InstructionParser() {
    }

    public static List<Instruction> parse(Stream<String> lines) {
        return lines.map(instruction -> instruction.split(" "))
                .map(InstructionParser::toInstruction)
                .toList();
    }

    private static Instruction toInstruction(String[] strings) {
        return switch (strings[0]) {
            case NOOP -> new NoOp();
            case ADDX -> new AddX(Integer.parseInt(strings[1]));
            default -> throw new IllegalArgumentException();
        };
    }
}
