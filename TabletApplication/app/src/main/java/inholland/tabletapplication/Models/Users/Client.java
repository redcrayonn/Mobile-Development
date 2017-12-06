package inholland.tabletapplication.Models.Users;

import java.util.ArrayList;

import inholland.tabletapplication.Models.Block;

public class Client extends User{
    private int points;
    private ArrayList<EntityModel> family;
    private ArrayList<EntityModel> caregivers;
    private ArrayList<Block> blocks;

    public int getPoints(){return points;}
    public ArrayList<EntityModel> getFamily(){return family;}
    public ArrayList<EntityModel> getCaregivers(){return caregivers;}
    public ArrayList<Block> getBlocks(){return blocks;}

}
