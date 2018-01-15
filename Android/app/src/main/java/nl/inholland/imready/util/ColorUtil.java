package nl.inholland.imready.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

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
                float blendRatio = i * stepSize;
                int blended = ColorUtils.blendARGB(colorA, colorB, blendRatio);
                output.add(blended);
            }
        }
        return output;
    }

    /*
    TODO: fix this method to work with intermediate colors
     */
    public static List<Integer> between(int colorA, int colorB, List<Integer> colors, int amount) {
        List<Integer> output = new ArrayList<>();

        if (amount <= colors.size()) {
            output.addAll(colors);
        } else {
            int size = colors.size();
            float stepSize = 1f / size;
            for (int i = 0; i < size; i++) {
                // if intermediate colors array size is 1 (total colors 3), then if i <= 1,
                // user intermediate as colorB, else use intermediate as colorA

                // if intermediateColors size = 1 (total 3)
                // i == 1 use c1 as colorB (colorA --> c1)
                // i == 2 use c1 as colorA (c1 --> colorB)


                float blendRatio = i * stepSize;
                int blended = ColorUtils.blendARGB(colorA, colorB, blendRatio);
                output.add(blended);
            }
        }
        return output;
    }

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }
}
