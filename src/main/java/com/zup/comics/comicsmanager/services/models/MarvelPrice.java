package com.zup.comics.comicsmanager.services.models;

import javax.validation.constraints.NotNull;

public class MarvelPrice {
    @NotNull(message = "O tipo do preço não foi retornado")
    private String type;
    @NotNull(message = "O preço não foi retornado")
    private Float price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MarvelPrice {price=" + price + ", type=" + type + "}";
    }

}
