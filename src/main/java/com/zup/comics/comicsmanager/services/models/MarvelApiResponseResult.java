package com.zup.comics.comicsmanager.services.models;

import java.util.Arrays;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MarvelApiResponseResult {
    @NotNull(message = "O ID do resultado não foi retornado")
    private Long id;
    private Long digitalId;
    @NotNull(message = "O título do resultado não foi retornado")
    private String title;
    private Long issueNumber;
    private String variantDescription;
    private String description;
    private Date modified;
    @NotNull(message = "O ISBN do resultado não foi retornado")
    private String isbn;
    private String upc;
    private String diamondCode;
    private String ean;
    private String issn;
    private String format;
    private Integer pageCount;
    @NotEmpty(message = "Os preços do resultado não foram retornados")
    private MarvelPrice prices[];
    @NotNull(message = "Os criadores do resultado não foram retornados")
    private MarvelCreators creators;

    public MarvelApiResponseResult() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(Long digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Long issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public MarvelPrice[] getPrices() {
        return prices;
    }

    public void setPrices(MarvelPrice[] prices) {
        this.prices = prices;
    }

    public MarvelCreators getCreators() {
        return creators;
    }

    public void setCreators(MarvelCreators creators) {
        this.creators = creators;
    }

    @Override
    public String toString() {
        return "MarvelApiResponseResult [creators=" + creators + ", description=" + description + ", diamondCode=" + diamondCode + ", digitalId=" + digitalId + ", ean=" + ean
                + ", format=" + format + ", id=" + id + ", isbn=" + isbn + ", issn=" + issn + ", issueNumber=" + issueNumber + ", modified=" + modified + ", pageCount=" + pageCount
                + ", prices=" + Arrays.toString(prices) + ", title=" + title + ", upc=" + upc + ", variantDescription=" + variantDescription + "]";
    }

}
