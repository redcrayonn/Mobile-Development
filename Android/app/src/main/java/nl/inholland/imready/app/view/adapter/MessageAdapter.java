package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.view.holder.MessageViewHolder;
import nl.inholland.imready.model.user.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> implements DataHolder<List<Message>> {

    private static final int MESSAGE_LEFT = 0;
    private static final int MESSAGE_RIGHT = 1;

    private List<Message> messages;
    private LayoutInflater layoutInflater;
    private Context context;

    public MessageAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        messages = new ArrayList<>();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MESSAGE_RIGHT:
                view = layoutInflater.inflate(R.layout.list_item_message_right, parent, false);
                return new MessageViewHolder(view);
            default:
            case MESSAGE_LEFT:
                view = layoutInflater.inflate(R.layout.list_item_message_left, parent, false);
                return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.fill(context, message);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        ImReadyApplication application = ImReadyApplication.getInstance();
        boolean fromCurrentUser = message.getSenderId().equals(application.getCurrentUserId());
        return fromCurrentUser ? MESSAGE_RIGHT : MESSAGE_LEFT;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        this.notifyDataSetChanged();
    }

    @Override
    public List<Message> getData() {
        return messages;
    }

    @Override
    public void setData(List<Message> data) {
        this.messages = data;
        this.notifyDataSetChanged();
    }
}
