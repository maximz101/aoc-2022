package day7;

public class AocFile extends AocFsComponent {
    private final int size;
    protected AocFile(String name, int size, AocFsComponent parent) {
        super(name, parent);
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printRecursively() {
        System.out.println(this);
    }

    @Override
    public boolean isDir() {
        return false;
    }

    @Override
    public String toString() {
        return "%d %s".formatted(size, name());
    }
}
