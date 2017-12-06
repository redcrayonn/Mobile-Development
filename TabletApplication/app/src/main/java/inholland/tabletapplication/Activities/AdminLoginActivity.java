package inholland.tabletapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import inholland.tabletapplication.Models.LoginUser;
import inholland.tabletapplication.R;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private LoginUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        loginUser = new LoginUser();

         username = (EditText) findViewById(R.id.username);
         password = (EditText) findViewById(R.id.password);
    }



    public void login(View view) {
        loginUser.Username = username.getText().toString();
        loginUser.Password = password.getText().toString();

        if (loginUser.CheckCredentials()){
            Intent myIntent = new Intent(this, AdminMainActivity.class);
            startActivity(myIntent);
        } else {
            //throw error
        }
    }
}
