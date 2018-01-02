package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.events.PersonalBlockLoadedEvent;
import nl.inholland.imready.app.view.holder.BlockViewHolder;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.enums.BlockType;

public class PersonalBlockAdapter extends BaseAdapter{

    private final int BUILDING_BLOCK_TYPE = 0;
    private final int ADD_BLOCK_TYPE = 1;

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<PersonalBlock> blocks;

    public PersonalBlockAdapter(Context context) {
        this.context = context;
        blocks = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
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
        BlockViewHolder viewHolder = null;
        if (convertView == null) {
            int type = getItemViewType(position);

            switch (type) {
                case BUILDING_BLOCK_TYPE:
                    convertView = layoutInflater.inflate(R.layout.list_item_personal_block, parent, false);
                    break;
                case ADD_BLOCK_TYPE:
                default:
                    convertView = layoutInflater.inflate(R.layout.list_item_block_add, parent, false);
                    break;
            }
            viewHolder = new BlockViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BlockViewHolder) convertView.getTag();
        }

        viewHolder.fill(context, blocks.get(position), null);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        PersonalBlock personalBlock = blocks.get(position);
        Block block = personalBlock.getBlock();
        if (block.getType() == BlockType.ADD) {
            return ADD_BLOCK_TYPE; // special ADD list_item_personal_block type
        } else {
            return BUILDING_BLOCK_TYPE; // default list_item_personal_block type
        }
    }

    public void setData(List<PersonalBlock> data) {
        this.blocks = data;
        if (this.blocks == null) {
            this.blocks = new ArrayList<>();
        }
        // publish loaded data to the event bus
        EventBus.getDefault().post(new PersonalBlockLoadedEvent(this.blocks));

        this.blocks.add(new PersonalBlock(BlockType.ADD));
        notifyDataSetChanged();
    }
}
