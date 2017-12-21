package nl.inholland.imready.app.view.activity.caregiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.adapter.ClientsAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;

public class CaregiverHomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listView;
    //Adapter for advanced way
    BaseAdapter listAdapter;
    private LoadMoreListener loadMoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Clienten");
        }

        setContentView(R.layout.activity_caregiver_home);
        initListView();

        loadMoreListener.loadMore();
    }

    private void initListView() {
        listView = findViewById(R.id.clientList);
        listAdapter = new ClientsAdapter(this);
        loadMoreListener = (LoadMoreListener) listAdapter;
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }
}
