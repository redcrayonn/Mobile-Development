package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.EntityModel;
import nl.inholland.projectapi.presentation.model.BaseView;

public class EntityPresenter extends BasePresenter {

    public List<BaseView> present(List<? extends EntityModel> models) {
        List<BaseView> views = new ArrayList<>();

        for (EntityModel model : models) {
            views.add(present(model));
        }
        return views;
    }

    public BaseView present(EntityModel model) {
        BaseView view = new BaseView();
        view.id = model.getId();
        return view;
    }

}
