package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.presenter.client.ClientBlockDetailsPresenter;
import nl.inholland.imready.model.blocks.Feedback;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.user.User;
import nl.inholland.imready.util.DateUtil;

import static br.com.zbra.androidlinq.Linq.stream;

public class PersonalActivityViewHolder implements FillableViewHolder<PersonalActivity>, View.OnClickListener {

    private final TextView titleView;
    private final TextView descriptionView;
    private final EditText assignmentInput;
    private final TextView deadlineText;
    private final CheckBox completedView;
    private final Button handInButton;
    private final View actionContainer;
    private final ClientBlockDetailsPresenter presenter;
    private final View activityDeadlineContainer;
    private final TextView waitingForApproval;
    private final View activityButtonContainer;
    private final TextView feedbackLabel;
    private final TextView feedbackText;
    private final View feedbackContainer;
    private PersonalActivity activity;

    public PersonalActivityViewHolder(View view, ClientBlockDetailsPresenter presenter) {
        completedView = view.findViewById(R.id.activity_checkbox);
        titleView = view.findViewById(R.id.activity_title);
        descriptionView = view.findViewById(R.id.activity_description);
        assignmentInput = view.findViewById(R.id.activity_input);
        deadlineText = view.findViewById(R.id.activity_deadline);
        feedbackContainer = view.findViewById(R.id.activity_feedback_container);
        feedbackLabel = view.findViewById(R.id.activity_feedback_label);
        feedbackText = view.findViewById(R.id.activity_feedback);

        handInButton = view.findViewById(R.id.activity_button);

        activityDeadlineContainer = view.findViewById(R.id.activity_deadline_container);
        actionContainer = view.findViewById(R.id.activity_action_container);
        activityButtonContainer = view.findViewById(R.id.activity_button_container);
        waitingForApproval = view.findViewById(R.id.waiting_for_approval);
        this.presenter = presenter;
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data) {
        activity = data;

        long daysDiff = DateUtil.getTimeDifferenceDays(new Date(), data.getDeadline());

        titleView.setText(data.getName());
        String description = data.getActivity().getDescription();
        description = TextUtils.isEmpty(description) ? context.getString(R.string.empty_description) : description;
        descriptionView.setText(description);
        assignmentInput.setText(data.getContent());
        deadlineText.setText(context.getString(R.string.deadline_days, daysDiff));
        handInButton.setOnClickListener(this);

        //Ui changes based on status
        switch (data.getStatus()) {
            case ONGOING:
                uiActivityOngoing(context);
                showFeedbackIfPresent(context, data.getFeedback());
                break;
            case PENDING:
                uiActivityPending(context);
                break;
            case DONE:
                uiActivityComplete(context);
                break;
        }

    }

    private void showFeedbackIfPresent(Context context, List<Feedback> feedback) {
        if (feedback != null && feedback.size() > 0) {
            Feedback latestFeedback = stream(feedback)
                    .orderByDescending(value -> value.getSent().getTime())
                    .first();

            User caregiver = latestFeedback.getCaregiver();
            String sender = caregiver != null ? caregiver.getFirstName() : context.getString(R.string.your_caregiver);
            feedbackLabel.setText(context.getString(R.string.activity_feedback_by, sender));
            feedbackContainer.setVisibility(View.VISIBLE);
            feedbackText.setText(latestFeedback.getContent());
        } else {
            feedbackContainer.setVisibility(View.GONE);
        }
    }

    private void uiActivityOngoing(Context context) {
        completedView.setChecked(false);
        descriptionView.setVisibility(View.VISIBLE);
        feedbackContainer.setVisibility(View.VISIBLE);
        assignmentInput.setEnabled(true);
        assignmentInput.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        handInButton.setEnabled(true);
        actionContainer.setVisibility(View.VISIBLE);
        activityButtonContainer.setVisibility(View.VISIBLE);
        activityDeadlineContainer.setVisibility(View.VISIBLE);
        deadlineText.setVisibility(View.VISIBLE);
        waitingForApproval.setVisibility(View.GONE);
    }

    private void uiActivityPending(Context context) {
        completedView.setChecked(false);
        descriptionView.setVisibility(View.VISIBLE);
        feedbackContainer.setVisibility(View.GONE);
        assignmentInput.setEnabled(false);
        assignmentInput.setInputType(InputType.TYPE_NULL);
        handInButton.setEnabled(false);
        actionContainer.setVisibility(View.VISIBLE);
        activityButtonContainer.setVisibility(View.GONE);
        activityDeadlineContainer.setVisibility(View.VISIBLE);
        deadlineText.setVisibility(View.GONE);
        waitingForApproval.setVisibility(View.VISIBLE);
    }

    private void uiActivityComplete(Context context) {
        completedView.setChecked(true);
        descriptionView.setVisibility(View.GONE);
        feedbackContainer.setVisibility(View.GONE);
        assignmentInput.setEnabled(false);
        assignmentInput.setInputType(InputType.TYPE_NULL);
        handInButton.setEnabled(false);
        actionContainer.setVisibility(View.GONE);
        activityButtonContainer.setVisibility(View.GONE);
        activityDeadlineContainer.setVisibility(View.GONE);
        deadlineText.setVisibility(View.GONE);
        waitingForApproval.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        String content = assignmentInput.getText().toString();
        presenter.putActivity(activity, content);
    }
}
