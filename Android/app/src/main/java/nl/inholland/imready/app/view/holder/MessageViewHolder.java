package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.user.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<Message> {

    private final TextView messageView;

    public MessageViewHolder(View view) {
        super(view);
        messageView = view.findViewById(R.id.message);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull Message data) {
        messageView.setText(data.getMessage());
    }
}
