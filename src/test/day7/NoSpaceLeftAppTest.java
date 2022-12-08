package day7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static day7.NoSpaceLeftApp.NEEDED_SPACE;
import static day7.NoSpaceLeftApp.TOTAL_SPACE;

class NoSpaceLeftAppTest {

    public static final String INPUT = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k""";

    @Test
    void day7_1() {

        var fileExplorer = WeirdShellParser.parse(INPUT.lines());
        var dirs = fileExplorer.dirsBySize(s -> s <= 100000);
        int sum = dirs.stream().mapToInt(AocFsComponent::size).sum();
        Assertions.assertEquals(95437, sum);
    }
    @Test
   void day7_2() {
        var fileExplorer = WeirdShellParser.parse(INPUT.lines());
        int spaceToDelete = NEEDED_SPACE - (TOTAL_SPACE - fileExplorer.getUsedSize());
        var dirsToDel = fileExplorer.dirsBySize(size -> size >= spaceToDelete );
       var min = dirsToDel.stream()
                .mapToInt(AocFsComponent::size)
                .min()
               .orElseThrow();
        Assertions.assertEquals(24933642, min);
    }
}