package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Component extends NamedEntityModel {
    public static final Creator<Component> CREATOR = new Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
    private String description;
    private Block block;
    private List<Activity> activities = new ArrayList<>();

    public Component(Parcel in) {
        super(in);
        description = in.readString();
        block = in.readParcelable(Block.class.getClassLoader());
        activities = new ArrayList<>();
        in.readTypedList(activities, Activity.CREATOR);
    }

    public Component(Block block, String name, ArrayList<Activity> activities) {

        this.block = block;
        this.name = name;
        this.activities = activities;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(description);
        parcel.writeParcelable(block, 0);
        parcel.writeTypedList(activities);
    }
}
