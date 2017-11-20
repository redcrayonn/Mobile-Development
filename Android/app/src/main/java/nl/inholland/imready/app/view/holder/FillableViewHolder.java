package nl.inholland.imready.app.view.holder;

import android.content.Context;

public interface FillableViewHolder<T> {
    void fill(Context context, T data);
}
