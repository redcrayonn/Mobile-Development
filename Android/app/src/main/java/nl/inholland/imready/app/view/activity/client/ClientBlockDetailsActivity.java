package nl.inholland.imready.app.view.activity.client;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.ComponentExpandableListAdapter;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.util.LayoutUtil;

public class ClientBlockDetailsActivity extends AppCompatActivity {

    private Block block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_block_details);

        // layout helpers
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        // Get data passed from previous view
        Intent intent = getIntent();
        block = intent.getParcelableExtra(ParcelableConstants.BLOCK);

        if (block == null) {
            Toast.makeText(this, "Something went wrong whilst loading the block data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set action bar title
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(block.getName());
        }

        // Setup expandable list
        ExpandableListView expandableListView = findViewById(R.id.blockcomponents);
        expandableListView.setClickable(true);
        expandableListView.setAdapter(new ComponentExpandableListAdapter(this, block.getComponents()));
        expandableListView.expandGroup(0);
    }


}
