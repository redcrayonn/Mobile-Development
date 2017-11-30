package nl.inholland.imready.app.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.SceneTransitionConstants;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameInput;
    private EditText passwordInput;
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

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        loginBtn = (Button) findViewById(R.id.login);

        setupHandlers();
    }

    private void setupHandlers() {
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
        Intent intent = new Intent(this, ClientHomeActivity.class);

        // Options

        // Go to home screen
        startActivity(intent);
    }
}
