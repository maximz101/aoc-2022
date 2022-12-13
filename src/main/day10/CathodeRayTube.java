package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CathodeRayTube {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            // part1(lines);
            part2(lines);
        }
    }

    private static void part1(Stream<String> lines) {
        List<Instruction> instructions = InstructionParser.parse(lines);
        Cpu cpu = Cpu.create();
        cpu.push(instructions);
        IntStream signal = cpu.signal();
        System.out.println(signal.sum());
    }

    private static void part2(Stream<String> lines) {
        List<Instruction> instructions = InstructionParser.parse(lines);
        Cpu cpu = Cpu.create(List.of(new Crt()));
        cpu.push(instructions);
        IntStream signal = cpu.signal();
        //  System.out.println(signal.sum());
    }
}
