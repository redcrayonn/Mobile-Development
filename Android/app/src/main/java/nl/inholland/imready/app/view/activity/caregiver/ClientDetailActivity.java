package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.Client;
import nl.inholland.imready.service.rest.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailActivity extends AppCompatActivity implements Callback<Client> {
    String clientId;
    ClientService clientService;
    ProgressBar progressBar;
    private String clientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        progressBar = findViewById(R.id.progressbar);

        setupScreen();
        setButtonListeners();
    }

    private void setButtonListeners() {
        View futureplanActionView = findViewById(R.id.futureplan);
        futureplanActionView.setOnClickListener(v -> gotoFutureplan());

        View notesActionView = findViewById(R.id.notes_button);
        notesActionView.setOnClickListener(v -> gotoNotes());
    }

    private void setupScreen() {
        Intent intent = getIntent();
        clientId = intent.getStringExtra("clientId");
        int notificationCount = intent.getIntExtra("notifications", 0);


        TextView notifications = findViewById(R.id.clientNotifications);

        if (notificationCount != 0){
            notifications.setText(Integer.toString(notificationCount));
        }
        else
            notifications.setVisibility(View.INVISIBLE);

        if (clientId != null){
            getClientDetails(clientId);
        }
        else{
            Toast.makeText(this, "Oops, something went wrong. Try again please", Toast.LENGTH_LONG).show();
        }
    }

    private void getClientDetails(String clientId) {
        //Activate spinner
        setProgressbarVisible(true);

        //Api call
        ApiClient client = ApiManager.getClient();
        clientService = client.getClientService();
        clientService.getClient(clientId).enqueue(this);
    }

    @Override
    public void onResponse(Call<Client> call, Response<Client> response) {
        if (response.isSuccessful()) {
            Client client = response.body();
            fillData(client);

            clientName = client.getFirstName();
            setProgressbarVisible(false);
        }
    }

    private void fillData(Client client) {
        TextView clientName = findViewById(R.id.clientName);
        clientName.setText(client.getFirstName());

        TextView clientEmail = findViewById(R.id.email);
        clientEmail.setText(client.getEmail());
    }

    private void setProgressbarVisible(boolean state){
        RelativeLayout buttonFutureplan = findViewById(R.id.futureplan);

        if (state){
            buttonFutureplan.setEnabled(true);
            progressBar.setVisibility(View.VISIBLE);
        }

        else{
            progressBar.setVisibility(View.INVISIBLE);
            buttonFutureplan.setEnabled(true);
        }
    }

    @Override
    public void onFailure(Call<Client> call, Throwable t) {
        Toast.makeText(this, "Uh oh, er is iets fout gegaan...", Toast.LENGTH_SHORT).show();
        setProgressbarVisible(false);
    }

    private void gotoFutureplan() {
        Intent intent = new Intent(this, CaregiverFutureplanOverviewActivity.class);
        intent.putExtra("clientId", clientId);
        intent.putExtra("clientName", clientName);
        startActivity(intent);
    }

    private void gotoNotes(){
        Intent intent = new Intent(this, NoteListActivity.class);
        intent.putExtra("clientName", clientName);
        startActivity(intent);
    }
}