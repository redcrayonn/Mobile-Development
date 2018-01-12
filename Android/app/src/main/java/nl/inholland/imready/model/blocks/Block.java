package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.model.enums.BlockType;
import nl.inholland.imready.service.deserializer.PostProcessingEnabler;

public class Block extends NamedEntityModel implements PostProcessingEnabler.PostProcessable {
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

    @SerializedName("Description")
    private String description;
    @SerializedName("Type")
    private BlockType type = BlockType.LIVING;
    @SerializedName("Components")
    private List<Component> components = new ArrayList<>();

    public Block(Parcel in) {
        super(in);
        description = in.readString();
        type = BlockType.valueOf(in.readString());
        components = new ArrayList<>();
        in.readTypedList(components, Component.CREATOR);
    }

    public Block(BlockType type) {
        this.type = type;
    }

    public Block(String name, String description, BlockType type, ArrayList<Component> components) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.components = components;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        super.writeToParcel(parcel, flag);
        parcel.writeString(description);
        parcel.writeString(type.name());
        parcel.writeTypedList(components);
    }

    @Override
    public void gsonPostProcess() {
        for (Component component : getComponents()) {
            component.setBlock(this);
        }
    }
}
