package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;

/**
 * Created by Peter on 09/01/2018.
 */

public class CaregiverPlanHeaderViewHolder implements FillableViewHolder<PersonalComponent> {
    private final TextView componentText;
    private final TextView notificationCountText;
    private final ImageView blockIcon;
    private Boolean isExpanded;

    public CaregiverPlanHeaderViewHolder(View view, Boolean isExpanded){
        this.componentText = view.findViewById(R.id.block_name);
        this.notificationCountText = view.findViewById(R.id.notificationCount);
        this.blockIcon = view.findViewById(R.id.block_icon);
        this.isExpanded = isExpanded;
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalComponent data) {
        componentText.setText(data.getName());
        blockIcon.setVisibility(View.INVISIBLE);

        if (data.getActivities() != null && data.getActivities().size() != 0) {
            List<PersonalActivity> activities = data.getActivities();
            Integer notifications = 0;

            //Search for notifications using pending status on the components and its activities

                for (PersonalActivity activity : activities){
                    if (activity.getStatus() == BlockPartStatus.PENDING){notifications++;}
                }

                //IF there is notifications show the notification counter
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

        //blockIcon.setImageDrawable(BlockUtil.getDrawableIcon(context, data.getComponent().getBlock().getType()));
    }
}
