package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Component extends NamedEntityModel {
    private BlockPartStatus status = BlockPartStatus.IRRELEVANT;
    private List<Activity> activities = new ArrayList<>();

    public Component() {
    }

    public Component(String name) {
        super(name);
    }

    public Component(String id, String name) {
        super(id, name);
    }

    public Component(String id, String name, BlockPartStatus status, List<Activity> activities) {
        super(id, name);
        this.status = status;
        this.activities = activities;
    }

    public Component(Parcel in) {
        super(in);
        status = (BlockPartStatus) in.readSerializable();
        activities = new ArrayList<>();
        in.readTypedList(activities, Activity.CREATOR);
    }

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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public BlockPartStatus getStatus() {
        return status;
    }

    public void setStatus(BlockPartStatus status) {
        this.status = status;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeSerializable(status);
        parcel.writeTypedList(activities);
    }
}
