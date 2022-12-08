package day7;

import java.util.List;
import java.util.function.IntPredicate;

public abstract class AocFsComponent {
    private final String name;
    private final AocFsComponent parent;

    protected AocFsComponent(String name, AocFsComponent parent) {
        this.name = name;
        this.parent = parent;
    }

    public String name() {
        return name;
    }

    public abstract int size();

    public List<AocFsComponent> dirsBySize(IntPredicate sizeFilter) {
        throw new UnsupportedOperationException();
    }

    public void addFsComponent(AocFsComponent fsItem) {
        throw new UnsupportedOperationException();
    }

    public AocFsComponent parent() {
        return parent;
    }

    public abstract boolean isDir();

    public abstract void printRecursively();

    public void list() {
        throw new UnsupportedOperationException();
    }

    public List<AocFsComponent> children() {
        throw new UnsupportedOperationException();
    }

    protected int depth() {
        var i = 0;
        var p = parent;
        while (p != null) {
            i++;
            p = p.parent;
        }
        return i;
    }
}
