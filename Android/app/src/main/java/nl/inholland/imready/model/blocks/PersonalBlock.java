package nl.inholland.imready.model.blocks;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;
import nl.inholland.imready.model.enums.BlockType;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.model.user.User;
import nl.inholland.imready.service.deserializer.PostProcessingEnabler;

public class PersonalBlock extends NamedEntityModel implements PostProcessingEnabler.PostProcessable {
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
        this.block = new Block(type);
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        super.writeToParcel(parcel, flag);
        parcel.writeTypedList(components);
        parcel.writeParcelable(block, flag);
        parcel.writeParcelable(client, flag);
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

    @Override
    public void gsonPostProcess() {
        for (PersonalComponent component : getComponents()) {
            component.setBlock(this);
        }
    }
}
