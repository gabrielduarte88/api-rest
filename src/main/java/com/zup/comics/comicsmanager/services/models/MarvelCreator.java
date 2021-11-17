package com.zup.comics.comicsmanager.services.models;

public class MarvelCreator {
    private String name;
    private String role;

    public MarvelCreator() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MarvelCreator {name=" + name + ", role=" + role + "}";
    }

}
