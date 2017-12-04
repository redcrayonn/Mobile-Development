package nl.inholland.imready.app.view.listener;

import java.util.List;

public interface OnLoadedListener<T> {
    void onLoaded(List<T> body);
}
