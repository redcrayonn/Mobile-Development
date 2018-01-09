package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.presenter.client.ClientBlockDetailsPresenter;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;

public class PersonalActivityViewHolder implements FillableViewHolder<PersonalActivity>, View.OnClickListener {

    private final TextView titleView;
    private final TextView descriptionView;
    private final EditText assignmentInput;
    private final TextView deadlineText;
    private final CheckBox completedView;
    private final Button handInButton;
    private final View actionContainer;
    private final ClientBlockDetailsPresenter presenter;
    private final View deadlineContainer;
    private PersonalActivity activity;

    public PersonalActivityViewHolder(View view, ClientBlockDetailsPresenter presenter) {
        completedView = view.findViewById(R.id.activity_checkbox);
        titleView = view.findViewById(R.id.activity_title);
        descriptionView = view.findViewById(R.id.activity_description);
        assignmentInput = view.findViewById(R.id.activity_input);
        deadlineText = view.findViewById(R.id.activity_deadline);

        handInButton = view.findViewById(R.id.activity_button);

        deadlineContainer = view.findViewById(R.id.activity_deadline_container);
        actionContainer = view.findViewById(R.id.activity_action_container);
        this.presenter = presenter;
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data, @Nullable OnChangeListener<PersonalActivity> changeListener) {
        activity = data;

        boolean isComplete = data.getStatus() == BlockPartStatus.DONE;
        boolean isPending = data.getStatus() == BlockPartStatus.PENDING;
        int visibility = isComplete ? View.GONE : View.VISIBLE;

        completedView.setChecked(isComplete);

        titleView.setText(data.getName());

        descriptionView.setText(data.getActivity().getDescription());
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
        handInButton.setOnClickListener(this);

        actionContainer.setVisibility(visibility);
        deadlineContainer.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        String content = assignmentInput.getText().toString();
        presenter.putActivity(activity, content);
    }
}
