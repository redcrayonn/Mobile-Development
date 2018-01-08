package nl.inholland.imready.app.logic;

import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.rest.RestClient;

public class ApiManager {
    private static BaseClient restClient = new RestClient();
    private static BaseClient mockClient = new MockClient();

    public static BaseClient getClient() {
        return restClient;
    }

    public static BaseClient getClient(boolean mocked) {
        if (mocked) {
            return mockClient;
        }
        return getClient();
    }
}
