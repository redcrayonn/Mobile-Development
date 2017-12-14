package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.Component;

public class ComponentViewHolder implements FillableViewHolder<Component> {
    private final TextView titleView;
    private final TextView deadlineView;

    public ComponentViewHolder(View v) {
        titleView = v.findViewById(R.id.component_name);
        deadlineView = v.findViewById(R.id.component_deadline);
    }

    @Override
    public void fill(@Nullable Context context, @NonNull Component data, @Nullable OnChangeListener<Component> changeListener) {
        // title
        titleView.setText(data.getName());

        // deadline
        Date now = new Date();
        String deadlineText = null;

        Random rng = new Random();
        long msDay = 1000 * 60 * 60 * 24;
        long daysInFuture = (rng.nextInt(40) + 1); // between 1 and 10 (inclusive) days in the future

        // future date is at least 1 month away
        if (daysInFuture / 30 >= 1) {
            deadlineText = "Nog " + daysInFuture / 30 + " maanden";
        }
        // future date is week(s) away
        else if (daysInFuture / 7 >= 1) {
            deadlineText = "Nog " + daysInFuture / 7 + " weken";
        }
        // future date is day(s) away
        else {
            deadlineText = "Nog " + daysInFuture + " dagen";
        }

        deadlineView.setText(deadlineText);
    }
}
