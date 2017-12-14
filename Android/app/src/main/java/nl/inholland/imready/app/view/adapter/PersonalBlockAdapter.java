package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.view.holder.BlockViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.app.view.listener.OnLoadedListener;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import nl.inholland.imready.service.rest.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalBlockAdapter extends BaseAdapter implements LoadMoreListener, Callback<FutureplanResponse> {

    private final int BUILDING_BLOCK_TYPE = 0;
    private final int ADD_BLOCK_TYPE = 1;

    private final Context context;
    private final ClientService clientService;
    private final LayoutInflater layoutInflater;
    private final List<OnLoadedListener<PersonalBlock>> onLoadedListeners;
    private List<PersonalBlock> blocks;

    public PersonalBlockAdapter(Context context, List<OnLoadedListener<PersonalBlock>> onLoadedListeners) {
        this.context = context;
        this.onLoadedListeners = onLoadedListeners;

        ApiClient client = ApiManager.getClient();
        clientService = client.getClientService();

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
                    break;
                case ADD_BLOCK_TYPE:
                default:
                    convertView = layoutInflater.inflate(R.layout.list_item_block_add, parent, false);
                    break;
            }
            viewHolder = new BlockViewHolder(convertView);
            viewHolder.fill(context, blocks.get(position), null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BlockViewHolder) convertView.getTag();
            viewHolder.fill(context, blocks.get(position), null);
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
        String clientId = "222c352b-fafa-46c5-b375-39dcdc99dec8";
        clientService.getFuturePlan(clientId).enqueue(this);
    }

    @Override
    public void onResponse(Call<FutureplanResponse> call, Response<FutureplanResponse> response) {
        FutureplanResponse futureplanResponse = response.body();
        if (response.isSuccessful() && futureplanResponse != null) {
            this.blocks = futureplanResponse.getBlocks();
            for (OnLoadedListener<PersonalBlock> listener : onLoadedListeners) {
                listener.onLoaded(this.blocks);
            }
            if (this.blocks == null) {
                this.blocks = new ArrayList<>();
            }
            this.blocks.add(new PersonalBlock("ADD"));
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<FutureplanResponse> call, Throwable t) {

    }
}
