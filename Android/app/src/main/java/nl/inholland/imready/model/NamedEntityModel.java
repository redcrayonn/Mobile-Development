package nl.inholland.imready.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class NamedEntityModel extends EntityModel {
    public static final Creator<NamedEntityModel> CREATOR = new Creator<NamedEntityModel>() {
        @Override
        public NamedEntityModel createFromParcel(Parcel in) {
            return new NamedEntityModel(in);
        }

        @Override
        public NamedEntityModel[] newArray(int size) {
            return new NamedEntityModel[size];
        }
    };

    @SerializedName("Name")
    protected String name;

    public NamedEntityModel() {
        super();
    }

    public NamedEntityModel(String name) {
        super();
        this.name = name;
    }

    public NamedEntityModel(String id, String name) {
        super(id);
        this.name = name;
    }

    protected NamedEntityModel(Parcel in) {
        super(in);
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(name);
    }
}
