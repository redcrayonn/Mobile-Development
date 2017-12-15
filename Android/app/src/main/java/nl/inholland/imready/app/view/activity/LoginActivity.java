package nl.inholland.imready.app.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.view.SceneTransitionConstants;
import nl.inholland.imready.app.view.activity.caregiver.CaregiverHomeActivity;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.AuthenticationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<ApiKeyResponse> {

    private AuthenticationService authService;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ApiClient client = ApiManager.getClient();
        authService = client.getAuthenticationService();

        initViews();
    }

    private void initViews() {
        ImageView logo = findViewById(R.id.logo);
        logo.setTransitionName(SceneTransitionConstants.VIEW_NAME_LOGO);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        loginBtn = findViewById(R.id.login);

        progressBar = findViewById(R.id.progressbar);

        // Restore email from last time a user used this app
        SharedPreferences settings = getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
        String lastUsedEmail = settings.getString(PreferenceConstants.LAST_USED_EMAIL, null);
        if (!TextUtils.isEmpty(lastUsedEmail)) {
            usernameInput.setText(lastUsedEmail);
            passwordInput.requestFocus();
        }

        setupHandlers();
    }

    private void setupHandlers() {
        loginBtn.setOnClickListener(this);
    }

    /* Main button handler (service locator) */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        authService.login(username, password, "password").enqueue(this);

        usernameInput.setEnabled(false);
        passwordInput.setEnabled(false);
        loginBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(Call<ApiKeyResponse> call, Response<ApiKeyResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            ApiKeyResponse keyResponse = response.body();
            if (keyResponse.getUserType() == null) {
                onFailure(call, new Throwable("User type is null"));
            }

            // Save the username for next time the app is opened
            SharedPreferences settings = getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PreferenceConstants.LAST_USED_EMAIL, usernameInput.getText().toString());
            editor.putString(PreferenceConstants.USER_NAME, keyResponse.getFirstName());
            editor.apply();

            Intent intent = null;
            switch (keyResponse.getUserType()) {
                case CLIENT:
                    // setup to go to Client Home
                    intent = new Intent(this, ClientHomeActivity.class);
                    break;
                case ADMIN:
                    // setup to go to Admin Home
                    break;
                case CAREGIVER:
                    // setup to go to Caregiver Home
                    intent = new Intent(this, CaregiverHomeActivity.class);
                    break;
                case FAMILY:
                    // setup to go to Family Home
                    break;
                default:
                    onFailure(call, new Throwable("Unknown user type"));
                    return;
            }
            // Go to user's home screen
            startActivity(intent);
            finish();
        } else {
            onFailure(call, new Throwable(getString(R.string.unknown_error)));
        }
    }

    @Override
    public void onFailure(Call<ApiKeyResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        resetUi();
    }

    private void resetUi() {
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
