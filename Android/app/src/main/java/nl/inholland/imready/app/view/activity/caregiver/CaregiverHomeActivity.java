package nl.inholland.imready.app.view.activity.caregiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.zbra.androidlinq.Stream;
import nl.inholland.imready.R;
import nl.inholland.imready.app.view.adapter.ClientsAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.app.view.listener.OnLoadedListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.service.model.ClientsResponse;

import static br.com.zbra.androidlinq.Linq.stream;

public class CaregiverHomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, OnLoadedListener<ClientsResponse>{
    ListView listView;
    //Adapter for advanced way
    BaseAdapter listAdapter;
    private LoadMoreListener loadMoreListener;

    //Simple adapter
    //ArrayAdapter<String> listAdapter;
    //ArrayList<ClientsResponse> content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Clienten");
        }

        setContentView(R.layout.activity_caregiver_home);
        initListView();

        loadMoreListener.loadMore();
    }

    //Advanced
    private void initListView() {
        listView = findViewById(R.id.clientList);
        List<OnLoadedListener<ClientsResponse>> listeners = new ArrayList<>();
        listeners.add(this);
        listAdapter = new ClientsAdapter(this, listeners);
        loadMoreListener = (LoadMoreListener) listAdapter;
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    //Simple
    /*private void initListView(){
        listView = findViewById(R.id.clientList);
        content = new ArrayList<>();
    }*/

    @Override
    public void onLoaded(List<ClientsResponse> body) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {

    }
}
