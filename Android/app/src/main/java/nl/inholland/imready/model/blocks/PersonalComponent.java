package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.service.deserializer.PostProcessingEnabler;

public class PersonalComponent extends NamedEntityModel implements PostProcessingEnabler.PostProcessable {
    public static final Creator<PersonalComponent> CREATOR = new Creator<PersonalComponent>() {
        @Override
        public PersonalComponent createFromParcel(Parcel in) {
            return new PersonalComponent(in);
        }

        @Override
        public PersonalComponent[] newArray(int size) {
            return new PersonalComponent[size];
        }
    };
    @SerializedName("Activities")
    private List<PersonalActivity> activities;
    @SerializedName("Status")
    private BlockPartStatus status;
    @SerializedName("Component")
    private Component component;
    private PersonalBlock block;

    protected PersonalComponent(Parcel in) {
        super(in);
        status = BlockPartStatus.valueOf(in.readString());
        activities = new ArrayList<>();
        in.readTypedList(activities, PersonalActivity.CREATOR);
        component = in.readParcelable(Component.class.getClassLoader());
        block = in.readParcelable(PersonalBlock.class.getClassLoader());
    }

    public List<PersonalActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<PersonalActivity> activities) {
        this.activities = activities;
    }

    public BlockPartStatus getStatus() {
        return status;
    }

    public void setStatus(BlockPartStatus status) {
        this.status = status;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public String getName() {
        return component.getName();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        super.writeToParcel(parcel, flag);
        parcel.writeString(status.name());
        parcel.writeTypedList(activities);
        parcel.writeParcelable(component, flag);
        parcel.writeParcelable(block, flag);
    }

    public void setBlock(PersonalBlock block) {
        this.block = block;
    }

    public PersonalBlock getBlock() {
        return block;
    }

    @Override
    public void gsonPostProcess() {
        for (PersonalActivity activity : getActivities()) {
            activity.setComponent(this);
        }
    }
}
