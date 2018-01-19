package nl.inholland.imready.app.view.activity.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.logic.events.ComponentDetailViewEvent;
import nl.inholland.imready.app.presenter.client.ClientComponentEditPresenter;
import nl.inholland.imready.app.presenter.client.ClientComponentEditPresenterImpl;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.fragment.ComponentAddDialogFragment;
import nl.inholland.imready.app.view.listener.DialogListener;
import nl.inholland.imready.model.blocks.Component;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.rest.ClientService;

public class ClientComponentEditActivity extends AppCompatActivity implements ClientComponentEditView, View.OnClickListener, DialogListener {

    private Component component;
    private ClientComponentEditPresenter presenter;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_component_edit);

        ApiClient apiClient = ApiManager.getClient();
        ClientService clientService = apiClient.getClientService();

        // Button
        addButton = findViewById(R.id.button_positive);
        addButton.setOnClickListener(this);

        presenter = new ClientComponentEditPresenterImpl(this, clientService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onComponentDetailViewEvent(ComponentDetailViewEvent event) {
        // get data posted between views
        component = event.getComponent();
        EventBus.getDefault().removeStickyEvent(event);
        this.updateViewData(component);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_positive:
                this.showConfirmDialog();
        }
    }

    @Override
    public void showConfirmDialog() {
        DialogFragment dialog = new ComponentAddDialogFragment();
        dialog.show(getSupportFragmentManager(), "");
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
    public void updateViewData(Component component) {
        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(component.getName());
            actionBar.setSubtitle(component.getBlock().getName());
        }

        // Description
        TextView descriptionView = findViewById(R.id.component_description);
        descriptionView.setText(component.getDescription());

        // Points
        TextView pointsView = findViewById(R.id.component_points);
        int points = presenter.getPointsFromComponent(component);
        pointsView.setText(String.valueOf(points));

        // Activities
        ListView listView = findViewById(R.id.activities);
        List<String> assignments = presenter.getActivityNamesFromComponent(component);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, assignments);
        listView.setAdapter(adapter);
    }

    @Override
    public void goToFutureplan() {
        Intent intent = new Intent();
        intent.putExtra(ParcelableConstants.COMPONENT, component.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        presenter.addComponentToFutureplan(component);
        // set ui to block
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // ignore
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        // ignore
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        // ignore
    }
}
