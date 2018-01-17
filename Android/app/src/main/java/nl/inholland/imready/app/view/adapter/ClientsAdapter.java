package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.holder.CaregiverHomeViewHolder;
import nl.inholland.imready.app.view.listener.LoadMoreListener;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.model.ClientsResponse;
import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Peter on 17/12/2017.
 */

public class ClientsAdapter extends BaseAdapter implements LoadMoreListener, Callback<List<ClientsResponse>> {
    private List<ClientsResponse> clients;

    private final Context context;
    private final CaregiverService caregiverService;
    private final LayoutInflater layoutInflater;
    private final SwipeRefreshLayout refreshLayout;

    public ClientsAdapter(Context context, SwipeRefreshLayout refreshLayout) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.refreshLayout = refreshLayout;

        ApiClient client = ApiManager.getClient();
        caregiverService = client.getCaregiverService();

        clients = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CaregiverHomeViewHolder viewHolder = null;

        convertView = layoutInflater.inflate(R.layout.list_item_client, parent, false);
        viewHolder = new CaregiverHomeViewHolder(convertView);
        convertView.setTag(viewHolder);

        viewHolder.fill(context, clients.get(position));

        return convertView;
    }

    @Override
    public void onResponse(Call<List<ClientsResponse>> call, Response<List<ClientsResponse>> response) {
        List<ClientsResponse> clients = response.body();
        if (response.isSuccessful() && clients != null) {
            this.clients = clients;
            notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFailure(Call<List<ClientsResponse>> call, Throwable t) {
        Toast.makeText(context, R.string.block_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore() {
        refreshLayout.setRefreshing(true);
        UserCache cache = ImReadyApplication.getInstance().getCache(UserRole.CAREGIVER);
        String userId = cache.getUserId();

        caregiverService.getClients(userId).enqueue(this);
    }
}
