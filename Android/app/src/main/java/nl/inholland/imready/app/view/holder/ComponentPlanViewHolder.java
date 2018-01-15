package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.Component;

public class ComponentPlanViewHolder implements FillableViewHolder<Component> {
    private final TextView titleView;

    public ComponentPlanViewHolder(View view) {
        this.titleView = view.findViewById(R.id.component_name);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull Component data) {
        titleView.setText(data.getName());
    }
}
