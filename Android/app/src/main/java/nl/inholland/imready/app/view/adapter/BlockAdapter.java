package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.BlockViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.rest.BlockService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockAdapter extends BaseAdapter implements LoadMoreListener, Callback<List<Block>> {

    private final int BUILDING_BLOCK_TYPE = 0;
    private final int ADD_BLOCK_TYPE = 1;

    private final Context context;
    private final BlockService blockService;
    private List<Block> blocks;

    public BlockAdapter(Context context) {
        this.context = context;

        ApiClient client = new MockClient();
        blockService = client.getBlockService();

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

            // fill based on type
            int type = getItemViewType(position);
            switch (type) {
                case BUILDING_BLOCK_TYPE:
                    viewHolder.fill(context, blocks.get(position));
                    break;
                case ADD_BLOCK_TYPE:
                    viewHolder.fill(context, null);
                    break;
            }
        } else {
            viewHolder = (BlockViewHolder) convertView.getTag();
            viewHolder.fill(context, blocks.get(position));
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == blocks.size() - 1) {
            return ADD_BLOCK_TYPE; // special ADD block type
        }
        return BUILDING_BLOCK_TYPE; // default block type
    }

    @Override
    public void loadMore() {
        blockService.getBlocks().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Block>> call, Response<List<Block>> response) {
        if (response.isSuccessful() && response.body() != null) {
            this.blocks = response.body();
            this.blocks.add(new Block("ADD"));
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<List<Block>> call, Throwable t) {

    }
}
