package day8;

import java.util.stream.Stream;

public class TreeGridParser {

    private TreeGrid treeGrid;

    public TreeGrid parse(Stream<String> lines) {
        var iterator = lines.iterator();
        int y = 0;
        while (iterator.hasNext()) {
            var line = iterator.next();
            char[] chars = line.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                Tree tree = new Tree(new TreeGrid.Coordinate(x, y), Character.getNumericValue(chars[x]), getTreeGrid(line.length()));
                getTreeGrid(line.length()).addTree(tree);
            }
            y++;
        }
        return treeGrid;
    }

    private TreeGrid getTreeGrid(int s) {
        if (treeGrid == null) {
            treeGrid = new TreeGrid(s);
        }
        return treeGrid;
    }
}
