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

public class PersonalBlockAdapter extends BaseAdapter implements DataHolder<List<PersonalBlock>> {

    private final int BUILDING_BLOCK_TYPE = 0;
    private final int ADD_BLOCK_TYPE = 1;

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<PersonalBlock> personalBlocks;

    public PersonalBlockAdapter(Context context) {
        this.context = context;
        personalBlocks = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personalBlocks.size();
    }

    @Override
    public Object getItem(int position) {
        return personalBlocks.get(position);
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

        viewHolder.fill(context, personalBlocks.get(position), null);

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        PersonalBlock personalBlock = personalBlocks.get(position);
        Block block = personalBlock.getBlock();
        if (block.getType() == BlockType.ADD) {
            return ADD_BLOCK_TYPE; // special ADD list_item_personal_block type
        } else {
            return BUILDING_BLOCK_TYPE; // default list_item_personal_block type
        }
    }

    @Override
    public List<PersonalBlock> getData() {
        return personalBlocks;
    }

    @Override
    public void setData(List<PersonalBlock> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        // check to see if we already had data
        if (this.personalBlocks.size() > 0) {
            // remove the last block (the ADD block) to prevent it being added multiple times
            data.remove(data.size() - 1);
        }
        // publish loaded personalBlocks to the event bus
        EventBus.getDefault().post(new PersonalBlockLoadedEvent(data));
        // add the "ADD" block
        data.add(new PersonalBlock(BlockType.ADD));
        this.personalBlocks = data;
        notifyDataSetChanged();
    }
}
