package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import nl.inholland.imready.R;
import nl.inholland.imready.model.user.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<Message> {

    private final TextView contentTextView;
    private final TextView sentTextView;

    public MessageViewHolder(View view) {
        super(view);
        contentTextView = view.findViewById(R.id.content);
        sentTextView = view.findViewById(R.id.message_sent_time);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull Message data) {
        contentTextView.setText(data.getMessage());

        Date sent = data.getSent();
        CharSequence sentText = DateUtils.getRelativeTimeSpanString(sent.getTime());
        sentTextView.setText(sentText);
    }
}
