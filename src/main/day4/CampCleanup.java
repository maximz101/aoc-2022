package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CampCleanup {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("./input.txt"))) {
//            System.out.println(fullyContainedRangesCount(lines));
            System.out.println(overlappingRangesCount(lines));
        }
    }

    record Range(int from, int to) {
        int size() {
            return to - from + 1;
        }

        boolean hasFullOverlapWith(Range range) {
            if (size() < range.size()) {
                return from >= range.from && to <= range.to;
            } else
                return range.from >= from && range.to <= to;
        }

        boolean doNotOverlapWith(Range range) {
            return from > range.to || to < range.from;
        }

        static Range from(String rangeStr) {
            String[] split = rangeStr.split("-");
            return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    static long fullyContainedRangesCount(Stream<String> lines) {
        return lines
                .map(s -> s.split(","))
                .map(rangesStr -> new Range[]{Range.from(rangesStr[0]), Range.from(rangesStr[1])})
                .map(ranges -> ranges[0].hasFullOverlapWith(ranges[1]))
                .filter(hasFullOverlap -> hasFullOverlap)
                .count()
                ;
    }

    static long overlappingRangesCount(Stream<String> lines) {
        return lines
                .map(s -> s.split(","))
                .map(rangesStr -> new Range[]{Range.from(rangesStr[0]), Range.from(rangesStr[1])})
                .map(ranges -> ranges[0].doNotOverlapWith(ranges[1]))
                .filter(hasNoOverlap -> !hasNoOverlap)
                .count()
                ;
    }
}
