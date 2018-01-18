package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
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
import nl.inholland.imready.app.view.holder.CaregiverPlanComponentViewHolder;
import nl.inholland.imready.app.view.holder.CaregiverPlanHeaderViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.zbra.androidlinq.Linq.stream;

/**
 * Created by Peter on 08/01/2018.
 */
public class CaregiverPlanExpandAdapter extends BaseExpandableListAdapter implements LoadMoreListener, Callback<FutureplanResponse> {
    private final Context context;
    private final LayoutInflater inflater;
    private SwipeRefreshLayout refreshLayout;
    private final CaregiverService caregiverService;
    private List<PersonalComponent> components;
    private final String clientId;


    public CaregiverPlanExpandAdapter(Context context, String clientId, SwipeRefreshLayout refreshLayout){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.refreshLayout = refreshLayout;

        ApiClient client = ApiManager.getClient();
        caregiverService = client.getCaregiverService();
        this.components = new ArrayList<>();
        this.clientId = clientId;
    }

    @Override
    public int getGroupCount() {return components.size();}

    @Override
    public int getChildrenCount(int position) {
        if (this.components.size() == 0) {
            return 0;
        }

        PersonalComponent component = components.get(position);
        if (component.getActivities() == null) {
            return 0;
        }
        return component.getActivities().size();
    }

    @Override
    public Object getGroup(int position) {return components.get(position);}

    @Override
    public Object getChild(int group, int child) {
        if (this.components.size() == 0) {
            return null;
        }
        PersonalComponent component = components.get(group);
        if (component.getActivities() == null || component.getActivities().size() == 0) {
            return null;
        }
        return component.getActivities().get(child);
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
    public View getGroupView(int componentPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        PersonalComponent component = (PersonalComponent) getGroup(componentPosition);

        FillableViewHolder<PersonalComponent> viewHolder;
        view = inflater.inflate(R.layout.list_item_caregiver_block_header, viewGroup, false);
        viewHolder = new CaregiverPlanHeaderViewHolder(view, isExpanded);
        view.setTag(viewHolder);

        //Sets group indicating icon
        ImageView groupIndicator = view.findViewById(R.id.group_indicator);
        if(getChildrenCount(componentPosition) > 0){
            groupIndicator.setSelected(isExpanded);
        }
        else{
            groupIndicator.setVisibility(View.INVISIBLE);
        }

        viewHolder.fill(context, component);

        return view;
    }

    @Override
    public View getChildView(int blockPosition, int componentPosition, boolean b, View view, ViewGroup viewGroup) {
        PersonalActivity component = (PersonalActivity) getChild(blockPosition, componentPosition);
        view = inflater.inflate(R.layout.list_item_component_notification, viewGroup, false);
        FillableViewHolder<PersonalActivity> viewHolder = new CaregiverPlanComponentViewHolder(view);

        viewHolder.fill(context, component);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onResponse(Call<FutureplanResponse> call, Response<FutureplanResponse> response) {
        FutureplanResponse blockresponse = response.body();
        if (response.isSuccessful() && blockresponse != null) {
            List<PersonalBlock> blocks = blockresponse.getBlocks();
            this.components = stream(blocks).selectMany(PersonalBlock::getComponents).toList();
            notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<FutureplanResponse> call, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {
        refreshLayout.setRefreshing(true);
        caregiverService.getClientPlan(clientId).enqueue(this);
    }
}
