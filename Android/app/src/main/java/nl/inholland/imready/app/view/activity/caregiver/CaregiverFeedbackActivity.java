package nl.inholland.imready.app.view.activity.caregiver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.logic.events.FeedbackViewEvent;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.model.blocks.Feedback;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.PutFeedbackModel;
import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaregiverFeedbackActivity extends AppCompatActivity implements Callback<Void> {
    private PersonalActivity activity;
    private String clientId;
    private String clientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_feedback);

        //Get the PersonalActivity from the eventbus and delete it from the bus
        FeedbackViewEvent stickyEvent = EventBus.getDefault().getStickyEvent(FeedbackViewEvent.class);
        if(stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            activity = stickyEvent.activity;
            clientId = stickyEvent.clientId;
            clientName = stickyEvent.clientName;

            setupLayout();
        }
    }

    private void sendFeedback(boolean isApproved) {
        EditText feedback = findViewById(R.id.feedback_input);

        ApiClient client = ApiManager.getClient();
        CaregiverService caregiverService = client.getCaregiverService();

        caregiverService.giveFeedback(getUser(), clientId, activity.getId(), new PutFeedbackModel(isApproved, feedback.getText().toString())).enqueue(this);
        updateData(isApproved);
        setupLayout();
    }

    private void updateData(Boolean isApproved) {
        activity.setFeedback(getFeedbackModel());

        if (isApproved)
            activity.setStatus(BlockPartStatus.DONE);
        else
            activity.setStatus(BlockPartStatus.ONGOING);
    }

    private List<Feedback> getFeedbackModel() {
        EditText feedback = findViewById(R.id.feedback_input);
        List<Feedback> list = new ArrayList<>();
        list.add(new Feedback(feedback.getText().toString()));
        return list;
    }

    private String getUser(){
        UserCache cache = ImReadyApplication.getInstance().getCache(UserRole.CAREGIVER);
        return cache.getUserId();
    }

    private void setupLayout() {
        //Fill textfields
        TextView nameText = findViewById(R.id.client_name);
        nameText.setText(clientName);

        TextView taskName = findViewById(R.id.task);
        taskName.setText(activity.getName());

        TextView taskStatus = findViewById(R.id.status);
        switch (activity.getStatus()){
            case DONE:
                taskStatus.setText(R.string.status_done);
                break;
            case ONGOING:
                taskStatus.setText(R.string.status_progress);
                break;
            default:
                taskStatus.setText(R.string.status_pending);
                break;
        }

        //Following to differentiate pending task from others
        TextView taskClientInput = findViewById(R.id.client_input);
        TextView taskClientInputHeader = findViewById(R.id.client_input_header);
        RelativeLayout feedbackPart = findViewById(R.id.feedback_layout);

        if (activity.getStatus() == BlockPartStatus.PENDING){
            taskClientInput.setText(activity.getContent());
            taskClientInput.setVisibility(View.VISIBLE);
            taskClientInputHeader.setVisibility(View.VISIBLE);

            feedbackPart.setVisibility(View.VISIBLE);

            //Setup the actions for the approve/deny buttons
            final Button approveButton = findViewById(R.id.button_approve);
            final Button denyButton = findViewById(R.id.button_deny);

            approveButton.setOnClickListener(v -> sendFeedback(true));
            denyButton.setOnClickListener(v -> sendFeedback(false));


        }
        else if (activity.getStatus() == BlockPartStatus.DONE){
            taskClientInput.setText(activity.getContent());
            taskClientInput.setVisibility(View.VISIBLE);
            taskClientInputHeader.setVisibility(View.VISIBLE);

            feedbackPart.setVisibility(View.INVISIBLE);
        }
        else{
            taskClientInput.setVisibility(View.INVISIBLE);
            taskClientInputHeader.setVisibility(View.INVISIBLE);
            feedbackPart.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // fix for a navigation bug in android; https://stackoverflow.com/a/29464116
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FeedbackViewEvent event) {
        //Necessary to work, no need for code in here though
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        setResult(RESULT_OK);
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Toast.makeText(this, "Er is iets mis gegaan, probeer het opnieuw.", Toast.LENGTH_LONG).show();
    }
}
