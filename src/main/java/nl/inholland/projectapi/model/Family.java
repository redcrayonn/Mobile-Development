/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Stefan
 */
public class Family extends User{

    public Family(ObjectId id, String username, List<Message> messages) {
        super(id, username, messages);
    }

    public Family() {
    }
    
    
}
