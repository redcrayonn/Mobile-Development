package nl.inholland.imready.app.view.listener;


import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

public interface DialogListener {
    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);
    void onDialogNeutralClick(DialogFragment dialog);
    void onDismiss(DialogInterface dialog);
}
