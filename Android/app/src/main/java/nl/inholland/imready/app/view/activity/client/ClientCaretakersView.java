package nl.inholland.imready.app.view.activity.client;


import java.util.List;

import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.user.User;

public interface ClientCaretakersView extends View {
    void setCaretakers(List<User> caretakers);
}
