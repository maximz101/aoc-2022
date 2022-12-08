package day7;

public class ChangeDirectoryCommand implements ShellCommand {
    private final AocFileExplorer fileExplorer;
    private final String to;

    public ChangeDirectoryCommand(AocFileExplorer fileExplorer, String to) {
        this.fileExplorer = fileExplorer;
        this.to = to;
    }

    @Override
    public void execute() {
        fileExplorer.changeDirectory(to);
    }
}
