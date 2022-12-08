package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NoSpaceLeftApp {
    static final int TOTAL_SPACE = 70000000;
    static final int NEEDED_SPACE = 30000000;

    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            var fileExplorer = WeirdShellParser.parse(lines);
            //fileExplorer.changeDirectory("/");
            //fileExplorer.printRecursively();
            //part1
            var dirs = fileExplorer.dirsBySize(s -> s <= 100000);
            int sum = dirs.stream().mapToInt(AocFsComponent::size).sum();
            System.out.println(sum);

            //part2
            // NEEDED_SPACE = CURRENT_FREE_SPACE + USED_SPACE_TO_DELETE
            // USED_SPACE_TO_DELETE = NEEDED_SPACE - CURRENT_FREE_SPACE
            int spaceToDelete = NEEDED_SPACE - (TOTAL_SPACE - fileExplorer.getUsedSize());
            var dirsToDel = fileExplorer.dirsBySize(size -> size >= spaceToDelete );
            dirsToDel.stream()
                    .mapToInt(AocFsComponent::size)
                    .min()
                    .ifPresent(System.out::println);
        }
    }
}
