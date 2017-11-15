package nl.inholland.imready.app.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.BlockViewHolder;
import nl.inholland.imready.model.blocks.Block;

public class BlockAdapter extends BaseAdapter {
    private final Context context;

    private final List<Block> blocks;

    public BlockAdapter(Context context) {
        this.context = context;
        blocks = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return blocks.size();
    }

    @Override
    public Object getItem(int position) {
        return blocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlockViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.block, parent, false);

            // init viewholder
            viewHolder = new BlockViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BlockViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
