package nl.inholland.imready.app.view.activity.shared;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.presenter.shared.MessagesPresenterImpl;
import nl.inholland.imready.app.view.adapter.MessageAdapter;
import nl.inholland.imready.model.user.Chat;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.rest.MessageBaseService;
import nl.inholland.imready.util.ColorUtil;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener, MessagesView, SwipeRefreshLayout.OnRefreshListener {

    private MessageAdapter adapter;

    private ImageButton sendButton;
    private EditText inputText;

    private MessagesPresenterImpl presenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        MessageBaseService messageService = ApiManager.getClient().getMessageService();
        ImReadyApplication instance = ImReadyApplication.getInstance();
        Store<Chat, BarCode> messagesStore = instance.getMessagesStore();
        this.presenter = new MessagesPresenterImpl(this, messageService, messagesStore, instance.getCurrentUserId(), "f8b1282e-ee65-45d2-ac13-676a8cbca8d3");

        initRefreshView();
        initRecyclerView();
        initUserInput();

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Berichten");
        }

        presenter.init();
    }

    private void initRefreshView() {
        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initUserInput() {
        sendButton = findViewById(R.id.message_send);
        sendButton.setOnClickListener(this);
        inputText = findViewById(R.id.message_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_messages, menu);

        MenuItem refreshItem = menu.findItem(R.id.refresh);
        ColorUtil.tintMenuIcon(this, refreshItem, android.R.color.white);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                presenter.refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if (messages != null && messages.size() > 0)
            recyclerView.smoothScrollToPosition(messages.size() - 1);
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }
}
