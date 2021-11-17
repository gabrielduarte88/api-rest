package com.zup.comics.comicsmanager.persistence.models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "User", catalog = "API_Comics")
@Where(clause = "deleted_at IS NULL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Necessário informar o nome")
    @Pattern(regexp = ".+((\\s).+)+", message = "Necessário informar o nome completo")
    @Size(min = 2, max = 60, message = "O nome deve conter de 2 a 60 caracteres")
    private String name;
    @NotBlank(message = "Necessário informar o e-mail")
    @Email(message = "Por favor, informe um e-mail válido")
    private String email;
    @NotBlank(message = "Necessário informar o CPF")
    @CPF(message = "Por favor, informe um CPF válido")
    private String cpf;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Necessário informar a data de nascimento")
    @Past(message = "A data de nascimento deve ser uma data passada")
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "deleted_at")
    private Date deletedAt;
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    @JsonManagedReference
    private Set<Comic> comics = new LinkedHashSet<>();

    public User() {}

    public User(Integer id) {
        this.id = id;
    }

    public User(String name, String email, String cpf, Date birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getBirthDate() {
        return birthDate;
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

    public Set<Comic> getComics() {
        return comics;
    }

    public void parse(User other) {
        this.name = other.getName();
        this.email = other.getEmail();
        this.cpf = other.getCpf();
        this.birthDate = other.getBirthDate();
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", cpf='" + cpf + '\'' + ", birthDate=" + birthDate + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + '}';
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
