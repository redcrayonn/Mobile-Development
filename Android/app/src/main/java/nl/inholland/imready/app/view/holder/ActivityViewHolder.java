package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.BlockPartStatus;

public class ActivityViewHolder implements FillableViewHolder<Activity> {

    private Activity activity;

    private final TextView titleView;
    private final TextView descriptionView;
    private final EditText assignmentInput;
    private final TextView deadlineText;
    private final CheckBox completedView;

    private final Button handInButton;
    private final View actionContainer;
    private final View deadlineContainer;

    public ActivityViewHolder(View view) {
        completedView = view.findViewById(R.id.activity_checkbox);
        titleView = view.findViewById(R.id.activity_title);
        descriptionView = view.findViewById(R.id.activity_description);
        assignmentInput = view.findViewById(R.id.activity_input);
        deadlineText = view.findViewById(R.id.activity_deadline);

        handInButton = view.findViewById(R.id.activity_button);

        deadlineContainer = view.findViewById(R.id.activity_deadline_container);
        actionContainer = view.findViewById(R.id.activity_action_container);
    }

    @Override
    public void fill(Context context, Activity data) {
        activity = data;

        boolean isComplete = data.getStatus() == BlockPartStatus.COMPLETE;
        boolean isPending = data.getStatus() == BlockPartStatus.PENDING;
        int visibility = isComplete ? View.GONE : View.VISIBLE;

        completedView.setChecked(isComplete);

        titleView.setText(data.getName());

        descriptionView.setText(data.getDescription());
        descriptionView.setEnabled(!isComplete);
        descriptionView.setVisibility(visibility);

        assignmentInput.setText(data.getContent());
        assignmentInput.setEnabled(!isComplete || !isPending);
        assignmentInput.setFocusable(!isComplete || !isPending);
        int inputType = isComplete || isPending ? InputType.TYPE_NULL : InputType.TYPE_TEXT_FLAG_MULTI_LINE;
        assignmentInput.setInputType(inputType);

        deadlineText.setText("over 2 dagen");

        handInButton.setEnabled(!isComplete || !isPending);
        handInButton.setVisibility(isComplete || isPending ? View.GONE : View.VISIBLE);
        handInButton.setOnClickListener(view -> {
            activity.setContent(assignmentInput.getText().toString());
            activity.setStatus(BlockPartStatus.COMPLETE);
        });

        actionContainer.setVisibility(visibility);
        deadlineContainer.setVisibility(visibility);
    }
}
