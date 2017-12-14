package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.model.enums.BlockPartStatus;

public class PersonalComponent extends NamedEntityModel {
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
    private List<PersonalActivity> activities;
    private BlockPartStatus status;
    private Component component;

    protected PersonalComponent(Parcel in) {
        super(in);
        activities = new ArrayList<>();
        in.readTypedList(activities, PersonalActivity.CREATOR);
        status = BlockPartStatus.valueOf(in.readString());
        component = in.readParcelable(Component.class.getClassLoader());
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
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(activities);
        parcel.writeString(status.name());
        parcel.writeParcelable(component, 0);
    }
}
