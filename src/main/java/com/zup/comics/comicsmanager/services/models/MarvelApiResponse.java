package com.zup.comics.comicsmanager.services.models;

import javax.validation.constraints.NotNull;

public class MarvelApiResponse {
    @NotNull(message = "O código da resposta não foi retornado")
    private Integer code;
    @NotNull(message = "O status da resposta não foi retornado")
    private String status;
    private String copyright;
    @NotNull(message = "Os dados da resposta não foram retornados")
    private MarvelApiResponseData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public MarvelApiResponseData getData() {
        return data;
    }

    public void setData(MarvelApiResponseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarvelApiResponse {" + "  code=" + code + ", copyright=" + copyright + ", data=" + data + ", status=" + status + "}";
    }

}
