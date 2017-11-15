package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.persistence.BlockDAO;

public class BlockComponentService extends BaseService{
    
    private final BlockDAO blockDAO;

    @Inject
    public BlockComponentService(BlockDAO blockDAO) {
        this.blockDAO = blockDAO;
    }

    public List<Component> getAll(String blockId, int count) {
        return reduceList(blockDAO.get(blockId).getComponents(), count);
    }

    public Component getById(String blockId, String componentId) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        for (Component c : block.getComponents()) {
            if (c.getId().equals(componentId)) {
                return c;
            }
        }
        throw new NotFoundException("Component not found");
    }

    public URI create(String blockId, Component component, UriInfo uriInfo) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        try {
            List<Component> componentList = block.getComponents();
            component.createNewId();
            componentList.add(component);
            blockDAO.update(block);
            return buildUri(uriInfo, component.getId());
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    public void update(String blockId, String componentId, Component updatedComponent) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");

        requiredValue(updatedComponent.getName());

        for (Component c : block.getComponents()) {
            if (c.getId().equals(componentId)) {
                c.setName(updatedComponent.getName());
                blockDAO.update(block);
            }
        }
    }

    public void delete(String blockId, String componentId) {
        BuildingBlock block = blockDAO.get(blockId);
        requireResult(block, "BuildingBlock not found");
        block.getComponents().removeIf(a -> a.getId().equals(componentId));
        blockDAO.update(block);
    }    
}
