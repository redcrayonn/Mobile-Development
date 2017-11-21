package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.MessageViewHolder;
import nl.inholland.imready.model.user.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<Message> messages;
    private LayoutInflater layoutInflator;
    private Context context;

    public MessageAdapter(Context context) {
        layoutInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = layoutInflator.inflate(R.layout.list_item_message_left, parent, false);
                return new MessageViewHolder(view);
            case 1:
                view = layoutInflator.inflate(R.layout.list_item_message_right, parent, false);
                return new MessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        Message message = new Message();
        message.setMessage("test");
        message.setDatetime(new Date());

        holder.fill(context, message);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}
