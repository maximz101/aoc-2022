package day7;

import java.util.List;
import java.util.function.IntPredicate;

public class AocFileExplorer {
    public static final String ROOT_LOCATION = "/";
    private AocFsComponent currentDir;

    public AocFileExplorer(AocFsComponent currentDir) {
        this.currentDir = currentDir;
    }

    public void changeDirectory(String to) {
        // todo : doesn't handle all abs path
        switch (to) {
            case ROOT_LOCATION -> {
                while (currentDir.parent() != null) {
                    currentDir = currentDir.parent();
                }
            }
            case ".." -> {
                if (currentDir.parent() != null) {
                    currentDir = currentDir.parent();
                }
            }
            default -> currentDir.children().stream()
                    .filter(AocFsComponent::isDir)
                    .filter(fsComponent -> fsComponent.name().equals(to))
                    .findFirst()
                    .ifPresent(fsComponent -> currentDir = fsComponent);
        }
    }

    public void mkDir(String name) {
        currentDir.addFsComponent(new AocDirectory(name, currentDir));
    }

    public void createFile(String name, int size) {
        currentDir.addFsComponent(new AocFile(name, size, currentDir));
    }

    public List<AocFsComponent> dirsBySize(IntPredicate filter) {
        var current = currentDir;
        changeDirectory(ROOT_LOCATION);
        var r = currentDir.dirsBySize(filter);
        currentDir = current;
        return r;
    }

    public void printRecursively() {
        currentDir.printRecursively();
    }

    public int getUsedSize() {
        var current = currentDir;
        changeDirectory(ROOT_LOCATION);
        int size = currentDir.size();
        currentDir = current;
        return size;
    }

    public void list() {
        currentDir.list();
    }
}
