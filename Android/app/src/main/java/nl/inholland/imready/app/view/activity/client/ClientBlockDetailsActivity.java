package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.PersonalComponentExpandableListAdapter;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;

import static br.com.zbra.androidlinq.Linq.stream;

public class ClientBlockDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_block_details);

        // Get data passed from previous view
        Intent intent = getIntent();
        PersonalBlock block = intent.getParcelableExtra(ParcelableConstants.BLOCK);

        if (block == null) {
            Toast.makeText(this, "Something went wrong whilst loading the block data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(block.getName());
        }

        // Setup expandable list
        ExpandableListView expandableListView = findViewById(R.id.blockcomponents);
        expandableListView.setClickable(true);
        // order by the closest deadline
        List<PersonalComponent> components = stream(block.getComponents())
                .orderBy(c -> c.getDeadline())
                .toList();
        BaseExpandableListAdapter adapter = new PersonalComponentExpandableListAdapter(this, components);
        expandableListView.setAdapter(adapter);


        PersonalComponent component = intent.getParcelableExtra(ParcelableConstants.COMPONENT);
        if (component == null) {
            expandableListView.expandGroup(0);
        } else {
            int index = block.getComponents().indexOf(component);
            expandableListView.expandGroup(index);
        }
    }


}
