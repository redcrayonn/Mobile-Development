package nl.inholland.projectapi.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import nl.inholland.projectapi.model.Appointment;
import nl.inholland.projectapi.model.Block;

@Singleton
public class MockDB
{
    
    public final static Map<Integer, Block> blocks = new HashMap<>();
    private static final Map<Integer, Appointment> appointments = new HashMap<>();
    
    public static void init()
    {
        addBlock(1, "surpal", "surpaaaal", "http://google.nl/logo");
        addBlock(2, "lijstje", "blokkie","http://bla.nl");
        
        appointments.put(1, new Appointment(1,"10-10-2017","Dinner with myself"));
        appointments.put(2, new Appointment(2,"11-10-2017","Play Project Cars with my imaginary friend"));
        appointments.put(3, new Appointment(3,"12-10-2017","Make Japanese Ramen"));
    }

   
    private static void addBlock(int id, String name, String description, String imageURL) {
        Block block = new Block();
        block.setId(id);
        block.setName(name);
        block.setDescription(description);
        block.setImageURL(imageURL);
        
        blocks.put(id, block);
    }
    
    public static List<Block> getAllBlocks() {
        return new ArrayList<>(blocks.values());
    }
    
    public static Block getBlockById(int id) {
        for (Block block : blocks.values()) {
            
            if(block.getId() == id) {
                return block;
            }
        }   
        return null;
    }

    public static Appointment getAppointmentById(int id) {

        for (Appointment appointment : appointments.values()) {

            if (appointment.getId() != id) {
                continue;
            }
            return appointment;
        }
        return null;
    }

    public static List<Appointment> getAllAppointments() {
        if (!appointments.isEmpty()) {
            return new ArrayList<>(appointments.values());
        }
        return Collections.emptyList();
    }
}
