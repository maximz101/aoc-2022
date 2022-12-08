package day7;

public class ListCommand implements ShellCommand {
    private final AocFileExplorer fileExplorer;

    public ListCommand(AocFileExplorer fileExplorer) {
        this.fileExplorer = fileExplorer;
    }

    @Override
    public void execute() {
        fileExplorer.list();
    }
}
