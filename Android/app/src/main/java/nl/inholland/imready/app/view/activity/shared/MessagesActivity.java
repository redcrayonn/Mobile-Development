package nl.inholland.imready.app.view.activity.shared;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.presenter.shared.MessagesPresenterImpl;
import nl.inholland.imready.app.view.adapter.MessageAdapter;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.rest.MessageBaseService;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener, MessagesView {

    private MessageBaseService messageService;
    private MessageAdapter adapter;

    private ImageButton sendButton;
    private EditText inputText;

    private MessagesPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        this.presenter = new MessagesPresenterImpl(this);

        messageService = ApiManager.getClient(true).getMessageService();

        //initRecyclerView();
        initUserInput();
    }

    private void initUserInput() {
        sendButton = findViewById(R.id.message_send);
        sendButton.setOnClickListener(this);
        inputText = findViewById(R.id.message_input);
    }

    private void initRecyclerView() {
        RecyclerView view = findViewById(R.id.messages);
        adapter = new MessageAdapter(this);
        view.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_send:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        if (sendButton != null && inputText != null) {
            if (inputText.getText().toString().isEmpty()) {
                return;
            }
            Message message = new Message();
            message.setMessage(inputText.getText().toString());
            presenter.sendMessage(message);
        } else {
            Toast.makeText(this, "Something went wrong trying to send the text", Toast.LENGTH_SHORT).show();
        }
    }
}
