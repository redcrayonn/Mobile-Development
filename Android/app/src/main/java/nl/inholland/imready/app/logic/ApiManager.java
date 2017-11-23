package nl.inholland.imready.app.logic;

import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.rest.RestClient;

public class ApiManager {
    private static ApiClient restClient = new RestClient();
    private static ApiClient mockClient = new MockClient();

    public static ApiClient getClient() {
        return restClient;
    }

    public static ApiClient getClient(boolean mocked) {
        if (mocked) {
            return mockClient;
        }
        return getClient();
    }
}
