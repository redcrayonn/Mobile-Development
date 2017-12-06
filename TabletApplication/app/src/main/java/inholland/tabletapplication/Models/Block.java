package inholland.tabletapplication.Models;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Peter on 06/12/2017.
 */

public class Block {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private ArrayList<Activity> activities;

    public String getName(){return name;}
    public String getDescription(){return description;}
    public ArrayList<Activity> getActivities(){return activities;}
}
