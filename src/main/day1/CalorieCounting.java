package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class CalorieCounting {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
            var list = new ArrayList<Integer>();
            list.add(0);
            var i = new AtomicInteger(0);
            lines.forEach(s -> {
                if ("".equals(s)) {
                    list.add(0);
                    i.incrementAndGet();
                } else {
                    list.set(i.get(), list.get(i.get()) + Integer.parseInt(s));
                }
            });
            int top1 = list.stream().mapToInt(value -> value).max().orElse(-1);
            System.out.println(top1);
            // top 3
            int top3 = list.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(value -> value).sum();
            System.out.println(top3);
        }
    }
}
