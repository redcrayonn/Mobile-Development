package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.events.FeedbackViewEvent;
import nl.inholland.imready.app.view.adapter.CaregiverPlanExpandAdapter;
import nl.inholland.imready.app.view.adapter.PersonalBlockAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.PersonalActivity;

public class CaregiverFutureplanOverviewActivity extends AppCompatActivity implements  ExpandableListView.OnChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    String clientId;
    String clientName;
    private CaregiverPlanExpandAdapter adapter;
    private LoadMoreListener loadMoreListener;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_futureplan_overview);

        //Get data from parent view
        Intent intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        clientName = intent.getStringExtra("clientName");

        //Use data for title
        TextView clientNameText = findViewById(R.id.clientName);
        clientNameText.setText(clientName);

        //Initialise expandable list
        initRefresh();
        initExpandList();
        loadMoreListener.loadMore();
    }

    private void initRefresh() {
        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initExpandList() {
        ExpandableListView expandableListView = findViewById(R.id.blocks);
        //expandableListView.setClickable(true);
        adapter = new CaregiverPlanExpandAdapter(this, clientId, refreshLayout);
        loadMoreListener = (LoadMoreListener) adapter;
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        PersonalActivity activity = (PersonalActivity) adapter.getChild(i,i1);
        gotoFeedbackActivity(activity);
        return true;
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
        EventBus.getDefault().postSticky(new FeedbackViewEvent(activity, clientName, clientId));
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                loadMoreListener.loadMore();
            }
        }
    }

    @Override
    public void onRefresh() {
        loadMoreListener.loadMore();
    }
}