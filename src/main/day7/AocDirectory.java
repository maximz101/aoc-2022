package day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;

public class AocDirectory extends AocFsComponent {
    private final List<AocFsComponent> children;

    protected AocDirectory(String name, AocFsComponent parent) {
        super(name, parent);
        children = new ArrayList<>();
    }

    @Override
    public int size() {
        return children.stream().mapToInt(AocFsComponent::size).sum();
    }

    @Override
    public List<AocFsComponent> dirsBySize(IntPredicate sizeFilter) {
        var dirs = new ArrayList<AocFsComponent>();
        if (parent() != null && sizeFilter.test(size())) {
            dirs.add(this);
        }

        children.stream()
                .filter(AocFsComponent::isDir)
                .forEach(fsComponent -> dirs.addAll(fsComponent.dirsBySize(sizeFilter)));
        return Collections.unmodifiableList(dirs);
    }

    @Override
    public void addFsComponent(AocFsComponent fsItem) {
        children.add(fsItem);
    }

    @Override
    public boolean isDir() {
        return true;
    }

    @Override
    public void printRecursively() {
        System.out.println(this);
        children.forEach(child -> {
            System.out.print("   ".repeat(depth()) + " - ");
            child.printRecursively();
        });
    }

    @Override
    public void list() {
        children.forEach(System.out::println);
    }

    @Override
    public List<AocFsComponent> children() {
        return children;
    }

    @Override
    public String toString() {
        return "dir %s".formatted(name());
    }
}
