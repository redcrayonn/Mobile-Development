package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.util.DateUtil;

public class DialogActivityViewHolder implements FillableViewHolder<PersonalActivity> {
    private final TextView titleView;
    private final TextView deadlineView;

    public DialogActivityViewHolder(View view) {
        titleView = view.findViewById(R.id.activity_title);
        deadlineView = view.findViewById(R.id.activity_deadline);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data) {
        titleView.setText(data.getName());
        // calculate day difference
        long daysDiff = DateUtil.getTimeDifferenceDays(new Date(), data.getDeadline());
        deadlineView.setText(context.getString(R.string.deadline_days, daysDiff));
    }
}
