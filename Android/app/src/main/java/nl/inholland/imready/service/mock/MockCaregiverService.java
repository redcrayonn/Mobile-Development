package nl.inholland.imready.service.mock;

import io.reactivex.Single;
import nl.inholland.imready.service.model.FutureplanResponse;
import nl.inholland.imready.service.model.PutFeedbackModel;
import nl.inholland.imready.service.rest.CaregiverService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

class MockCaregiverService implements CaregiverService {
    private final BehaviorDelegate<CaregiverService> delegate;

    public MockCaregiverService(BehaviorDelegate<CaregiverService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<ResponseBody> getClients(String caregiverId) {
        return null;
    }

    @Override
    public Call<FutureplanResponse> getClientPlan(String clientId) {
        return null;
    }

    @Override
    public Call<Void> giveFeedback(String caregiverid, String clientId, String activityId, PutFeedbackModel feedbackModel) {
        return null;
    }
}
