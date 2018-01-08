package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.zbra.androidlinq.Stream;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.logic.events.FutureplanChangedEvent;
import nl.inholland.imready.app.logic.events.PersonalBlockLoadedEvent;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.activity.LoginActivity;
import nl.inholland.imready.app.view.activity.shared.MessagesActivity;
import nl.inholland.imready.app.view.adapter.PersonalBlockAdapter;
import nl.inholland.imready.app.view.fragment.WelcomeDialogFragment;
import nl.inholland.imready.model.blocks.Component;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.model.enums.BlockType;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.model.FutureplanResponse;

import static br.com.zbra.androidlinq.Linq.stream;

public class ClientHomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SingleObserver<FutureplanResponse> {

    private static final String STATE_POPUP = "popup";

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private PersonalBlockAdapter gridAdapter;

    private boolean popupShown;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        initGridView();
        initRefresh();
        initToolbarAndDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initData(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawerToggle.syncState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(STATE_POPUP, popupShown);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        popupShown = savedInstanceState.getBoolean(STATE_POPUP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_home, menu);
        TextView userText = findViewById(R.id.username);

        SharedPreferences settings = getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
        String username = settings.getString(PreferenceConstants.USER_NAME, getString(R.string.default_username));
        userText.setText(username);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.messages:
                gotoMessages();
                return true;
            case R.id.notifications:
                gotoNotifications();
                return true;
            case R.id.refresh:
                refreshData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshData() {
        initData(true);
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_messages:
                gotoMessages();
                break;
            case R.id.drawer_family:
                gotoFamily();
                break;
            case R.id.drawer_info:
                gotoInfo();
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.blocks);
        gridAdapter = new PersonalBlockAdapter(this);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);
    }

    private void initToolbarAndDrawer() {
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.futureplan);

        // Drawer
        drawer = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClosed);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Button messagesButton = drawer.findViewById(R.id.drawer_messages);
        messagesButton.setOnClickListener(this);

        Button familyButton = drawer.findViewById(R.id.drawer_family);
        familyButton.setOnClickListener(this);

        Button infoButton = drawer.findViewById(R.id.drawer_info);
        infoButton.setOnClickListener(this);

        Button logoutButton = drawer.findViewById(R.id.logout);
        logoutButton.setOnClickListener(this);
    }

    private void initRefresh() {
        refreshLayout = findViewById(R.id.pull_refresh);
        refreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void initData(boolean fetchFromNetwork) {
        ImReadyApplication instance = ImReadyApplication.getInstance();
        UserCache cache = instance.getCache(UserRole.CLIENT);
        Store<FutureplanResponse, BarCode> store = instance.getFutureplanStore();

        // cache request param, where type is the key for the cache and key the unique identifier
        BarCode request = new BarCode("future_plan", cache.getUserId());

        // request data from the futureplan store
        Single<FutureplanResponse> dataRequest;
        if (fetchFromNetwork) {
            // get data from network
            dataRequest = store.fetch(request);
        } else {
            // get data from disk and store it in-memory
            // if data is not present it does a network call to retrieve the data from online
            dataRequest = store.get(request);
        }

        dataRequest
            // required to pass the data to views (ui changes are required to be on the main thread)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // callback implementation (onSucces / onFailure)
            .subscribe(this);
    }

    private void gotoMessages() {
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    private void gotoNotifications() {
        Toast.makeText(this, "Soon!", Toast.LENGTH_SHORT).show();
    }

    private void gotoFamily() {
        Toast.makeText(this, "Soon!", Toast.LENGTH_SHORT).show();
    }

    private void gotoInfo() {
        Toast.makeText(this, "Soon!", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        PersonalBlock block = (PersonalBlock) adapterView.getItemAtPosition(position);
        if (block.getBlock().getType() == BlockType.ADD) {
            Intent intent = new Intent(this, ClientFutureplanEditActivity.class);
            List<PersonalBlock> personalBlocks = gridAdapter.getData();
            List<String> componentIds = stream(personalBlocks)
                    .selectMany(PersonalBlock::getComponents)
                    .select(PersonalComponent::getComponent)
                    .select(Component::getId)
                    .toList();
            // pass in a list of component id's which tells the next view what options to disable
            intent.putStringArrayListExtra(ParcelableConstants.COMPONENT, (ArrayList<String>) componentIds);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ClientBlockDetailsActivity.class);
            intent.putExtra(ParcelableConstants.BLOCK, block);
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPersonalBlockLoadedEvent(PersonalBlockLoadedEvent event) {
        Stream<PersonalComponent> components = stream(event.blocks).selectMany(PersonalBlock::getComponents);
        Stream<PersonalActivity> activities = components.selectMany(PersonalComponent::getActivities);
        List<PersonalActivity> todoSoon = activities.where(activity -> activity.getStatus() == BlockPartStatus.ONGOING).toList();

        if (!todoSoon.isEmpty() && !popupShown) {
            WelcomeDialogFragment dialogWelcome = new WelcomeDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ParcelableConstants.TODO_ACTIVITIES, (ArrayList<? extends Parcelable>) todoSoon);
            dialogWelcome.setArguments(bundle);
            dialogWelcome.show(getSupportFragmentManager(), WelcomeDialogFragment.TAG);
        }
        popupShown = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFutureplanChanged(FutureplanChangedEvent event) {
        initData(true);
    }

    @Override
    public void onSubscribe(Disposable request) {
        // ignore
    }

    @Override
    public void onSuccess(FutureplanResponse response) {
        gridAdapter.setData(response.getBlocks());
        resetUi();
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(ClientHomeActivity.class.getSimpleName(), throwable.getMessage(), throwable);
        Toast.makeText(this, R.string.personal_block_failed, Toast.LENGTH_SHORT).show();
        resetUi();
    }

    private void resetUi() {
        refreshLayout.setRefreshing(false);
    }
}
