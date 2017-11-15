package nl.inholland.imready.app.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.SceneTransitionConstants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setTransitionName(SceneTransitionConstants.VIEW_NAME_LOGO);
        loginBtn = (Button) findViewById(R.id.login);

        setupButtonHandlers();
    }

    private void setupButtonHandlers() {
        loginBtn.setOnClickListener(this);
    }

    /* Main button handler (service locator) */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                onLoginBtnClicked();
                break;
            default:
                return;
        }
    }

    /* Button Handlers */
    // Login button
    private void onLoginBtnClicked() {
        // Setup intent to the home screen
        Intent intent = new Intent(this, HomeActivity.class);

        // Options

        // Go to home screen
        startActivity(intent);
    }
}
