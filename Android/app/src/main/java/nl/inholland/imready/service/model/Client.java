package nl.inholland.imready.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Peter on 23/12/2017.
 */

public class Client {
    //Fields
    @SerializedName("Id")
    private String id;

    @SerializedName("FirstName")
    private String firstname;

    @SerializedName("LastName")
    private String lastname;

    @SerializedName("Points")
    private int points;

    @SerializedName("Email")
    private String email;

    @SerializedName("Roles")
    private ArrayList<String> roles;

    //Properties
    public String getId() { return this.id; }

    public void setId(String Id) { this.id = Id; }

    public String getFirstName() { return this.firstname; }

    public void setFirstName(String FirstName) { this.firstname = FirstName; }

    public String getLastName() { return this.lastname; }

    public void setLastName(String LastName) { this.lastname = LastName; }

    public int getPoints() { return this.points; }

    public void setPoints(int Points) { this.points = Points; }

    public String getEmail() { return this.email; }

    public void setEmail(String Email) { this.email = email; }

    public ArrayList<String> getRoles() { return this.roles; }

    public void setRoles(ArrayList<String> Roles) { this.roles = Roles; }
}
