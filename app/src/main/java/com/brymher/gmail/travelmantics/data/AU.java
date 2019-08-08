package com.brymher.gmail.travelmantics.data;

import androidx.annotation.Nullable;

public class AU {
    private String name;
    private String profile_image;
    private Boolean admin;

    public AU(String id, String name, Boolean admin, @Nullable String profile_image) {
        this.name = name;
        this.profile_image = profile_image;
        this.admin = admin;
        this.id = id;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
