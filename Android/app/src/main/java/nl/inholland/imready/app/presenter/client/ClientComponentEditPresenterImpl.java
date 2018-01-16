package nl.inholland.imready.app.presenter.client;


import android.content.Context;

import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.activity.client.ClientComponentEditView;
import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.Component;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.rest.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.zbra.androidlinq.Linq.stream;

public class ClientComponentEditPresenterImpl implements ClientComponentEditPresenter, Callback<Void> {
    private final ClientComponentEditView view;
    private final ClientService service;
    private Component component;

    public ClientComponentEditPresenterImpl(ClientComponentEditView view, ClientService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public int getPointsFromComponent(Component component) {
        return stream(component.getActivities())
                .sum(Activity::getPoints);
    }

    @Override
    public List<String> getActivityNamesFromComponent(Component component) {
        return stream(component.getActivities())
                .select(Activity::getName)
                .toList();
    }

    @Override
    public void addComponentToFutureplan(Component component) {
        this.component = component;
        UserCache userCache = ImReadyApplication.getInstance().getCache(UserRole.CLIENT);
        String clientId = userCache.getUserId();
        service.enrollComponent(clientId, component.getId()).enqueue(this);
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Context context = view.getContext();
            String succesMessage = context.getString(R.string.personal_component_succes, component.getName());
            view.showMessage(succesMessage);
            view.goToFutureplan();
        } else {
            onFailure(call, new Throwable());
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        Context context = view.getContext();
        String errorMessage = context.getString(R.string.enroll_component_failed);
        view.showMessage(errorMessage);
    }
}
