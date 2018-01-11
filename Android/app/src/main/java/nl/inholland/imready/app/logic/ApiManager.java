package nl.inholland.imready.app.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.deserializer.PostProcessingEnabler;
import nl.inholland.imready.service.mock.MockClient;
import nl.inholland.imready.service.rest.RestClient;

public class ApiManager {
    private static BaseClient client;
    private static BaseClient mockClient;
    private static Gson gson;

    public static BaseClient getClient() {
        if (client == null) {
            client = new RestClient();
        }
        return client;
    }

    public static BaseClient getClient(boolean mocked) {
        if (mocked) {
            if (mockClient == null) {
                mockClient = new MockClient();
            }
            return mockClient;
        }
        return getClient();
    }

    public static Gson provideGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .registerTypeAdapterFactory(new PostProcessingEnabler())
                    //.registerTypeAdapter(Block.class, new BlockDeserializer())
                    .create();
        }
        return gson;
    }
}
