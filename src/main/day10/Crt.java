package day10;

import java.util.Set;
import java.util.function.Consumer;

public class Crt implements Consumer<Integer> {
    private static final int LINE_LENGTH = 40;
    private int pixelPosition = 0;

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void accept(Integer spritePosition) {
        var sprites = Set.of(spritePosition - 1, spritePosition, spritePosition + 1);

        if (sprites.contains(pixelPosition)) {
            System.out.print("#");
            sb.append("#");
        } else {
            System.out.print(".");
            sb.append(".");
        }

        pixelPosition++;
        if (pixelPosition == LINE_LENGTH) {
            pixelPosition = 0;
            System.out.println();
            sb.append('\n');
        }
    }

    public String getOutput() {
        return sb.toString();
    }
}
