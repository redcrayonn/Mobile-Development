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
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;

/**
 * Created by Peter on 09/01/2018.
 */

public class CaregiverPlanHeaderViewHolder implements FillableViewHolder<PersonalBlock> {
    private final TextView blockText;
    private final TextView notificationCountText;

    public CaregiverPlanHeaderViewHolder(View view){
        this.blockText = view.findViewById(R.id.block_name);
        this.notificationCountText = view.findViewById(R.id.notificationCount);

    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalBlock data, @Nullable OnChangeListener<PersonalBlock> changeListener) {
        blockText.setText(data.getName());

        if (data.getComponents() != null && data.getComponents().size() != 0) {
            List<PersonalComponent> components = data.getComponents();
            Integer notifications = 0;

            //Search for notifications using pending status on the components and its activities
            for (PersonalComponent component : components) {
                if (component.getStatus() == BlockPartStatus.PENDING){notifications++;}
                List<PersonalActivity> activities = component.getActivities();

                for (PersonalActivity activity : activities){
                    if (activity.getStatus() == BlockPartStatus.PENDING){notifications++;}
                }
            }

            if (notifications > 0){
                notificationCountText.setVisibility(View.VISIBLE);
                notificationCountText.setText(notifications.toString());
            }
            else{
                notificationCountText.setVisibility(View.INVISIBLE);
            }
        }
        else {
            notificationCountText.setVisibility(View.INVISIBLE);
        }
    }
}
