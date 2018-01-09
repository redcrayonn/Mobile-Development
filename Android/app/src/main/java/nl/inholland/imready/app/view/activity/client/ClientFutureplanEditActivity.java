package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.events.FutureplanChangedEvent;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.BlockPlanExpandableListAdapter;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.Component;

public class ClientFutureplanEditActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener, SingleObserver<List<Block>> {

    private BlockPlanExpandableListAdapter adapter;
    private ExpandableListView expandableListView;
    private ArrayList<String> componentsAlreadyInFutureplan;

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

        initListView(componentsAlreadyInFutureplan);
        initData(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable state = expandableListView.onSaveInstanceState();
        outState.putParcelable(ParcelableConstants.LIST_VIEW_STATE, state);
        outState.putStringArrayList(ParcelableConstants.COMPONENT, componentsAlreadyInFutureplan);
        super.onSaveInstanceState(outState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnFutureplanChangedEvent(FutureplanChangedEvent event) {
        if (componentsAlreadyInFutureplan != null) {
            componentsAlreadyInFutureplan.remove(event.componentId);
            initData(true);
        }
    }

    private void initListView(List<String> componentsAlreadyInFutureplan) {
        expandableListView = findViewById(R.id.blocks);
        expandableListView.setClickable(true);
        expandableListView.setSaveEnabled(true);
        adapter = new BlockPlanExpandableListAdapter(this, componentsAlreadyInFutureplan);
        expandableListView.setAdapter(adapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnChildClickListener(this);
    }

    private void initData(boolean fetchFromNetwork) {
        ImReadyApplication instance = ImReadyApplication.getInstance();
        Store<List<Block>, BarCode> store = instance.getBlocksStore();

        BarCode request = BarCode.empty();

        Single<List<Block>> dataRequest;
        if (fetchFromNetwork) {
            dataRequest = store.fetch(request);
        } else {
            dataRequest = store.get(request);
        }

        dataRequest
                // required to pass the data to views (ui changes are required to be on the main thread)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // callback implementation (onSucces / onFailure)
                .subscribe(this);
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

    @Override
    public void onSubscribe(Disposable d) {
        //ignore
    }

    @Override
    public void onSuccess(List<Block> blocks) {
        adapter.setData(blocks);
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(this, R.string.block_failed, Toast.LENGTH_SHORT).show();
    }
}
