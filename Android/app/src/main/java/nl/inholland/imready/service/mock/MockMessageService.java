package nl.inholland.imready.service.mock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockMessageService extends MockMessageBaseService implements MessageBaseService {

    private static final String ClientId = "222c352b-fafa-46c5-b375-39dcdc99dec8";
    private static final String CaregiverId = "z9y8x7w6";

    private static final List<Message> MOCK_MESSAGES = new ArrayList<Message>() {{
        Random rng = new Random();

        for (int i = 0; i < 30; i++) {
            String messageId = String.valueOf(rng.nextInt());
            boolean sentByCaregiver = rng.nextBoolean();
            String senderId = sentByCaregiver ? CaregiverId : ClientId;
            String receiverId = sentByCaregiver ? ClientId : CaregiverId;

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
            Date timeSent = new Date();

            Message message = new Message(messageId,
                    senderId,
                    receiverId,
                    "this is message #" + i + " - " + dateFormat.format(timeSent),
                    timeSent,
                    false);
            add(message);
        }
    }};

    public MockMessageService(BehaviorDelegate<MessageBaseService> delegate) {
        super(delegate);
    }

    @Override
    public Call<List<Message>> getMessages(String id, Integer count) {
        try {
            requireResult(id, "id can not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Message> response = (count != null && count != 0)
                ? MOCK_MESSAGES.subList(0, count)
                : MOCK_MESSAGES;
        Collections.reverse(response);
        return delegate.returningResponse(response).getMessages(id, count);
    }

    @Override
    public Call<Message> getMessage(String id, String messageId) {
        try {
            requireResult(id, "id can not be null");
            requireResult(messageId, "messageId can not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Call<EmptyResponse> postMessage(String id, Message body) {
        try {
            requireResult(id, "id can not be null");
            requireResult(body, "body can not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MOCK_MESSAGES.add(body);

        return delegate.returningResponse(new EmptyResponse()).postMessage(id, body);
    }

    @Override
    public Call<EmptyResponse> deleteMessage(String id, String messageId) {
        try {
            requireResult(id, "id can not be null");
            requireResult(messageId, "messageId can not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delegate.returningResponse(new EmptyResponse()).deleteMessage(id, messageId);
    }
}
