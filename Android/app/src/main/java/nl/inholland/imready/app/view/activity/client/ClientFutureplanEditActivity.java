package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.events.ComponentDetailViewEvent;
import nl.inholland.imready.app.presenter.client.ClientFutureplanEditPresenterImpl;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.BlockPlanExpandableListAdapter;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.Component;

public class ClientFutureplanEditActivity extends AppCompatActivity implements ClientFutureplanEditView, ExpandableListView.OnChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    static final int COMPONENT_ADD_REQUEST = 1;

    private BlockPlanExpandableListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ExpandableListView expandableListView;
    private ArrayList<String> componentsAlreadyInFutureplan;
    private ClientFutureplanEditPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_futureplan_edit);

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.plan);
        }

        // main entry
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            componentsAlreadyInFutureplan = intent.getStringArrayListExtra(ParcelableConstants.COMPONENT);
        }
        // from restored state since getIntent will not contain the array needed
        else {
            componentsAlreadyInFutureplan = savedInstanceState.getStringArrayList(ParcelableConstants.COMPONENT);
        }

        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(this);

        initListView(componentsAlreadyInFutureplan);

        presenter = new ClientFutureplanEditPresenterImpl(this, ImReadyApplication.getInstance().getBlocksStore());
        presenter.init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable state = expandableListView.onSaveInstanceState();
        outState.putParcelable(ParcelableConstants.LIST_VIEW_STATE, state);
        outState.putStringArrayList(ParcelableConstants.COMPONENT, componentsAlreadyInFutureplan);
        super.onSaveInstanceState(outState);
    }

    private void initListView(List<String> componentsAlreadyInFutureplan) {
        showRefreshing();
        expandableListView = findViewById(R.id.blocks);
        expandableListView.setClickable(true);
        expandableListView.setSaveEnabled(true);
        adapter = new BlockPlanExpandableListAdapter(this, componentsAlreadyInFutureplan);
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnChildClickListener(this);
        stopRefreshing();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
        Component component = (Component) adapter.getChild(groupPosition, childPosition);
        goToComponentView(component);
        return component != null;
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

    @Override
    public void onRefresh() {
        presenter.refreshData();
    }

    @Override
    public void setData(List<Block> blocks) {
        adapter.setData(blocks);
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void goToComponentView(Component component) {
        if (component != null) {
            Intent intent = new Intent(this, ClientComponentEditActivity.class);
            EventBus.getDefault().postSticky(new ComponentDetailViewEvent(component));
            startActivityForResult(intent, COMPONENT_ADD_REQUEST);
        } else {
            showMessage(getString(R.string.unknown_error));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPONENT_ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                String componentAddedId = data.getStringExtra(ParcelableConstants.COMPONENT);
                componentsAlreadyInFutureplan.add(componentAddedId);
                // update the visible list
                adapter.updateComponents(componentsAlreadyInFutureplan);
                setResult(RESULT_OK);
            }
        }
    }
}
