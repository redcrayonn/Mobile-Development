package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.activity.shared.MessagesActivity;
import nl.inholland.imready.app.view.adapter.BlockAdapter;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.Block;

public class ClientHomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private BaseAdapter gridAdapter;
    private LoadMoreListener loadMoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        initGridView();
        initToolbarAndDrawer();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
        loadMoreListener.loadMore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_home, menu);
        TextView userText = findViewById(R.id.username);
        userText.setText("test name");
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
            default:
                return super.onOptionsItemSelected(item);
        }
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
        }
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.blocks);
        gridAdapter = new BlockAdapter(this);
        loadMoreListener = (LoadMoreListener) gridAdapter;
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);
    }

    private void initToolbarAndDrawer() {
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawer = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClosed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
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

    private void gotoMessages() {
        Intent intent = new Intent(this, MessagesActivity.class);
        startActivity(intent);
    }

    private void gotoNotifications() {
        Toast.makeText(this, "notifications", Toast.LENGTH_SHORT).show();
    }

    private void gotoFamily() {
        Toast.makeText(this, "family", Toast.LENGTH_SHORT).show();
    }

    private void gotoInfo() {
        Toast.makeText(this, "info", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, ClientBlockDetailsActivity.class);
        Block block = (Block) adapterView.getItemAtPosition(position);
        intent.putExtra(ParcelableConstants.BLOCK, block);
        startActivity(intent);
    }
}
