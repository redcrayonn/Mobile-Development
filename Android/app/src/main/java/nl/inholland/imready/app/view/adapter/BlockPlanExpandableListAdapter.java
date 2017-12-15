package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.view.holder.BlockPlanViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.rest.BlockService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockPlanExpandableListAdapter extends BaseExpandableListAdapter implements LoadMoreListener, Callback<List<Block>> {

    private final Context context;
    private final BlockService blockService;
    private final LayoutInflater inflater;
    private List<Block> blocks;

    public BlockPlanExpandableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        ApiClient client = ApiManager.getClient();
        blockService = client.getBlockService();

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
            return 0;
        }
        Block block = blocks.get(groupPosition);
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
        Block block = this.blocks.get(groupPosition);
        FillableViewHolder<Block> viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_block_header, parent, false);
            viewHolder = new BlockPlanViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<Block>) convertView.getTag();
        }

        ImageView groupIndicator = convertView.findViewById(R.id.group_indicator);
        groupIndicator.setSelected(isExpanded);

        viewHolder.fill(context, block, null);

        // set convertview background to grey if it has been added

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void loadMore() {
        blockService.getBlocks().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Block>> call, Response<List<Block>> response) {
        List<Block> blocks = response.body();
        if (response.isSuccessful() && blocks != null) {
            this.blocks = blocks;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<List<Block>> call, Throwable t) {
        Toast.makeText(context, R.string.block_failed, Toast.LENGTH_SHORT).show();
    }
}
