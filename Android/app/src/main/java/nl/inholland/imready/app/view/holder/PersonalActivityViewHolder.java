package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import nl.inholland.imready.R;
import nl.inholland.imready.app.presenter.client.ClientBlockDetailsPresenter;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.util.DateUtil;

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
    private final TextView waitingForApproval;
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
        waitingForApproval = view.findViewById(R.id.waiting_for_approval);
        this.presenter = presenter;
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data) {
        activity = data;

        long daysDiff = DateUtil.getTimeDifferenceDays(new Date(), data.getDeadline());

        titleView.setText(data.getName());
        descriptionView.setText(data.getActivity().getDescription());
        assignmentInput.setText(data.getContent());
        deadlineText.setText(context.getString(R.string.deadline_days, daysDiff));
        handInButton.setOnClickListener(this);

        //Ui changes based on status
        switch (data.getStatus()) {
            case ONGOING:
                uiActivityOngoing();
                break;
            case PENDING:
                uiActivityPending();
                break;
            case DONE:
                uiActivityComplete();
                break;
        }
    }

    private void uiActivityOngoing() {
        completedView.setChecked(false);
        descriptionView.setVisibility(View.VISIBLE);
        assignmentInput.setEnabled(false);
        assignmentInput.setEnabled(false);
        assignmentInput.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        handInButton.setEnabled(true);
        actionContainer.setVisibility(View.VISIBLE);
        deadlineContainer.setVisibility(View.VISIBLE);
        waitingForApproval.setVisibility(View.GONE);
    }

    private void uiActivityPending() {
        assignmentInput.setInputType(InputType.TYPE_NULL);
        actionContainer.setVisibility(View.GONE);
        deadlineContainer.setVisibility(View.VISIBLE);
        waitingForApproval.setVisibility(View.VISIBLE);
    }

    private void uiActivityComplete() {
        assignmentInput.setInputType(InputType.TYPE_NULL);
        actionContainer.setVisibility(View.GONE);
        deadlineContainer.setVisibility(View.GONE);
        waitingForApproval.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        String content = assignmentInput.getText().toString();
        presenter.putActivity(activity, content);
    }
}
