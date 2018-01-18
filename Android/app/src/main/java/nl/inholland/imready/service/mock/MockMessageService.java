package nl.inholland.imready.service.mock;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.model.user.Chat;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.rest.MessageBaseService;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockMessageService extends MockMessageBaseService implements MessageBaseService {

    private static final String ClientId = "222c352b-fafa-46c5-b375-39dcdc99dec8";
    private static final String CaregiverId = "f8b1282e-ee65-45d2-ac13-676a8cbca8d3";

    private static final List<Message> MOCK_MESSAGES = new ArrayList<Message>() {{
        Random rng = new Random();

        for (int i = 0; i < 30; i++) {
            String messageId = String.valueOf(rng.nextInt());
            boolean sentByCaregiver = rng.nextBoolean();
            String senderId = sentByCaregiver ? CaregiverId : ClientId;

            Date timeSent = new Date();

            Message message = new Message(messageId,
                    senderId,
                    "this is message #" + i,
                    timeSent,
                    false);
            add(message);
        }
    }};

    public MockMessageService(BehaviorDelegate<MessageBaseService> delegate) {
        super(delegate);
    }

    @Override
    public Single<ResponseBody> getChat(String userId, String receiverId) {
        Chat chat = new Chat();
        chat.setId(userId);
        chat.setMessages(MOCK_MESSAGES);

        Gson gson = ApiManager.provideGson();
        String json = gson.toJson(chat);
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("json"), json);

        return delegate.returningResponse(responseBody).getChat(userId, receiverId);
    }

    @Override
    public Call<EmptyResponse> postMessage(String userId, String receiverId, Message body) {
        try {
            requireResult(userId, "id can not be null");
            requireResult(body, "body can not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MOCK_MESSAGES.add(body);

        return delegate.returningResponse(new EmptyResponse()).postMessage(userId, receiverId, body);
    }
}
