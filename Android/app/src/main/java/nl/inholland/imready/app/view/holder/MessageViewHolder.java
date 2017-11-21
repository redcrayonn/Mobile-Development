package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;

import nl.inholland.imready.model.user.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<Message> {
    private String message;
    private Date timeSent;

    public MessageViewHolder(View view) {
        super(view);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    @Override
    public void fill(Context context, Message data) {
        setMessage(data.getMessage());
        setTimeSent(data.getDatetime());
    }
}
