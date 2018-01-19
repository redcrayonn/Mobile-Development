package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.adapter.ClientsAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.model.ClientsResponse;

public class CaregiverHomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SingleObserver<List<ClientsResponse>> {
    ListView listView;
    ClientsAdapter listAdapter;
    private SwipeRefreshLayout refreshLayout;
    private Store<List<ClientsResponse>, BarCode> clientsStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);

        initRefresh();
        initListView();

        clientsStore = ImReadyApplication.getInstance().getClientsStore();

        BarCode barCode = new BarCode("clients", ImReadyApplication.getInstance().getCurrentUserId());
        clientsStore.get(barCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        refreshLayout.setRefreshing(true);
    }

    private void initRefresh() {
        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initListView() {
        listView = findViewById(R.id.listview);
        listAdapter = new ClientsAdapter(this, refreshLayout);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Get clicked client
        ClientsResponse client = (ClientsResponse)adapterView.getItemAtPosition(i);

        //Prepare necessary data
        Intent intent = new Intent(this, ClientDetailActivity.class);
        intent.putExtra("clientId", client.getId());
        intent.putExtra("notifications", client.getNotificationCount());

        //Next activity
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        BarCode barCode = new BarCode("clients", ImReadyApplication.getInstance().getCurrentUserId());
        clientsStore.fetch(barCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        //ignore
    }

    @Override
    public void onSuccess(List<ClientsResponse> clientsResponses) {
        listAdapter.setData(clientsResponses);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        refreshLayout.setRefreshing(false);
    }
}
