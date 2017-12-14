package nl.inholland.imready.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class EntityModel implements Parcelable {
    public static final Creator<EntityModel> CREATOR = new Creator<EntityModel>() {
        @Override
        public EntityModel createFromParcel(Parcel in) {
            return new EntityModel(in);
        }

        @Override
        public EntityModel[] newArray(int size) {
            return new EntityModel[size];
        }
    };
    protected String id;

    public EntityModel() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public EntityModel(String id) {
        super();
        if (id == null || id.isEmpty()) {
            return;
        }
        this.id = id;
    }

    protected EntityModel(Parcel in) {
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
