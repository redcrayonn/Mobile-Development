package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.model.enums.BlockType;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.model.user.User;

public class PersonalBlock extends NamedEntityModel {
    public static final Creator<PersonalBlock> CREATOR = new Creator<PersonalBlock>() {
        @Override
        public PersonalBlock createFromParcel(Parcel in) {
            return new PersonalBlock(in);
        }

        @Override
        public PersonalBlock[] newArray(int size) {
            return new PersonalBlock[size];
        }
    };

    @SerializedName("Components")
    private List<PersonalComponent> components;
    @SerializedName("Block")
    private Block block;
    @SerializedName("Client")
    private Client client;

    public PersonalBlock(Parcel in) {
        super(in);
        components = new ArrayList<>();
        in.readTypedList(components, PersonalComponent.CREATOR);
        block = in.readParcelable(Block.class.getClassLoader());
        client = in.readParcelable(User.class.getClassLoader());
    }

    public PersonalBlock(BlockType type) {
        if (this.block == null) {
            this.block = new Block(type);
        }
    }

    @Override
    public String getName() {
        return block.getName();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(components);
        parcel.writeParcelable(block, 0);
        parcel.writeParcelable(client, 0);
    }

    public List<PersonalComponent> getComponents() {
        return components;
    }

    public void setComponents(List<PersonalComponent> components) {
        this.components = components;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
