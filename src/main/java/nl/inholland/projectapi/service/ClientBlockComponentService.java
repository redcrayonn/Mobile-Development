package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.persistence.ClientDAO;

public class ClientBlockComponentService {
    
    private final ClientDAO clientDAO;

    @Inject
    public ClientBlockComponentService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Component> getComponents(Client client, String blockId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                return b.getComponents();
            }
        }
        throw new NotFoundException("Components not found");
    }

    public Component getComponent(Client client, String blockId, String componentId) {
        for (Component c : getComponents(client, blockId)) {
            if (c.getId().equals(componentId)) {
                return c;
            }
        }
        throw new NotFoundException("Component not found");
    }    
}
