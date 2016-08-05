package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
@SuppressWarnings("unused")
public class UserPreference {
    private List<Preference> preferences;
    private String email;

    public UserPreference() {}

    private UserPreference(Builder builder) {
        this.email = builder.email;
        this.preferences = builder.preferences;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public String getEmail() {
        return email;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<Preference> preferences;
        private String email;

        public Builder() {}

        public Builder preferences(List<Preference> preferences) {
            this.preferences = preferences;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserPreference build() {
            return new UserPreference(this);
        }
    }
}
