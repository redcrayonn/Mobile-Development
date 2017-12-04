package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.rest.AuthenticationService;
import nl.inholland.imready.service.rest.BlockService;
import nl.inholland.imready.service.rest.CaregiverService;
import nl.inholland.imready.service.rest.ClientService;
import nl.inholland.imready.service.rest.FamilyService;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockClient extends BaseClient {

    private final MockRetrofit mockRetrofit;

    public MockClient() {
        super();
        // setup mock network behaviour
        NetworkBehavior behavior = NetworkBehavior.create();

        // create retrofit's mock builder
        mockRetrofit = new MockRetrofit.Builder(this.retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        BehaviorDelegate<AuthenticationService> delegate = mockRetrofit.create(AuthenticationService.class);
        return new MockAuthenticationService(delegate);
    }

    @Override
    public BlockService getBlockService() {
        BehaviorDelegate<BlockService> delegate = mockRetrofit.create(BlockService.class);
        return new MockBlockService(delegate);
    }

    @Override
    public ClientService getClientService() {
        BehaviorDelegate<ClientService> delegate = mockRetrofit.create(ClientService.class);
        return new MockClientService(delegate);
    }

    @Override
    public FamilyService getFamilyService() {
        BehaviorDelegate<FamilyService> delegate = mockRetrofit.create(FamilyService.class);
        return new MockFamilyService(delegate);
    }

    @Override
    public CaregiverService getCaregiverService() {
        BehaviorDelegate<CaregiverService> delegate = mockRetrofit.create(CaregiverService.class);
        return new MockCaregiverService(delegate);
    }

    @Override
    public MessageBaseService getMessageService() {
        BehaviorDelegate<MessageBaseService> delegate = mockRetrofit.create(MessageBaseService.class);
        return new MockMessageService(delegate);
    }
}
