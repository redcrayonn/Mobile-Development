package nl.inholland.imready.service.rest;

import java.security.InvalidParameterException;

import nl.inholland.imready.model.user.Caregiver;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.model.user.User;
import nl.inholland.imready.service.BaseClient;

public class RestClient extends BaseClient {

    public RestClient() {
        super();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return retrofit.create(AuthenticationService.class);
    }

    @Override
    public BlockService getBlockService() {
        return retrofit.create(BlockService.class);
    }

    @Override
    public ClientService getClientService() {
        return retrofit.create(ClientService.class);
    }

    @Override
    public FamilyService getFamilyService() {
        return retrofit.create(FamilyService.class);
    }

    @Override
    public CaregiverService getCaregiverService() {
        return retrofit.create(CaregiverService.class);
    }

    @Override
    public MessageBaseService getMessageService() {
        return retrofit.create(MessageClientService.class);
    }
}
