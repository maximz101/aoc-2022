package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MonkeyInTheMiddle {
    public static void main(String[] args) throws IOException {
        var lines = Files.readString(Path.of("./input.txt"));
        MonkeyGame monkeyGame = NotesParser.parseToMonkeyGame(lines);
//        List<RoundResult> roundResults = monkeyGame.playRounds(20);
        List<RoundResult> roundResults = monkeyGame.playRounds(10_000);
        var monkeyBusinessLevel = roundResults.stream()
                .reduce(RoundResult::merge).orElseThrow().monkeyBusinessLevel();
        System.out.println(monkeyBusinessLevel);
    }

}
