package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.adapter.CaregiverPlanExpandAdapter;
import nl.inholland.imready.app.view.adapter.PersonalBlockAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.PersonalActivity;

public class CaregiverFutureplanOverviewActivity extends AppCompatActivity implements  ExpandableListView.OnChildClickListener{

    String clientId;
    private CaregiverPlanExpandAdapter adapter;
    private LoadMoreListener loadMoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_futureplan_overview);

        //Get data from parent view
        Intent intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        String clientName = intent.getStringExtra("clientName");

        //Use data for title
        TextView clientNameText = findViewById(R.id.clientName);
        clientNameText.setText(clientName);

        //Initialise expandable list
        initExpandList();
        loadMoreListener.loadMore();
    }

    private void initExpandList() {
        ExpandableListView expandableListView = findViewById(R.id.blocks);
        //expandableListView.setClickable(true);
        adapter = new CaregiverPlanExpandAdapter(this, clientId);
        loadMoreListener = (LoadMoreListener) adapter;
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        Toast.makeText(this, "Uh oh, er is iets fout gegaan...", Toast.LENGTH_SHORT).show();

        PersonalActivity activity = (PersonalActivity) adapter.getChild(i,i1);
        gotoFeedbackActivity(activity);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // fix for a navigation bug in android; https://stackoverflow.com/a/29464116
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoFeedbackActivity(PersonalActivity activity) {
        Intent intent = new Intent(this, CaregiverFeedbackActivity.class);
        intent.putExtra("activity", activity);
        startActivity(intent);
    }
}
