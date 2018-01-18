package nl.inholland.imready.app.view.activity.client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.presenter.client.ClientCaretakersPresenter;
import nl.inholland.imready.app.presenter.client.ClientCaretakersPresenterImpl;
import nl.inholland.imready.app.view.adapter.ClientCaretakerAdapter;
import nl.inholland.imready.model.user.User;

public class ClientCaretakersActivity extends AppCompatActivity implements ClientCaretakersView, View.OnClickListener {

    private ClientCaretakersPresenter presenter;
    private ListView listview;
    private FloatingActionButton inviteButton;
    private ClientCaretakerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_caretakers);

        this.presenter = new ClientCaretakersPresenterImpl(this);

        listview = findViewById(R.id.listview);
        adapter = new ClientCaretakerAdapter(this);
        listview.setAdapter(adapter);
        inviteButton = findViewById(R.id.button_positive);
        inviteButton.setOnClickListener(this);

        presenter.init();
    }

    @Override
    public void onClick(View view) {
        // show dialog
        showMessage("Soon!");
    }

    @Override
    public void setCaretakers(List<User> caretakers) {
        adapter.setData(caretakers);
    }
}
