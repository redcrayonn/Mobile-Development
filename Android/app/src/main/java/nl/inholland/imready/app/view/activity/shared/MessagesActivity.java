package nl.inholland.imready.app.view.activity.shared;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.adapter.MessageAdapter;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        MessageAdapter adapter = new MessageAdapter(this);
        RecyclerView view = findViewById(R.id.messages);
        view.setAdapter(adapter);
    }
}
