package nl.inholland.imready.app.view;


import android.content.Context;

public interface View {
    void showMessage(String message);
    Context getContext();
}
