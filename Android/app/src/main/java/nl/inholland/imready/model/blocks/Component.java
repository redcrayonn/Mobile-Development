package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.service.deserializer.PostProcessingEnabler;

public class Component extends NamedEntityModel implements PostProcessingEnabler.PostProcessable {
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
    @SerializedName("Description")
    private String description;
    @SerializedName("Block")
    private Block block;
    @SerializedName("Activities")
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
        parcel.writeParcelable(block, i);
        parcel.writeTypedList(activities);
    }

    @Override
    public void gsonPostProcess() {
        for (Activity activity : getActivities()) {
            activity.setComponent(this);
        }
    }
}
