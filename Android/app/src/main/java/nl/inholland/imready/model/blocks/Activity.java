package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import nl.inholland.imready.model.NamedEntityModel;

public class Activity extends NamedEntityModel {
    //region Fields
    private String description;
    private int points;
    private BlockPartStatus status = BlockPartStatus.IRRELEVANT;
    private String content;
    private String assignment;
    //endregion

    //region Constructors
    public Activity() {
    }

    public Activity(String name) {
        super(name);
    }

    public Activity(String id, String name) {
        super(id, name);
    }
    //endregion

    //region Getters/Setters
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

    public BlockPartStatus getStatus() {
        return status;
    }

    public void setStatus(BlockPartStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }
    //endregion

    //region Parcelable implementation
    public Activity(Parcel in) {
        super(in);
        description = in.readString();
        points = in.readInt();
        status = (BlockPartStatus) in.readSerializable();
        content = in.readString();
        assignment = in.readString();
    }

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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(description);
        parcel.writeInt(points);
        parcel.writeSerializable(status);
        parcel.writeString(content);
        parcel.writeString(assignment);
    }
    //endregion
}
