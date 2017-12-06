package inholland.tabletapplication.Models;

/**
 * Created by Peter on 06/12/2017.
 */

public class Activity {
    private int id;
    private String name;
    private String description;
    private String content;
    private Status status;
    private int points;
    private String assignment;

    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getContent(){return content;}
    public Status getStatus(){return status;}
    public int getPoints(){return points;}
    public String getAssignment(){return assignment;}
}
