package inholland.tabletapplication.Models.Users;

/**
 * Created by Peter on 05/12/2017.
 */

public abstract class User extends EntityModel{
    private String name;
    private String role;

    public String getName(){
        return name;
    }
}
