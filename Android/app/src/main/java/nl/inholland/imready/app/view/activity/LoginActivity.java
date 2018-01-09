package nl.inholland.imready.app.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.presenter.LoginPresenter;
import nl.inholland.imready.app.presenter.LoginPresenterImpl;
import nl.inholland.imready.app.view.SceneTransitionConstants;
import nl.inholland.imready.app.view.activity.caregiver.CaregiverHomeActivity;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;
import nl.inholland.imready.app.view.fragment.LoginFailedDialogFragment;
import nl.inholland.imready.app.view.listener.DialogListener;
import nl.inholland.imready.model.enums.UserRole;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, DialogListener, LoginView {

    private ImageView logo;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private ProgressBar progressBar;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl(this, ApiManager.getClient());

        // init views
        logo = findViewById(R.id.logo);
        logo.setTransitionName(SceneTransitionConstants.VIEW_NAME_LOGO);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);

        // Restore email from last time a user used this app
        String lastUsedEmail = presenter.getLastUsedUserName();
        if (!TextUtils.isEmpty(lastUsedEmail)) {
            usernameInput.setText(lastUsedEmail);
            passwordInput.requestFocus();
        }

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        login();
    }

    private void login() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        presenter.validateCredentials(username, password);
    }

    @Override
    public void showProgress() {
        usernameInput.setEnabled(false);
        passwordInput.setEnabled(false);
        loginBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        usernameInput.setEnabled(true);
        passwordInput.setEnabled(true);
        loginBtn.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void navigateToHome(UserRole forUserType) {
        Intent intent = null;
        switch (forUserType) {
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
                Log.e(this.getClass().getSimpleName(), "Unknown user type");
                return;
        }
        // Go to user's home screen
        startActivity(intent);
        finish();
    }

    @Override
    public String getUsername() {
        return usernameInput.getText().toString();
    }

    @Override
    public void showLoginError() {
        DialogFragment fragment = new LoginFailedDialogFragment();
        fragment.show(getSupportFragmentManager(), "login_failed");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // retry
        login();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // cancel
        hideProgress();
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        // work offline
        hideProgress();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        hideProgress();
    }
}
