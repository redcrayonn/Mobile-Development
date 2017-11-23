package inholland.tabletapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import inholland.tabletapplication.R;

import static inholland.tabletapplication.R.id.username;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

         username = (EditText) findViewById(R.id.username);
         password = (EditText) findViewById(R.id.password);
    }



    public void login(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent myIntent = new Intent(this, AdminMainActivity.class);
            startActivity(myIntent);
            //correct password
        } else {
            //wrong password
        }
    }
}
