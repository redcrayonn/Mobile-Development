package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;

public interface FillableViewHolder<T> {
    void fill(@NonNull Context context, @NonNull T data);
}
