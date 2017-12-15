package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.Component;

public class ComponentPlanViewHolder implements FillableViewHolder<Component> {
    private final TextView titleView;
    private final ImageView imageView;
    private final boolean active;

    public ComponentPlanViewHolder(View view, boolean active) {
        this.titleView = view.findViewById(R.id.component_name);
        this.imageView = view.findViewById(R.id.add_indicator);
        this.active = active;
    }

    @Override
    public void fill(@NonNull Context context, @NonNull Component data, @Nullable OnChangeListener<Component> changeListener) {
        titleView.setText(data.getName());
        // whether the user already has activated the underlaying component
        int visible = active ? View.INVISIBLE : View.VISIBLE;
        imageView.setVisibility(visible);
    }
}
