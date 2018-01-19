package nl.inholland.imready.app.presenter.client;

import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.activity.client.ClientBlockDetailsView;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.model.PutClientActivityModel;
import nl.inholland.imready.service.rest.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientBlockDetailsPresenterImpl implements ClientBlockDetailsPresenter {
    private final ClientBlockDetailsView view;

    public ClientBlockDetailsPresenterImpl(ClientBlockDetailsView view) {
        this.view = view;
    }

    @Override
    public void putActivity(PersonalActivity activity, String content) {
        activity.setContent(content);
        view.showHandInDialog(activity);
    }

    @Override
    public void putActivity(PersonalActivity activity, BlockPartStatus status) {
        // network call
        UserCache cache = ImReadyApplication.getInstance().getCache(UserRole.CLIENT);
        ClientService clientService = ApiManager.getClient().getClientService();
        PutClientActivityModel model = new PutClientActivityModel(status, activity.getContent());
        clientService.putActivity(cache.getUserId(), activity.getId(), model).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showSucces();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //ignore
            }
        });
    }

    @Override
    public void saveActivity(PersonalActivity activity) {
        /*UserCache cache = ImReadyApplication.getInstance().getCache(UserRole.CLIENT);
        ClientService clientService = ApiManager.getClient(true).getClientService();
        PutClientActivityModel model = new PutClientActivityModel(activity.getStatus(), activity.getContent());
        clientService.putActivity(cache.getUserId(), activity.getId(), model).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //ignore
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //ignore
            }
        });*/
    }
}
