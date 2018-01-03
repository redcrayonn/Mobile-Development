package nl.inholland.imready.app.view.activity.caregiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Client client;
    RelativeLayout futureplanActionView;
    ClientService clientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        futureplanActionView = findViewById(R.id.futureplan);
        //futureplanActionView.findViewById(R.id.action_image);

        Intent intent = getIntent();
        String clientId = intent.getStringExtra("clientId");

        if (clientId != null){
            ApiClient client = ApiManager.getClient();
            clientService = client.getClientService();
            clientService.getClient(clientId).enqueue(this);
        }
        else{
            String errorText = "Oops, something went wrong. Try again please";
            Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResponse(Call<Client> call, Response<Client> response) {
        if (response.isSuccessful()) {
            Toast.makeText(this, "Succes!", Toast.LENGTH_SHORT).show();
            client = response.body();
            fillData(client);
        }
    }

    private void fillData(Client client) {
        TextView clientName = findViewById(R.id.clientName);
        clientName.setText(client.getFirstName());

        TextView clientEmail = findViewById(R.id.email);
        clientEmail.setText(client.getEmail());
    }

    @Override
    public void onFailure(Call<Client> call, Throwable t) {
        Toast.makeText(this, "Uh oh, er is iets fout gegaan...", Toast.LENGTH_SHORT).show();
    }
}
