package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.Activity;

public class DialogActivityViewHolder implements FillableViewHolder<Activity> {
    private final TextView titleView;
    private final TextView deadlineView;

    public DialogActivityViewHolder(View view) {
        titleView = view.findViewById(R.id.activity_title);
        deadlineView = view.findViewById(R.id.activity_deadline);
    }

    @Override
    public void fill(Context context, Activity data) {
        titleView.setText(data.getName());
        deadlineView.setText(context.getString(R.string.deadline_days, 1));
    }
}
