package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.model.enums.BlockType;

import static br.com.zbra.androidlinq.Linq.stream;

public class BlockViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<PersonalBlock> {
    private ImageView blockImageView;
    private TextView blockTitleView;
    private TextView blockNotification;

    public BlockViewHolder(View view) {
        super(view);
        blockImageView = view.findViewById(R.id.block_image);
        blockTitleView = view.findViewById(R.id.block_title);
        blockNotification = view.findViewById(R.id.block_notification);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull PersonalBlock data, @Nullable OnChangeListener<PersonalBlock> changeListener) {
        if (data.getBlock().getType() == BlockType.ADD) {
            return;
        }

        blockTitleView.setText(data.getName());
        blockImageView.setImageDrawable(context.getDrawable(R.drawable.ic_home));

        // NOTIFICATION LABEL
        fillNotificationCounter(data);
    }

    private void fillNotificationCounter(PersonalBlock data) {
        // activities in block components that have an ongoing or pending assignment
        List<PersonalActivity> activities = stream(data.getComponents())
                .where(c -> c != null)
                .selectMany(PersonalComponent::getActivities)
                .where(a -> a != null)
                .where(a -> a.getStatus() != BlockPartStatus.DONE)
                .toList();
        // if there are any in above list, show/hide the counter
        int notificationCount = activities.size();
        boolean showCounter = notificationCount != 0;

        blockNotification.setText(String.valueOf(notificationCount));
        if (showCounter) {
            blockNotification.setVisibility(View.VISIBLE);
        } else {
            blockNotification.setVisibility(View.INVISIBLE);
        }
    }
}
