package nl.inholland.imready.app.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.zbra.androidlinq.Stream;
import nl.inholland.imready.R;
import nl.inholland.imready.app.view.ParcelableConstants;
import nl.inholland.imready.app.view.adapter.DialogActivityAdapter;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.BlockPartStatus;
import nl.inholland.imready.model.blocks.Component;

import static br.com.zbra.androidlinq.Linq.stream;

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
            List<Block> blocks = bundle.getParcelableArrayList(ParcelableConstants.BLOCKS);
            if (blocks != null || !blocks.isEmpty()) {
                // filter data
                Stream<Component> components = stream(blocks).selectMany(Block::getComponents);
                Stream<Activity> activities = components.selectMany(Component::getActivities);
                List<Activity> todoSoon = activities.where(activity -> activity.getStatus() == BlockPartStatus.ONGOING).toList();
                todoCount = todoSoon.size();

                // fill dialog data
                ListView tasks = dialogView.findViewById(R.id.dialog_tasks);
                DialogActivityAdapter adapter = new DialogActivityAdapter(getActivity(), todoSoon);
                tasks.setAdapter(adapter);
                tasks.setOnItemClickListener(adapter);
            }
        }

        // fill dialog data
        TextView titleView = dialogView.findViewById(R.id.dialog_title);
        titleView.setText(getString(R.string.welcome, "Geoffrey"));

        TextView subTextView = dialogView.findViewById(R.id.dialog_text);
        subTextView.setText(getString(R.string.welcome_subtext, String.valueOf(todoCount)));

        // build the dialog
        builder.setView(dialogView)
                .setPositiveButton(R.string.close, (dialogInterface, i) -> {

                });

        return builder.create();
    }
}
