package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.PersonalComponent;

public class PersonalComponentViewHolder implements FillableViewHolder<PersonalComponent> {
    private final TextView titleView;
    private final TextView deadlineView;

    public PersonalComponentViewHolder(View v) {
        titleView = v.findViewById(R.id.component_name);
        deadlineView = v.findViewById(R.id.component_deadline);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalComponent data) {
        // title
        titleView.setText(data.getName());

        // deadline
        Date now = new Date();
        Date deadline = new Date();
        String deadlineText = null;

        long daysInFuture = 15;

        // future date is at least 1 month away
        if (daysInFuture / 30 >= 1) {
            deadlineText = context.getString(R.string.months_left, daysInFuture / 30);
        }
        // future date is week(s) away
        else if (daysInFuture / 7 >= 1) {
            deadlineText = context.getString(R.string.weeks_left, daysInFuture / 7);
        }
        // future date is day(s) away
        else {
            deadlineText = context.getString(R.string.days_left, daysInFuture);
        }

        deadlineView.setText(deadlineText);
    }
}
