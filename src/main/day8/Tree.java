package day8;

import day8.TreeGrid.Coordinate;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static day8.Direction.*;

record Tree(Coordinate coordinate, int height, TreeGrid treeGrid) {

    Set<Tree> getNeighbours() {
        return Stream.of(left(), up(), right(), down())
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Optional<Tree> left() {
        return treeGrid.getNext(coordinate(), LEFT);
    }

    public Optional<Tree> right() {
        return treeGrid.getNext(coordinate(), RIGHT);
    }

    public Optional<Tree> up() {
        return treeGrid.getNext(coordinate(), UP);
    }

    public Optional<Tree> down() {
        return treeGrid.getNext(coordinate(), DOWN);
    }

    public boolean isEdge() {
        return getNeighbours().size() < 4;
    }

    public boolean isVisibleFromOutside() {
        if (isEdge()) {
            return true;
        }
        return Arrays.stream(values())
                .map(this::isInvisibleFor)
                .anyMatch(visible -> !visible);
    }

    private boolean isInvisibleFor(Direction direction) {
        return Stream.of(direction)
                .flatMap(this::getTreesStream)
                .anyMatch(tree -> tree.height() >= height());
    }

    private Stream<Tree> getTreesStream(Direction direction) {
        return switch (direction) {
            case LEFT -> Stream.iterate(left(), Optional::isPresent, tree -> tree.get().left()).map(Optional::get);
            case UP -> Stream.iterate(up(), Optional::isPresent, tree -> tree.get().up()).map(Optional::get);
            case RIGHT -> Stream.iterate(right(), Optional::isPresent, tree -> tree.get().right()).map(Optional::get);
            case DOWN -> Stream.iterate(down(), Optional::isPresent, tree -> tree.get().down()).map(Optional::get);
        };
    }

    public long scenicScore() {
        return Arrays.stream(Direction.values())
                .mapToLong(this::viewingDistance)
                .reduce((left, right) -> left * right)
                .orElse(0);
    }

    private long viewingDistance(Direction direction) {
        var iterator = getTreesStream(direction).iterator();
        int count = 0;
        while (iterator.hasNext()) {
            int nextHeight = iterator.next().height;
            if (nextHeight >= height()) {
                count++;
                return count;
            }
            count++;
        }
        return count;
    }
}
