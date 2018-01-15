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


public class ComponentAddDialogFragment extends DialogFragment {
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
        DialogFragment fragment = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.App_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_component_add, null);

        TextView textView = dialogView.findViewById(R.id.dialog_text);
        textView.setText(Html.fromHtml(getString(R.string.component_add_text))); // trick to allow coloured text

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                listener.onDialogPositiveClick(fragment);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogNegativeClick(fragment);
            }
        });

        builder.setView(dialogView);
        return builder.create();
    }
}
