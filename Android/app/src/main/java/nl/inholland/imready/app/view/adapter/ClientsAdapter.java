package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.CaregiverHomeViewHolder;
import nl.inholland.imready.service.model.ClientsResponse;

public class ClientsAdapter extends BaseAdapter implements DataHolder<List<ClientsResponse>> {
    private List<ClientsResponse> clients;

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final SwipeRefreshLayout refreshLayout;

    public ClientsAdapter(Context context, SwipeRefreshLayout refreshLayout) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.refreshLayout = refreshLayout;

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
    public List<ClientsResponse> getData() {
        return clients;
    }

    @Override
    public void setData(List<ClientsResponse> data) {
        clients = data;
        notifyDataSetChanged();
    }
}
