package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public class Client extends User {
    @SerializedName("Points")
    private long points;
    @SerializedName("Caregiver")
    private Caregiver caregiver;
    @SerializedName("Blocks")
    private List<PersonalBlock> blocks = new ArrayList<>();
    @SerializedName("Relatives")
    private List<Relative> relatives = new ArrayList<>();

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public List<PersonalBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<PersonalBlock> blocks) {
        this.blocks = blocks;
    }

    public List<Relative> getRelatives() {
        return relatives;
    }

    public void setRelatives(List<Relative> relatives) {
        this.relatives = relatives;
    }
}
