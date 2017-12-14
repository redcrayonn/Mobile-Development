package nl.inholland.imready.model.user;

import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public class Client extends User {
    private long points;
    private Caregiver caregivers;
    private List<PersonalBlock> blocks;
    private List<Relative> relatives;

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Caregiver getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(Caregiver caregivers) {
        this.caregivers = caregivers;
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
