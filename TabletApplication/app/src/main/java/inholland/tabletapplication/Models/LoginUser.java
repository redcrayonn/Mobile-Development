package inholland.tabletapplication.Models;

/**
 * Created by Peter on 27/11/2017.
 */

public class LoginUser {
    public String Username;
    public String Password;

    public LoginUser(){

    }

    public boolean CheckCredentials(){
        if (Username != null &&
                !Username.isEmpty() &&
                Password != null &&
                !Password.isEmpty()){
            //Ask API

            return true;
        }
        else
            return false;
    }
}
