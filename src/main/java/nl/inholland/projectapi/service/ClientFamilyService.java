/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.persistence.FamilyDAO;
import nl.inholland.projectapi.persistence.ClientDAO;

/**
 *
 * @author kelvi
 */
public class ClientFamilyService extends BaseService {
    
    private final ClientDAO clientDAO;
    private final FamilyDAO familyDAO;
    
    @Inject
    public ClientFamilyService(ClientDAO clientDAO, FamilyDAO familyDAO) {
        this.clientDAO = clientDAO;
        this.familyDAO = familyDAO;
    }
    
    
    public List<Family> getAll(Client client, int count) {
        List<Family> family = client.getFamily();
        requireResult(family, "Not Found");
        return reduceList(family, count);
    }
    
    
    public URI create(Client client, Family family, UriInfo uriInfo) {
        if(familyDAO.get(family.getId()) != null) {
            client.getFamily().add(family);
            clientDAO.update(client);
            return buildUri(uriInfo, family.getId());
        }
        throw new NotFoundException("Family not found");
    }
    
    public Family get(Client client, String familyId) {
        for(Family f : getAll(client, -1)) {
            if(f.getId().equals(familyId)) {
                return f;
            }
        }
        throw new NotFoundException("Family not found");
    }
    
    public void update(Client client, String familyId, Family newFamily) {
        for(Family f : client.getFamily()) {
            if(f.getId().equals(familyId)) {
                f = newFamily;
                clientDAO.update(client);
            }
        }
        throw new NotFoundException("Family not found");
    }
    
    public void delete(Client client, String familyId) {
        if(!client.getFamily().removeIf(f -> f.getId().equals(familyId))) {
            throw new NotFoundException("Family not found");
        }
        clientDAO.update(client);
    }
}
