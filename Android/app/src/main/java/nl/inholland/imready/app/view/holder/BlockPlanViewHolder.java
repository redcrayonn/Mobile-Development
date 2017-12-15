package nl.inholland.imready.app.view.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.util.BlockUtil;

public class BlockPlanViewHolder implements FillableViewHolder<Block> {
    private final ImageView groupIconView;
    private final TextView titleView;
    private final TextView groupCountView;

    public BlockPlanViewHolder(View view) {
        this.groupIconView = view.findViewById(R.id.block_image);
        this.titleView = view.findViewById(R.id.block_title);
        this.groupCountView = view.findViewById(R.id.group_count);
    }

    @Override
    public void fill(@NonNull Context context, @NonNull Block data, @Nullable OnChangeListener<Block> changeListener) {
        groupIconView.setImageDrawable(BlockUtil.getDrawableIcon(context, data.getType()));
        titleView.setText(data.getName());
        int count = data.getComponents() != null ? data.getComponents().size() : 0;
        groupCountView.setText(context.getString(R.string.component_count, count));
    }
}
