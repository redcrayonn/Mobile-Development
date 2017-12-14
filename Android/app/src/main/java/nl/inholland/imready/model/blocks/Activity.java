package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import nl.inholland.imready.model.NamedEntityModel;

public class Activity extends NamedEntityModel {
    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
    private String description;
    private int points;
    private Component component;

    public Activity() {
    }

    public Activity(Parcel in) {
        super(in);
        description = in.readString();
        points = in.readInt();
    }

    public Activity(Component component, String name, String description, int points) {
        this.component = component;
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(description);
        parcel.writeInt(points);
    }
}
