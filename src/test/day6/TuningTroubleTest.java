package day6;

import day6.TuningTrouble.ElfSignalDecoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

class TuningTroubleTest {

    @ParameterizedTest
    @CsvSource(value = {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb:7",
            "bvwbjplbgvbhsrlpgdmjqwftvncz:5",
            "nppdvjthqldpwncqszvftbrmjlhg:6",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg:10",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw:11",
    },
            delimiter = ':')
    void firstMarkerTest4(String input, int expected) throws IOException {
        byte[] bytes = input.getBytes();
        var elfSignalDecoder = new ElfSignalDecoder(new ByteArrayInputStream(bytes), 4);
        Assertions.assertEquals(expected, elfSignalDecoder.firstMarker());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb:19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz:23",
            "nppdvjthqldpwncqszvftbrmjlhg:23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg:29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw:26",
    },
            delimiter = ':')
    void firstMarkerTest14(String input, int expected) throws IOException {
        byte[] bytes = input.getBytes();
        var elfSignalDecoder = new ElfSignalDecoder(new ByteArrayInputStream(bytes), 14);
        Assertions.assertEquals(expected, elfSignalDecoder.firstMarker());
    }

}