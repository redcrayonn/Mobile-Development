package nl.inholland.imready.app.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.logic.events.FutureplanChangedEvent;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Component;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.rest.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.zbra.androidlinq.Linq.stream;

public class ClientComponentEditActivity extends AppCompatActivity implements Callback<Void> {

    private Component component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_component_edit);

        ApiClient apiClient = ApiManager.getClient();
        ClientService clientService = apiClient.getClientService();

        // Get data passed from previous view
        Intent intent = getIntent();
        component = intent.getParcelableExtra(ParcelableConstants.COMPONENT);

        // Set action bar title
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(component.getName());
        }

        // Description
        TextView descriptionView = findViewById(R.id.component_description);
        descriptionView.setText(component.getDescription());

        // Points
        TextView pointsView = findViewById(R.id.component_points);
        int points = stream(component.getActivities())
                .sum(Activity::getPoints);
        pointsView.setText(String.valueOf(points));

        // Activities
        ListView listView = findViewById(R.id.activities);
        List<String> assignments = stream(component.getActivities())
                .select(Activity::getName)
                .toList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, assignments);
        listView.setAdapter(adapter);

        String clientId = "222c352b-fafa-46c5-b375-39dcdc99dec8";

        // Button
        Button button = findViewById(R.id.button_positive);
        button.setOnClickListener(view -> {
            clientService.enrollComponent(clientId, component.getId()).enqueue(this);
        });
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
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Toast.makeText(this, getString(R.string.personal_component_succes), Toast.LENGTH_SHORT).show();
            //notify application of changed data
            EventBus.getDefault().post(new FutureplanChangedEvent());
            finish();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(this, R.string.enroll_component_failed, Toast.LENGTH_SHORT).show();
    }
}
