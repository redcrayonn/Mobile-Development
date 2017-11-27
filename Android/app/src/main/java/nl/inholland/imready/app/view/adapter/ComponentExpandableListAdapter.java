package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.ActivityViewHolder;
import nl.inholland.imready.app.view.holder.ComponentViewHolder;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Component;

public class ComponentExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Component> components;
    private LayoutInflater inflater;

    public ComponentExpandableListAdapter(Context context, List<Component> components) {
        this.context = context;
        this.components = components;
        inflater = LayoutInflater.from(context);
    }

    /* Groups */
    @Override
    public int getGroupCount() {
        return components.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.components.get(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Component component = this.components.get(groupPosition);
        FillableViewHolder<Component> viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_component, parent, false);
            viewHolder = new ComponentViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<Component>) convertView.getTag();
        }

        ImageView groupIndicator = convertView.findViewById(R.id.group_indicator);
        groupIndicator.setSelected(isExpanded);

        // fill view from data here
        viewHolder.fill(context, component);
        return convertView;
    }

    /* Child */
    @Override
    public int getChildrenCount(int groupPosition) {
        Component component = this.components.get(groupPosition);
        List<Activity> activities = component.getActivities();
        return activities.size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Component component = components.get(groupPosition);
        return component.getActivities().get(childPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getChild(groupPosition, childPosition);
        FillableViewHolder<Activity> viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_activity, parent, false);
            viewHolder = new ActivityViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ActivityViewHolder) convertView.getTag();
        }

        viewHolder.fill(context, activity);

        return convertView;
    }

    /* Other */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
