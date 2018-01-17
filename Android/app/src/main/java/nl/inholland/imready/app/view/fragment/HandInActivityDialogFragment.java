package nl.inholland.imready.app.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.DialogListener;

public class HandInActivityDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    DialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                listener.onDialogPositiveClick(this);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
            default:
                listener.onDialogNegativeClick(this);
                break;
        }
    }
}
