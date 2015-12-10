package com.teamawesome.navii.server.model;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
@SuppressWarnings("unused")
public class UserPreference {
    private List<Preference> preferences;
    private String username;

    public UserPreference() {}

    private UserPreference(Builder builder) {
        this.username = builder.username;
        this.preferences = builder.preferences;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<Preference> preferences;
        private String username;

        public Builder() {}

        public Builder preferences(List<Preference> preferences) {
            this.preferences = preferences;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public UserPreference build() {
            return new UserPreference(this);
        }
    }
}