package nl.inholland.imready.app.view.activity;

import android.support.v4.app.FragmentManager;

import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.enums.UserRole;

public interface LoginView extends View {
    void showProgress();
    void hideProgress();
    void navigateToHome(UserRole forUserType);

    String getUsername();
    FragmentManager getSupportFragmentManager();

    void showLoginError();
}
