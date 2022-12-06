package day6;

import java.io.*;
import java.nio.CharBuffer;
import java.util.HashMap;

public class TuningTrouble {
    public static void main(String[] args) throws IOException {
        var elfSignalDecoder = new ElfSignalDecoder(new FileInputStream("./input.txt"), 14);
        System.out.println(elfSignalDecoder.firstMarker());
    }

    static class ElfSignalDecoder {
        final InputStream signal;
        final int markerLength;

        public ElfSignalDecoder(InputStream input, int markerLength) {
            signal = input;
            this.markerLength = markerLength;
        }

        public int firstMarker() throws IOException {
            var reader = new BufferedReader(new InputStreamReader(signal));
            var charBuffer = CharBuffer.allocate(markerLength);
            var charCount = new HashMap<Character, Integer>(markerLength);
            var index = 1;
            int nextChar;
            while ((nextChar = reader.read()) != -1) {
                if (charBuffer.position() == markerLength) {
                    charBuffer.rewind();
                }
                charCount.computeIfPresent(charBuffer.charAt(0), (character, count) -> {
                    var nc = count - 1;
                    return nc == 0 ? null : nc;
                });
                charBuffer.put((char) nextChar);
                charCount.merge((char) nextChar, 1, Integer::sum);
                if (charCount.keySet().size() == markerLength) {
                    return index;
                }
                index++;
            }
            if (charCount.keySet().size() < markerLength) {
                return -1;
            }
            return index;
        }
    }
}
