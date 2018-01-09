package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;

/**
 * Created by Peter on 09/01/2018.
 */

public class CaregiverPlanComponentViewHolder implements FillableViewHolder<PersonalComponent> {
    private final TextView componentText;
    private final TextView notificationCountText;

    public CaregiverPlanComponentViewHolder(View view){
        this.componentText = view.findViewById(R.id.component_name);
        this.notificationCountText = view.findViewById(R.id.notificationCount);

    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalComponent data, @Nullable OnChangeListener<PersonalComponent> changeListener) {
        componentText.setText(data.getName());

        List<PersonalActivity> activities = data.getActivities();
        Integer notifications =0;

        for (PersonalActivity activity : activities){
            if (activity.getStatus() == BlockPartStatus.PENDING){
                notifications++;}
        }

        if (notifications > 0){
            notificationCountText.setVisibility(View.VISIBLE);
            notificationCountText.setText(notifications.toString());
        }
        else{
            notificationCountText.setVisibility(View.INVISIBLE);
        }
    }
}
