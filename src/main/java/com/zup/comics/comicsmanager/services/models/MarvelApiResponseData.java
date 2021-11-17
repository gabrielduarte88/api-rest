package com.zup.comics.comicsmanager.services.models;

import java.util.Arrays;
import javax.validation.constraints.NotEmpty;

public class MarvelApiResponseData {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Integer count;
    @NotEmpty(message = "O resultado não foi retornado")
    private MarvelApiResponseResult results[];

    public MarvelApiResponseData() {
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MarvelApiResponseResult[] getResults() {
        return results;
    }

    public void setResults(MarvelApiResponseResult[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MarvelApiResponseData {count=" + count + ", limit=" + limit + ", offset=" + offset + ", results=" + Arrays.toString(results) + ", total=" + total + "";
    }
    
}
