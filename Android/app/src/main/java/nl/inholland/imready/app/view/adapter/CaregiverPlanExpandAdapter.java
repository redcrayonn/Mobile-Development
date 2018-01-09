package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.view.holder.CaregiverPlanComponentViewHolder;
import nl.inholland.imready.app.view.holder.CaregiverPlanHeaderViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Peter on 08/01/2018.
 */
public class CaregiverPlanExpandAdapter extends BaseExpandableListAdapter implements LoadMoreListener, Callback<FutureplanResponse> {
    private final Context context;
    private final LayoutInflater inflater;
    private final CaregiverService caregiverService;
    private List<PersonalBlock> blocks;
    private final String clientId;


    public CaregiverPlanExpandAdapter(Context context, String clientId){
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        ApiClient client = ApiManager.getClient();
        caregiverService = client.getCaregiverService();
        this.blocks = new ArrayList<>();
        this.clientId = clientId;
    }

    @Override
    public int getGroupCount() {return blocks.size();}

    @Override
    public int getChildrenCount(int position) {
        if (this.blocks.size() == 0) {
            return 0;
        }

        PersonalBlock block = blocks.get(position);
        if (block.getComponents() == null) {
            return 0;
        }
        return block.getComponents().size();
    }

    @Override
    public Object getGroup(int position) {return blocks.get(position);}

    @Override
    public Object getChild(int group, int child) {
        if (this.blocks.size() == 0) {
            return null;
        }
        PersonalBlock block = blocks.get(group);
        if (block.getComponents() == null || block.getComponents().size() == 0) {
            return null;
        }
        return block.getComponents().get(child);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int blockPosition, boolean componentPosition, View view, ViewGroup viewGroup) {
        PersonalBlock block = (PersonalBlock) getGroup(blockPosition);

        FillableViewHolder<PersonalBlock> viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_caregiver_block_header, viewGroup, false);
            viewHolder = new CaregiverPlanHeaderViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<PersonalBlock>) view.getTag();
        }

        viewHolder.fill(context, block, null);

        return view;
    }

    @Override
    public View getChildView(int blockPosition, int componentPosition, boolean b, View view, ViewGroup viewGroup) {
        PersonalComponent component = (PersonalComponent) getChild(blockPosition, componentPosition);
        FillableViewHolder<PersonalComponent> viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_component_notification, viewGroup, false);

            viewHolder = new CaregiverPlanComponentViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<PersonalComponent>) view.getTag();
        }

        viewHolder.fill(context, component, null);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onResponse(Call<FutureplanResponse> call, Response<FutureplanResponse> response) {
        List<PersonalBlock> blocks = response.body().getBlocks();
        if (response.isSuccessful() && blocks != null) {
            this.blocks = blocks;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<FutureplanResponse> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {
        caregiverService.getClientPlan(clientId).enqueue(this);
    }
}
