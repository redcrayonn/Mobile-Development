package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.ComponentExpandableListAdapter;
import nl.inholland.imready.model.blocks.Block;

public class ClientBlockDetailsActivity extends AppCompatActivity {

    private Block block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_block_details);

        Intent intent = getIntent();
        block = intent.getParcelableExtra(ParcelableConstants.BLOCK);

        ExpandableListView expandableListView = findViewById(R.id.blockcomponents);
        expandableListView.setAdapter(new ComponentExpandableListAdapter(this, block.getComponents()));
    }
}
