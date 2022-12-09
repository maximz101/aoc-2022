package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TreeTopTreeHouse {

    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            TreeGrid treeGrid = new TreeGridParser().parse(lines);
            //part1
            System.out.println(treeGrid.visibleFromOutsideCount());
            //part2
            System.out.println(treeGrid.getMaxScenicScore());
        }
    }
}
