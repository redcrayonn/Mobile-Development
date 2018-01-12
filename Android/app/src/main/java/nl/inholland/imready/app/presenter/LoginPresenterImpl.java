package nl.inholland.imready.app.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.activity.LoginView;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.AuthenticationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenterImpl implements LoginPresenter, Callback<ApiKeyResponse> {

    @NonNull
    private final LoginView loginView;
    @NonNull
    private final AuthenticationService service;
    private final Context context;
    @NonNull
    private final BaseClient apiClient;

    public LoginPresenterImpl(@NonNull LoginView loginView, @NonNull BaseClient apiClient) {
        this.loginView = loginView;
        this.service = apiClient.getAuthenticationService();
        this.context = loginView.getContext();
        this.apiClient = apiClient;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            String usernameError = context.getString(R.string.empty_username);
            loginView.showMessage(usernameError);
            return;
        } else if (TextUtils.isEmpty(password)) {
            String passwordError = context.getString(R.string.empty_username);
            loginView.showMessage(passwordError);
            return;
        }

        loginView.showProgress();

        service.login(username, password, "password").enqueue(this);
    }

    @Override
    public String getLastUsedUserName() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
        return settings.getString(PreferenceConstants.LAST_USED_EMAIL, null);
    }

    @Override
    public void onResponse(Call<ApiKeyResponse> call, Response<ApiKeyResponse> response) {
        ApiKeyResponse keyResponse = response.body();
        if (response.isSuccessful() && keyResponse != null) {
            if (keyResponse.getUserType() == null) {
                onFailure(call, new Throwable("User type is null"));
            }

            // Save the username for next time the app is opened
            SharedPreferences settings = context.getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PreferenceConstants.LAST_USED_EMAIL, loginView.getUsername());
            editor.putString(PreferenceConstants.USER_NAME, keyResponse.getFirstName());
            editor.apply();

            // Save the auth token for further requests
            apiClient.setToken(keyResponse.getAccessToken());

            ImReadyApplication application = ImReadyApplication.getInstance();
            UserCache cache = application.getCache(keyResponse.getUserType());
            cache.setUserId(keyResponse.getUserId());
            application.setCurrentUserRole(keyResponse.getUserType());

            loginView.navigateToHome(keyResponse.getUserType());
        } else {
            onFailure(call, new Throwable(context.getString(R.string.unknown_error)));
        }
    }

    @Override
    public void onFailure(Call<ApiKeyResponse> call, Throwable t) {
        loginView.showLoginError();
    }
}

