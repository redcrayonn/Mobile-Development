package nl.inholland.imready.app.view.holder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.inholland.imready.R;
import nl.inholland.imready.model.blocks.Block;

public class BlockViewHolder extends RecyclerView.ViewHolder implements FillableViewHolder<Block> {
    ImageView blockImageView;
    TextView blockTitleView;
    public BlockViewHolder(View view) {
        super(view);
        blockImageView = view.findViewById(R.id.block_image);
        blockTitleView = view.findViewById(R.id.block_title);
    }

    @Override
    public void fill(Block data) {
        if (data == null) {
            blockTitleView.setText(null);
            return;
        }

        blockTitleView.setText(data.getName());
    }
}
