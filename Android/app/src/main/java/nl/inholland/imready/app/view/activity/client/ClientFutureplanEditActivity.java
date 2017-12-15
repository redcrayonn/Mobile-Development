package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.BlockPlanExpandableListAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.Component;

public class ClientFutureplanEditActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    private BlockPlanExpandableListAdapter adapter;

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
        adapter = new BlockPlanExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnChildClickListener(this);

        LoadMoreListener loadMoreListener = adapter;
        loadMoreListener.loadMore();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
        Component component = (Component) adapter.getChild(groupPosition, childPosition);
        if (component != null) {
            Intent intent = new Intent(this, ClientComponentEditActivity.class);
            intent.putExtra(ParcelableConstants.COMPONENT, component);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
