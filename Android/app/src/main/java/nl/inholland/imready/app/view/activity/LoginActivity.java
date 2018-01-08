package nl.inholland.imready.app.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.SceneTransitionConstants;
import nl.inholland.imready.app.view.activity.caregiver.CaregiverHomeActivity;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;
import nl.inholland.imready.app.view.fragment.LoginFailedDialogFragment;
import nl.inholland.imready.app.view.listener.DialogListener;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.AuthenticationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<ApiKeyResponse>, DialogListener {

    private AuthenticationService authService;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private ProgressBar progressBar;
    private BaseClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiClient = ApiManager.getClient();
        authService = apiClient.getAuthenticationService();

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
                login();
                break;
            default:
                return;
        }
    }

    /* Button Handlers */
    // Login button
    private void login() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getString(R.string.empty_username), Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        usernameInput.setEnabled(false);
        passwordInput.setEnabled(false);
        loginBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        authService.login(username, password, "password").enqueue(this);
    }

    private void resetUi() {
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResponse(Call<ApiKeyResponse> call, Response<ApiKeyResponse> response) {
        ApiKeyResponse keyResponse = response.body();
        if (response.isSuccessful() && keyResponse != null) {
            if (keyResponse.getUserType() == null) {
                onFailure(call, new Throwable("User type is null"));
            }

            // Save the username for next time the app is opened
            SharedPreferences settings = getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PreferenceConstants.LAST_USED_EMAIL, usernameInput.getText().toString());
            editor.putString(PreferenceConstants.USER_NAME, keyResponse.getFirstName());
            editor.apply();

            // Save the auth token for further requests
            apiClient.setToken(keyResponse.getAccessToken());

            UserCache cache;

            Intent intent = null;
            switch (keyResponse.getUserType()) {
                case CLIENT:
                    // setup to go to Client Home
                    intent = new Intent(this, ClientHomeActivity.class);
                    cache = ImReadyApplication.getInstance().getCache(UserRole.CLIENT);
                    cache.setUserId(keyResponse.getUserId());
                    break;
                case ADMIN:
                    // setup to go to Admin Home
                    break;
                case CAREGIVER:
                    // setup to go to Caregiver Home
                    intent = new Intent(this, CaregiverHomeActivity.class);
                    cache = ImReadyApplication.getInstance().getCache(UserRole.CAREGIVER);
                    cache.setUserId(keyResponse.getUserId());
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
        DialogFragment fragment = new LoginFailedDialogFragment();
        fragment.show(getSupportFragmentManager(), "login_failed");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // retry
        login();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // cancel
        resetUi();
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        // work offline
        resetUi();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        resetUi();
    }
}
