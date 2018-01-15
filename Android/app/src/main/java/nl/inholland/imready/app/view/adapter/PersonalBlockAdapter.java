package nl.inholland.imready.app.view.adapter;

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
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.enums.BlockType;

public class PersonalBlockAdapter extends BaseAdapter implements DataHolder<List<PersonalBlock>> {

    private final int BUILDING_BLOCK_TYPE = 0;
    private final int ADD_BLOCK_TYPE = 1;

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<PersonalBlock> personalBlocks;

    private PersonalBlock addBlock = new PersonalBlock(BlockType.ADD);

    public PersonalBlockAdapter(Context context) {
        this.context = context;
        personalBlocks = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personalBlocks.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (personalBlocks.size() == 0) {
            return addBlock;
        }
        else if (position == personalBlocks.size()) {
            return addBlock;
        }
        return personalBlocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        switch (type) {
            case ADD_BLOCK_TYPE:
                convertView = layoutInflater.inflate(R.layout.list_item_block_add, parent, false);
                break;
            case BUILDING_BLOCK_TYPE:
            default:
                convertView = layoutInflater.inflate(R.layout.list_item_personal_block, parent, false);
                break;
        }

        BlockViewHolder viewHolder = new BlockViewHolder(convertView);
        viewHolder.fill(context, (PersonalBlock) getItem(position));

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        PersonalBlock personalBlock = (PersonalBlock) getItem(position);
        Block block = personalBlock.getBlock();
        if (block.getType() == BlockType.ADD)
            return ADD_BLOCK_TYPE;
        else
            return BUILDING_BLOCK_TYPE;
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
        this.personalBlocks = data;
        notifyDataSetChanged();
    }
}
