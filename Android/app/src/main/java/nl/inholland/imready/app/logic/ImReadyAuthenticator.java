package nl.inholland.imready.app.logic;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.IOException;

import nl.inholland.imready.app.view.AuthenticatorConstants;
import nl.inholland.imready.app.view.activity.LoginActivity;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.ServerAuthenticationService;
import retrofit2.Response;

public class ImReadyAuthenticator extends AbstractAccountAuthenticator {

    private Context context;

    public ImReadyAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AuthenticatorConstants.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorConstants.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorConstants.ARG_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(context);

        String authToken = am.peekAuthToken(account, authTokenType);

        // Lets give another try to authenticate the user if we
        // couldn't get the autToken from the AccountManager
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                ApiClient client = ApiManager.getClient(true);
                ServerAuthenticationService authService = client.getAuthenticationService();
                try {
                    Response<ApiKeyResponse> keyResponse = authService.login(account.name, password).execute();
                    if (keyResponse.isSuccessful() && keyResponse.body() != null) {
                        ApiKeyResponse key = keyResponse.body();
                        authToken = key.getAuthtoken();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our LoginActivity.
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AuthenticatorConstants.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorConstants.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String s, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
