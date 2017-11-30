package nl.inholland.imready.app.view.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.view.AuthenticatorConstants;
import nl.inholland.imready.app.view.SceneTransitionConstants;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.ServerAuthenticationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AccountAuthenticatorActivity implements View.OnClickListener {

    private ServerAuthenticationService serverAuthenticationService;
    private AccountManager accountManager;

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        ApiClient apiClient = ApiManager.getClient(true);
        serverAuthenticationService = apiClient.getAuthenticationService();
        accountManager = AccountManager.get(this);
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
        //Intent intent = new Intent(this, ClientHomeActivity.class);

        // Options


        // Go to home screen
        //startActivity(intent);

        final String username = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        new AsyncTask<Void, Void, Intent>() {

            @Override
            protected Intent doInBackground(Void... params) {
                try {
                    Response<ApiKeyResponse> apiKeyResponse = serverAuthenticationService.login(username, password).execute();
                    if (apiKeyResponse.isSuccessful() && apiKeyResponse.body() != null) {
                        ApiKeyResponse keyResponse = apiKeyResponse.body();
                        String authToken = keyResponse.getAuthtoken();
                        final Intent intent = new Intent();
                        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, AuthenticatorConstants.ARG_ACCOUNT_TYPE);
                        intent.putExtra(AccountManager.KEY_AUTHTOKEN, authToken);
                        intent.putExtra(AuthenticatorConstants.PARAM_USER_PASS, password);
                        return intent;
                    }

                } catch (IOException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }

    private void finishLogin(Intent intent) {
        String username = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String password = intent.getStringExtra(AuthenticatorConstants.PARAM_USER_PASS);
        final Account account = new Account(username, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if (getIntent().getBooleanExtra(AuthenticatorConstants.ARG_ADDING_NEW_ACCOUNT, false)) {
            String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authTokenType = AuthenticatorConstants.ARG_AUTH_TYPE;
            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            accountManager.addAccountExplicitly(account, password, null);
            accountManager.setAuthToken(account, authTokenType, authToken);
        } else {
            accountManager.setPassword(account, password);
        }
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}
