package day8;

import java.util.Arrays;
import java.util.Optional;

public class TreeGrid {
    private final int size;
    private final Tree[][] grid;

    public TreeGrid(int size) {
        this.size = size;
        grid = new Tree[size][size];
    }

    Optional<Tree> get(Coordinate coordinate) {
        return Optional.ofNullable(grid[coordinate.x()][coordinate.y()]);
    }

    public long visibleFromOutsideCount() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(Tree::isVisibleFromOutside)
                .count();
    }

    public void addTree(Tree tree) {
        int x = tree.coordinate().x();
        int y = tree.coordinate().y();
        grid[x][y] = tree;
    }

    public Optional<Tree> getNext(Coordinate coordinate, Direction dir) {
        return switch (dir) {
            case LEFT ->
                    coordinate.x() - 1 < 0 ? Optional.empty() : Optional.ofNullable(grid[coordinate.x() - 1][coordinate.y()]);
            case UP ->
                    coordinate.y() - 1 < 0 ? Optional.empty() : Optional.ofNullable(grid[coordinate.x()][coordinate.y() - 1]);
            case RIGHT ->
                    coordinate.x() + 1 >= size ? Optional.empty() : Optional.ofNullable(grid[coordinate.x() + 1][coordinate.y()]);
            case DOWN ->
                    coordinate.y() + 1 >= size ? Optional.empty() : Optional.ofNullable(grid[coordinate.x()][coordinate.y() + 1]);
        };
    }

    public long getMaxScenicScore() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .mapToLong(Tree::scenicScore)
                .max()
                .orElse(-1);
    }

    void print() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tree tree = grid[x][y];
                boolean visibleFromOutside = tree.isVisibleFromOutside();
                if (!visibleFromOutside) {
                    System.out.print("\033[0;32m");
                }
                System.out.printf("%d", tree.height());
                System.out.print("\033[0m");
            }
            System.out.println();
        }
    }

    record Coordinate(int x, int y) {

        Coordinate {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException();
            }
        }

    }
}
