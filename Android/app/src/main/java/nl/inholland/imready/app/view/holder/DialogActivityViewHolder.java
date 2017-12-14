package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.PersonalActivity;

public class DialogActivityViewHolder implements FillableViewHolder<PersonalActivity> {
    private final TextView titleView;
    private final TextView deadlineView;

    public DialogActivityViewHolder(View view) {
        titleView = view.findViewById(R.id.activity_title);
        deadlineView = view.findViewById(R.id.activity_deadline);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data, @Nullable OnChangeListener<PersonalActivity> changeListener) {
        titleView.setText(data.getName());
        deadlineView.setText(context.getString(R.string.deadline_days, 1));
    }
}
