package nl.inholland.imready.app.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import nl.inholland.imready.R;

public class HandInActivityDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.App_Dialog);

        // create view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_hand_in_activity, null);

        // fill dialog views
        TextView textView = dialogView.findViewById(R.id.dialog_text);
        textView.setText(Html.fromHtml(getString(R.string.hand_in_text))); // trick to allow coloured text

        builder.setPositiveButton(android.R.string.yes, this);
        builder.setNegativeButton(android.R.string.no, this);

        // build the dialog
        builder.setView(dialogView);
        return builder.create();
    }

    private void onPositiveClick() {
        // network call
        Toast.makeText(getContext(), "Yay", Toast.LENGTH_SHORT).show();
    }

    private void onNegativeClick() {
        dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                onPositiveClick();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
            default:
                onNegativeClick();
                break;
        }
    }
}
