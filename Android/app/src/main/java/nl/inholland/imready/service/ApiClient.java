package nl.inholland.imready.service;

import nl.inholland.imready.service.rest.AuthenticationService;
import nl.inholland.imready.service.rest.BlockService;
import nl.inholland.imready.service.rest.CaregiverService;
import nl.inholland.imready.service.rest.ClientService;
import nl.inholland.imready.service.rest.FamilyService;
import nl.inholland.imready.service.rest.MessageService;

public interface ApiClient {
    AuthenticationService getAuthenticationService();
    BlockService getBlockService();
    ClientService getClientService();
    FamilyService getFamilyService();
    CaregiverService getCaregiverService();
    MessageService getMessageService();
}
