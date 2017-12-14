package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nl.inholland.imready.app.view.listener.OnChangeListener;

public interface FillableViewHolder<T> {
    void fill(@Nullable Context context, @NonNull T data, @Nullable OnChangeListener<T> changeListener);
}
