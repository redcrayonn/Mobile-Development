package nl.inholland.imready.app.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.DialogPersonalActivityAdapter;
import nl.inholland.imready.model.blocks.PersonalActivity;

public class WelcomeDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.App_Dialog);

        // create view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_welcome, null);

        int todoCount = 0;
        // get data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<PersonalActivity> activities = bundle.getParcelableArrayList(ParcelableConstants.TODO_ACTIVITIES);

            todoCount = activities.size();

            // fill dialog data
            ListView tasks = dialogView.findViewById(R.id.dialog_tasks);
            DialogPersonalActivityAdapter adapter = new DialogPersonalActivityAdapter(getActivity(), activities);
            tasks.setAdapter(adapter);
            tasks.setOnItemClickListener(adapter);
        }

        // fill dialog data
        TextView titleView = dialogView.findViewById(R.id.dialog_title);
        titleView.setText(getString(R.string.welcome, "Geoffrey"));

        TextView subTextView = dialogView.findViewById(R.id.dialog_text);
        subTextView.setText(getString(R.string.welcome_subtext, String.valueOf(todoCount)));

        Button closeBtn = dialogView.findViewById(R.id.button_neutral);
        closeBtn.setOnClickListener(view -> dismiss());

        // build the dialog
        builder.setView(dialogView);

        return builder.create();
    }
}
