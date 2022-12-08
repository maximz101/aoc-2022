package day7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WeirdShellParser {
    public static final String REGEX_COMMAND_GROUP = "command";
    public static final String REGEX_CMDARGS_GROUP = "cdargs";
    public static final Pattern COMMANDS_PATTERN = Pattern.compile("^\\$\\s(?<command>ls|cd)\\s?(?<cdargs>(?<=cd\\s).+)?$");
    public static final String DIR_TOKEN = "dir";
    public static final String SEPARATOR_TOKEN = " ";

    private WeirdShellParser() {
    }

    public static AocFileExplorer parse(Stream<String> lines) {
        var fs = new AocDirectory("/", null);
        var fsExplorer = new AocFileExplorer(fs);
        var iterator = lines.iterator();
        while (iterator.hasNext()) {
            var next = iterator.next();
            var matcher = COMMANDS_PATTERN.matcher(next);
            if (matcher.matches()) {
                handleCommand(fsExplorer, matcher);
            } else {
                handleOutput(fsExplorer, next);
            }
        }
        return fsExplorer;
    }

    private static void handleOutput(AocFileExplorer fsExplorer, String next) {
        var s = next.split(SEPARATOR_TOKEN);
        var name = s[1];
        if (DIR_TOKEN.equals(s[0])) {
            fsExplorer.mkDir(name);
        } else {
            fsExplorer.createFile(name, Integer.parseInt(s[0]));
        }
    }

    private static void handleCommand(AocFileExplorer fsExplorer, Matcher matcher) {
        var command = switch (matcher.group(REGEX_COMMAND_GROUP)) {
            case "cd" -> new ChangeDirectoryCommand(fsExplorer, matcher.group(REGEX_CMDARGS_GROUP));
            case "ls" -> new NoopCommand();//if we wanna list, new ListCommand(fsExplorer);
            default -> new NoopCommand();
        };
        command.execute();
    }
}
