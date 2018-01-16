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

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.events.FeedbackViewEvent;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;

public class CaregiverFeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_feedback);

        //Get the PersonalActivity from the eventbus and delete it from the bus
        FeedbackViewEvent stickyEvent = EventBus.getDefault().getStickyEvent(FeedbackViewEvent.class);
        if(stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            PersonalActivity activity = stickyEvent.activity;
            setupLayout(activity, stickyEvent.clientName);
        }
    }

    private void openFeedback(boolean isApproved) {
        String text = "Uitwerking goedkeuring:" + isApproved;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void setupLayout(PersonalActivity activity, String clientName) {
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
        if (activity.getStatus() == BlockPartStatus.PENDING){
            TextView taskClientInput = findViewById(R.id.client_input);
            TextView taskClientInputHeader = findViewById(R.id.client_input_header);

            taskClientInput.setText(activity.getContent());
            taskClientInput.setVisibility(View.VISIBLE);
            taskClientInputHeader.setVisibility(View.VISIBLE);

            RelativeLayout feedbackPart = findViewById(R.id.feedback_layout);
            feedbackPart.setVisibility(View.VISIBLE);
            //Setup the actions for the approve/deny buttons
            final Button approveButton = findViewById(R.id.button_approve);
            final Button denyButton = findViewById(R.id.button_deny);

            approveButton.setOnClickListener(v -> openFeedback(true));
            denyButton.setOnClickListener(v -> openFeedback(false));
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

    }
}
