package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.service.model.ClientsResponse;

/**
 * Created by Peter on 17/12/2017.
 */

public class CaregiverHomeViewHolder implements FillableViewHolder<ClientsResponse> {
    private final TextView nameView;
    private final TextView notificationCountView;

    public CaregiverHomeViewHolder(View view){
        this.nameView = view.findViewById(R.id.clientName);
        this.notificationCountView = view.findViewById(R.id.clientNotifications);
    }


    @Override
    public void fill(@NonNull Context context, @NonNull ClientsResponse data) {
        nameView.setText(data.getName());

        Integer notifications = data.getNotificationCount();
        if (notifications > 0){
            notificationCountView.setVisibility(View.VISIBLE);
            notificationCountView.setText(notifications.toString());
        }
        else
            notificationCountView.setVisibility(View.INVISIBLE);
    }
}
