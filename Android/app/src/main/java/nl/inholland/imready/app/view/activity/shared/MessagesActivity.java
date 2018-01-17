package nl.inholland.imready.app.view.activity.shared;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.presenter.shared.MessagesPresenterImpl;
import nl.inholland.imready.app.view.adapter.MessageAdapter;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.rest.MessageBaseService;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener, MessagesView {

    private MessageAdapter adapter;

    private ImageButton sendButton;
    private EditText inputText;

    private MessagesPresenterImpl presenter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        MessageBaseService messageService = ApiManager.getClient(true).getMessageService();
        this.presenter = new MessagesPresenterImpl(this, messageService);

        initRefreshView();
        initRecyclerView();
        initUserInput();

        presenter.init();
    }

    private void initRefreshView() {
        refreshLayout = findViewById(R.id.pull_refresh);
    }

    private void initRecyclerView() {
        RecyclerView view = findViewById(R.id.messages);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(this);
        view.setAdapter(adapter);
    }

    private void initUserInput() {
        sendButton = findViewById(R.id.message_send);
        sendButton.setOnClickListener(this);
        inputText = findViewById(R.id.message_input);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_send:
                if (sendButton != null && inputText != null) {
                    if (inputText.getText().toString().isEmpty()) {
                        return;
                    }
                    presenter.sendMessage(inputText.getText().toString());
                } else {
                    showMessage("Something went wrong trying to send the text");
                }
                break;
        }
    }

    @Override
    public void addMessage(Message message) {
        adapter.addMessage(message);
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setViewData(List<Message> messages) {
        adapter.setData(messages);
    }
}
