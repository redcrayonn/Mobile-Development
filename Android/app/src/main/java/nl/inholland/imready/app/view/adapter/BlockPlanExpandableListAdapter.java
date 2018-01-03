package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.BlockPlanViewHolder;
import nl.inholland.imready.app.view.holder.ComponentPlanViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.Component;

public class BlockPlanExpandableListAdapter extends BaseExpandableListAdapter implements DataHolder<List<Block>> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Block> blocks;

    public BlockPlanExpandableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        blocks = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return blocks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.blocks.size() == 0) {
            return 0;
        }
        Block block = blocks.get(groupPosition);
        if (block.getComponents() == null) {
            return 0;
        }
        return block.getComponents().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return blocks.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.blocks.size() == 0) {
            return null;
        }
        Block block = blocks.get(groupPosition);
        if (block.getComponents() == null || block.getComponents().size() == 0) {
            return null;
        }
        return block.getComponents().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Block block = (Block) getGroup(groupPosition);
        FillableViewHolder<Block> viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_block_header, parent, false);
            viewHolder = new BlockPlanViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<Block>) convertView.getTag();
        }

        ImageView groupIndicator = convertView.findViewById(R.id.group_indicator);
        if (block.getComponents() == null || block.getComponents().size() == 0) {
            groupIndicator.setVisibility(View.INVISIBLE);
            groupIndicator.setSelected(false);
        } else {
            groupIndicator.setVisibility(View.VISIBLE);
            groupIndicator.setSelected(isExpanded);
        }

        viewHolder.fill(context, block, null);

        // set convertview background to grey if it has been added

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Component component = (Component) getChild(groupPosition, childPosition);
        FillableViewHolder<Component> viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_component_edit, parent, false);
            viewHolder = new ComponentPlanViewHolder(convertView, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<Component>) convertView.getTag();
        }

        viewHolder.fill(context, component, null);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public List<Block> getData() {
        return blocks;
    }

    @Override
    public void setData(List<Block> data) {
        this.blocks = data;
        notifyDataSetChanged();
    }
}
