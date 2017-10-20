package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.ClientDAO;
import org.bson.types.ObjectId;

public class ClientBlockService extends BaseService {

    private final ClientDAO clientDAO;
    private final BlockDAO blockDAO;

    @Inject
    public ClientBlockService(ClientDAO clientDAO, BlockDAO blockDAO) {
        this.clientDAO = clientDAO;
        this.blockDAO = blockDAO;
    }

    public List<BuildingBlock> getAll(Client client, int count) {
        List<BuildingBlock> blocks = client.getBuildingBlocks();
        requireResult(blocks, "Not Found");
        return reduceList(blocks, count);
    }

    public URI create(Client client, BuildingBlock newBlock, UriInfo uriInfo) {
        requireResult(client, "Client not found");
        BuildingBlock block = blockDAO.get(newBlock.getId());
        requireResult(block, "Block not found");
        block.setId(new ObjectId());
        block.getActivities().forEach(a -> a.setId(new ObjectId()));
        client.getBuildingBlocks().add(block);
        clientDAO.update(client);
        return buildUri(uriInfo, block.getId());
    }

    public BuildingBlock getById(Client client, String blockId) {
        for (BuildingBlock b : client.getBuildingBlocks()) {
            if (b.getId().equals(blockId)) {
                return b;
            }
        }
        throw new NotFoundException("Not found");
    }

    public void delete(Client client, String blockId) {
        client.getBuildingBlocks().removeIf(m -> m.getId().equals(blockId));
        clientDAO.update(client);
    }
}
