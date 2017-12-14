package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.BlockPartStatus;
import nl.inholland.imready.model.blocks.Component;

import static br.com.zbra.androidlinq.Linq.stream;

public class BlockViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<Block> {
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
    public void fill(@Nullable Context context, @NonNull Block data, @Nullable OnChangeListener<Block> changeListener) {
        if (data == null) {
            return;
        }

        blockTitleView.setText(data.getName());
        blockImageView.setImageDrawable(context.getDrawable(R.drawable.ic_home));

        // NOTIFICATION LABEL
        fillNotificationCounter(data);
    }

    private void fillNotificationCounter(Block data) {
        // activities in block components that have an ongoing or pending assignment
        List<Activity> activities = stream(data.getComponents())
                .where(c -> c != null)
                .selectMany(Component::getActivities)
                .where(a -> a != null)
                .where(a -> a.getStatus() != BlockPartStatus.COMPLETE ||
                        a.getStatus() != BlockPartStatus.IRRELEVANT )
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
