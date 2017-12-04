package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.activity.client.ClientHomeActivity;
import nl.inholland.imready.app.view.holder.BlockViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.app.view.listener.OnLoadedListener;
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
    private final LayoutInflater layoutInflater;

    private final List<OnLoadedListener<Block>> onLoadedListeners;

    public BlockAdapter(Context context, List<OnLoadedListener<Block>> onLoadedListeners) {
        this.context = context;
        this.onLoadedListeners = onLoadedListeners;

        ApiClient client = new MockClient();
        blockService = client.getBlockService();

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
        BlockViewHolder viewHolder;
        if (convertView == null) {
            int type = getItemViewType(position);

            switch (type) {
                case BUILDING_BLOCK_TYPE:
                    convertView = layoutInflater.inflate(R.layout.list_item_block, parent, false);
                    viewHolder = new BlockViewHolder(convertView);
                    viewHolder.fill(context, blocks.get(position));
                    convertView.setTag(viewHolder);
                    break;
                case ADD_BLOCK_TYPE:
                    convertView = layoutInflater.inflate(R.layout.list_item_block_add, parent, false);
                    viewHolder = new BlockViewHolder(convertView);
                    viewHolder.fill(context, null);
                    convertView.setTag(viewHolder);
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
            return ADD_BLOCK_TYPE; // special ADD list_item_block type
        }
        return BUILDING_BLOCK_TYPE; // default list_item_block type
    }

    @Override
    public void loadMore() {
        blockService.getBlocks().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Block>> call, Response<List<Block>> response) {
        if (response.isSuccessful() && response.body() != null) {
            this.blocks = response.body();
            for (OnLoadedListener<Block> listener : onLoadedListeners) {
                listener.onLoaded(response.body());
            }
            this.blocks.add(new Block("ADD"));
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<List<Block>> call, Throwable t) {

    }
}
