package nl.inholland.imready.app.view;


import android.content.Context;
import android.widget.Toast;

public interface View {
    default void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    };
    default Context getContext() {
        return (Context) this;
    }
}
