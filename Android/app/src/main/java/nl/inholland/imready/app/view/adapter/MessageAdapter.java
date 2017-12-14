package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.MessageViewHolder;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> implements Callback<List<Message>> {

    private List<Message> messages;
    private LayoutInflater layoutInflater;
    private Context context;

    private MessageBaseService messageService;

    public MessageAdapter(Context context, MessageBaseService messageService) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;

        this.messageService = messageService;
        this.messageService.getMessages("abc", null).enqueue(this);
        messages = new ArrayList<>();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
            case 1:
                view = layoutInflater.inflate(R.layout.list_item_message_left, parent, false);
                return new MessageViewHolder(view);
            case 2:
                view = layoutInflater.inflate(R.layout.list_item_message_right, parent, false);
                return new MessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.fill(context, message, null);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        // TODO: determine type based on message sender
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
        if (response.isSuccessful() && response.body() != null) {
            messages = response.body();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
        // failed
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }
}
