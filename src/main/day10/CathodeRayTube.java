package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class CathodeRayTube {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            List<Instruction> instructions = InstructionParser.parse(lines);
            Cpu cpu = Cpu.create();
            cpu.push(instructions);
            IntStream signal = cpu.signal();
            System.out.println(signal.sum());
        }
    }
}
