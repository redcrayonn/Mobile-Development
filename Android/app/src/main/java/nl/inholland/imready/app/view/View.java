package nl.inholland.imready.app.view;


import android.content.Context;

public interface View {
    void showMessage(String message);
    default Context getContext() {
        return (Context) this;
    }
}
