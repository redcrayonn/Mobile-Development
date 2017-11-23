package nl.inholland.imready.app.view.activity.shared;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.view.adapter.MessageAdapter;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity implements View.OnClickListener, Callback<EmptyResponse> {

    private MessageBaseService messageService;
    private MessageAdapter adapter;

    private ImageButton sendButton;
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messageService = ApiManager.getClient(true).getMessageService();

        initRecyclerView();
        initUserInput();
    }

    private void initUserInput() {
        sendButton = findViewById(R.id.message_send);
        sendButton.setOnClickListener(this);
        inputText = findViewById(R.id.message_input);
    }

    private void initRecyclerView() {
        RecyclerView view = findViewById(R.id.messages);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new MessageAdapter(this, messageService);
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
            adapter.addMessage(message);
            messageService.postMessage(message.getSenderId(), message).enqueue(this);

        } else {
            Toast.makeText(this, "Something went wrong trying to send the text", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {

    }

    @Override
    public void onFailure(Call<EmptyResponse> call, Throwable t) {

    }
}
