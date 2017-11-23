package nl.inholland.imready.model.blocks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Block extends NamedEntityModel {
    private String description;
    private String imageURL;
    private List<Component> components = new ArrayList<>();

    public Block() {
        super();
    }

    public Block(String name) {
        super(name);
    }

    public Block(String id, String name) {
        super(id, name);
    }

    public Block(String id, String name, String description, String imageURL, List<Component> components) {
        super(id, name);
        this.description = description;
        this.imageURL = imageURL;
        this.components = components;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Block(Parcel in) {
        super(in);
        description = in.readString();
        imageURL = in.readString();
        components = new ArrayList<>();
        in.readTypedList(components, Component.CREATOR);
    }

    public static final Creator<Block> CREATOR = new Creator<Block>() {
        @Override
        public Block createFromParcel(Parcel in) {
            return new Block(in);
        }

        @Override
        public Block[] newArray(int size) {
            return new Block[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(description);
        parcel.writeString(imageURL);
        parcel.writeTypedList(components);
    }
}
