package nl.inholland.imready.model.user;

import nl.inholland.imready.model.NamedEntityModel;

public abstract class User extends NamedEntityModel {
    private UserRole role;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

