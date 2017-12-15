package nl.inholland.imready.app.view.activity.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.adapter.BlockPlanExpandableListAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;

public class ClientFutureplanEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_futureplan_edit);

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.plan);
        }

        ExpandableListView expandableListView = findViewById(R.id.blocks);
        expandableListView.setClickable(true);
        BlockPlanExpandableListAdapter adapter = new BlockPlanExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);

        LoadMoreListener loadMoreListener = (LoadMoreListener) adapter;
        loadMoreListener.loadMore();
    }
}
