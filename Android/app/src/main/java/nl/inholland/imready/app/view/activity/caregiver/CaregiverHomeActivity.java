package nl.inholland.imready.app.view.activity.caregiver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nl.inholland.imready.R;

public class CaregiverHomeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> clients;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);
        initListView();
    }

    private void initListView() {
        listView = findViewById(R.id.clientList);
        clients = new ArrayList<>();
        clients.add("Bob");
        clients.add("Alice");
        clients.add("Petra");
        clients.add("Kevin");
        clients.add("Hugo");
        clients.add("Paul");
        clients.add("Derp");

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clients);
        listView.setAdapter(listAdapter);
    }
}
