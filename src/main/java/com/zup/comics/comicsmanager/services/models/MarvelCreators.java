package com.zup.comics.comicsmanager.services.models;

import java.util.Arrays;
import javax.validation.constraints.NotEmpty;

public class MarvelCreators {
    private Integer available;
    @NotEmpty(message = "Os criadores do resultado não foram retornados")
    private MarvelCreator items[];

    public MarvelCreators() {}

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public MarvelCreator[] getItems() {
        return items;
    }

    public void setItems(MarvelCreator[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MarvelCreators [available=" + available + ", items=" + Arrays.toString(items) + "]";
    }
}
