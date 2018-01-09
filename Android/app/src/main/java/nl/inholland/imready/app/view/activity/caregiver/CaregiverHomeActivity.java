package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.adapter.ClientsAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.model.ClientsResponse;

public class CaregiverHomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listView;
    BaseAdapter listAdapter;
    private LoadMoreListener loadMoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);



        // Toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Cliënten");
        //setSupportActionBar(toolbar);
        initListView();

        loadMoreListener.loadMore();
    }

    private void initListView() {
        listView = findViewById(R.id.listview);
        listAdapter = new ClientsAdapter(this);
        loadMoreListener = (LoadMoreListener) listAdapter;
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Get clicked client
        ClientsResponse client = (ClientsResponse)adapterView.getItemAtPosition(i);

        //Prepare necessary data
        Intent intent = new Intent(this, ClientDetailActivity.class);
        intent.putExtra("clientId", client.getId());
        intent.putExtra("notifications", client.getNotificationCount());

        //Next activity
        startActivity(intent);
    }
}
