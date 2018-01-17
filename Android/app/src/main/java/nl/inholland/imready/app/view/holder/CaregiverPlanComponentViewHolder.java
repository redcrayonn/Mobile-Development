package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;

/**
 * Created by Peter on 09/01/2018.
 */

public class CaregiverPlanComponentViewHolder implements FillableViewHolder<PersonalActivity> {
    private final TextView activityText;
    private final TextView notificationCountText;
    private final ImageView checkIcon;

    public CaregiverPlanComponentViewHolder(View view){
        this.activityText = view.findViewById(R.id.component_name);
        this.notificationCountText = view.findViewById(R.id.notificationCount);
        this.checkIcon = view.findViewById(R.id.check_icon);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalActivity data) {
        activityText.setText(data.getName());

        Integer notifications = 0;

            if (data.getStatus() == BlockPartStatus.PENDING){
                notifications++;}
        if (data.getStatus()==BlockPartStatus.DONE){
            checkIcon.setVisibility(View.VISIBLE);
        }
        else
            checkIcon.setVisibility(View.INVISIBLE);
        if (notifications > 0){
            notificationCountText.setVisibility(View.VISIBLE);
            notificationCountText.setText(notifications.toString());
        }
        else{

            notificationCountText.setVisibility(View.INVISIBLE);
        }
    }
}
