package com.zup.comics.comicsmanager.persistence.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Comic", catalog = "API_Comics")
@Where(clause = "deleted_at IS NULL")
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "comic_id")
    @NotNull(message = "O ID da Comic deve ser informado")
    private Integer comicId;
    @ManyToOne
    @JoinColumn(name = "user")
    @NotNull(message = "O usuário deve ser informado")
    @JsonBackReference
    private User user;
    @NotBlank(message = "Necessário informar o título")
    private String title;
    @NotNull(message = "Necessário informar o preço")
    private Float price;
    @Transient
    private Float discount;
    @Transient
    private Float priceWithDiscount;
    @NotBlank(message = "Necessário informar o ISBN")
    private String isbn;
    private String description;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "deleted_at")
    private Date deletedAt;
    @OneToMany(mappedBy = "comic")
    @JsonManagedReference
    private Set<ComicAuthor> authors = new LinkedHashSet<>();

    public Comic() {}

    public Comic(Integer comicId, User user, String title, Float price, String isbn, String description) {
        this.comicId = comicId;
        this.user = user;
        this.title = title;
        this.price = price;
        this.isbn = isbn;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public Integer getComicId() {
        return comicId;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public Float getPrice() {
        return price;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setPriceWithDiscount(Float priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public Float getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public Set<ComicAuthor> getAuthors() {
        return authors;
    }

    public void parse(Comic other) {
        this.title = other.getTitle();
        this.price = other.getPrice();
        this.isbn = other.getIsbn();
        this.description = other.getDescription();
    }

    public void delete() {
        this.deletedAt = new Date();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comic other = (Comic) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Comic{" + "id=" + id + ", comicId=" + comicId + ", user=" + user + ", price=" + price + ", isbn=" + isbn + ", description=" + description + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + '}';
    }

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null)
            createdAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }
}
