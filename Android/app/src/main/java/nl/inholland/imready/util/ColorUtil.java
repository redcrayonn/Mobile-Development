package nl.inholland.imready.util;

import android.support.v4.graphics.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    public static List<Integer> between(int colorA, int colorB, int amount) {
        List<Integer> output = new ArrayList<>();

        if (amount <= 1) {
            output.add(colorA);
        } else if (amount == 2) {
            output.add(colorA);
            output.add(colorB);
        } else {
            float stepSize = 1f / amount;
            for (int i = 0; i < amount; i++) {
                int blended = ColorUtils.blendARGB(colorA, colorB, i * stepSize);
                output.add(blended);
            }
        }
        return output;
    }
}
