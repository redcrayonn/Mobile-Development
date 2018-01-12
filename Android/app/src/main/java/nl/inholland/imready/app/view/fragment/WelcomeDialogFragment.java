package nl.inholland.imready.app.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.activity.client.ClientHomeView;
import nl.inholland.imready.app.view.adapter.DialogPersonalActivityAdapter;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;

public class WelcomeDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "welcome";

    private BaseAdapter adapter;
    private ClientHomeView homeView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (activity instanceof ClientHomeView) {
            homeView = (ClientHomeView) activity;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.App_Dialog);

        // create view
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_welcome, null);

        int todoCount = 0;
        // get data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<PersonalActivity> activities = bundle.getParcelableArrayList(ParcelableConstants.TODO_ACTIVITIES);

            if (activities != null) {
                todoCount = activities.size();

                // fill dialog data
                ListView tasks = dialogView.findViewById(R.id.dialog_tasks);
                adapter = new DialogPersonalActivityAdapter(activity, activities);
                tasks.setAdapter(adapter);
                tasks.setOnItemClickListener(this);
            }
        }

        // fill dialog views
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        PersonalActivity activity = (PersonalActivity) adapter.getItem(position);
        PersonalComponent component = activity.getComponent();
        PersonalBlock block = component.getBlock();
        homeView.goToBlockInfo(block);
        dismiss();
    }
}
