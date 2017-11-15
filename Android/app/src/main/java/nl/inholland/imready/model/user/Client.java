package nl.inholland.imready.model.user;

import java.util.List;

import nl.inholland.imready.model.blocks.Block;

public class Client extends User {
    private long points;
    private List<Family> family;
    private List<Caregiver> caregivers;
    private List<Block> blocks;
}
