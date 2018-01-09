package nl.inholland.imready.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.view.holder.FillableViewHolder;
import nl.inholland.imready.app.view.holder.PersonalActivityViewHolder;
import nl.inholland.imready.app.view.holder.PersonalComponentViewHolder;
import nl.inholland.imready.app.view.listener.OnChangeListener;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.util.ColorUtil;

public class PersonalComponentExpandableListAdapter extends BaseExpandableListAdapter implements OnChangeListener<PersonalActivity> {

    private Context context;
    private List<PersonalComponent> components;
    private LayoutInflater inflater;

    private List<Integer> blendedComponentColors;

    public PersonalComponentExpandableListAdapter(Context context, List<PersonalComponent> components) {
        this.context = context;
        this.components = components;
        inflater = LayoutInflater.from(context);

        int accentColor = context.getResources().getColor(R.color.colorAccent);
        int mainColor = context.getResources().getColor(R.color.colorPrimary);

        blendedComponentColors = ColorUtil.between(accentColor, mainColor, components.size());
    }

    /* Groups */
    @Override
    public int getGroupCount() {
        return components.size();
    }

    /* Child */
    @Override
    public int getChildrenCount(int groupPosition) {
        PersonalComponent component = this.components.get(groupPosition);
        List<PersonalActivity> activities = component.getActivities();
        if (activities == null || activities.size() == 0) {
            return 1;
        }
        return activities.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.components.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        PersonalComponent component = this.components.get(groupPosition);
        List<PersonalActivity> activities = component.getActivities();
        if (activities == null || activities.size() == 0) {
            return null;
        }
        return activities.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /* Other */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PersonalComponent component = this.components.get(groupPosition);
        FillableViewHolder<PersonalComponent> viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_personal_component, parent, false);
            viewHolder = new PersonalComponentViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FillableViewHolder<PersonalComponent>) convertView.getTag();
        }

        ImageView groupIndicator = convertView.findViewById(R.id.group_indicator);
        groupIndicator.setSelected(isExpanded);

        // fill view from data here
        viewHolder.fill(context, component, null);

        convertView.setBackgroundColor(blendedComponentColors.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PersonalActivity activity = (PersonalActivity) getChild(groupPosition, childPosition);
        if (activity == null) {
            convertView = inflater.inflate(R.layout.simple_list_item2, parent, false);
            ((TextView)convertView).setText(R.string.empty_component);
        } else {
            FillableViewHolder<PersonalActivity> viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_personal_activity, parent, false);
                viewHolder = new PersonalActivityViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PersonalActivityViewHolder) convertView.getTag();
            }

            viewHolder.fill(context, activity, this);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onChanged(Object sender, PersonalActivity activity) {
        notifyDataSetChanged();
    }
}
