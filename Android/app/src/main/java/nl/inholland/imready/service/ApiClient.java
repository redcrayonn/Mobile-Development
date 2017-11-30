package nl.inholland.imready.service;

import nl.inholland.imready.service.rest.ServerAuthenticationService;
import nl.inholland.imready.service.rest.BlockService;
import nl.inholland.imready.service.rest.CaregiverService;
import nl.inholland.imready.service.rest.ClientService;
import nl.inholland.imready.service.rest.FamilyService;
import nl.inholland.imready.service.rest.MessageBaseService;

public interface ApiClient {
    ServerAuthenticationService getAuthenticationService();
    BlockService getBlockService();
    ClientService getClientService();
    FamilyService getFamilyService();
    CaregiverService getCaregiverService();
    MessageBaseService getMessageService();
}
