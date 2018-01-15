package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.DialogActivityViewHolder;
import nl.inholland.imready.model.blocks.PersonalActivity;

public class DialogPersonalActivityAdapter extends BaseAdapter {

    private final Context context;
    private final List<PersonalActivity> activities;
    private final LayoutInflater layoutInflater;

    public DialogPersonalActivityAdapter(Context context, List<PersonalActivity> activities) {
        this.context = context;
        this.activities = activities;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DialogActivityViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_personal_activity_todo, parent, false);
            viewHolder = new DialogActivityViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DialogActivityViewHolder) convertView.getTag();
        }

        viewHolder.fill(context, activities.get(position));
        return convertView;
    }
}
