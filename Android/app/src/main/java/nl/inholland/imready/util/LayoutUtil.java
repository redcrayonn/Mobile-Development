package nl.inholland.imready.util;

import android.view.View;
import android.view.ViewGroup;

public class LayoutUtil {
    public static void invalidateRecursive(ViewGroup layout) {
        int count = layout.getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = layout.getChildAt(i);
            if (child instanceof ViewGroup)
                invalidateRecursive((ViewGroup) child);
            else
                child.invalidate();
        }
    }
}
