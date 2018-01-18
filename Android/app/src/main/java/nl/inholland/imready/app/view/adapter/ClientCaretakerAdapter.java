package nl.inholland.imready.app.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.model.user.User;

public class ClientCaretakerAdapter extends BaseAdapter implements DataHolder<List<User>> {

    private List<User> caretakers;

    private final LayoutInflater layoutInflator;
    private final Context context;

    public ClientCaretakerAdapter(Context context) {
        this.context = context;
        this.layoutInflator = LayoutInflater.from(context);

        caretakers = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return caretakers.size();
    }

    @Override
    public Object getItem(int position) {
        return caretakers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = layoutInflator.inflate(R.layout.list_item_caretaker, parent, false);

        User item = (User) getItem(position);
        TextView caretakerName = convertView.findViewById(R.id.caretaker_name);
        TextView caretakerTitle = convertView.findViewById(R.id.caretaker_title);
        caretakerName.setText(String.format("%s %s", item.getFirstName(), item.getLastName()));
        caretakerTitle.setText(UserRole.CAREGIVER.name());

        return convertView;
    }

    @Override
    public List<User> getData() {
        return caretakers;
    }

    @Override
    public void setData(List<User> data) {
        caretakers = data;
        notifyDataSetChanged();
    }
}
