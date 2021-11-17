package com.zup.comics.comicsmanager.persistence.repositories;

import com.zup.comics.comicsmanager.persistence.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select count(user) from User user where (user.email = :email OR user.cpf = :cpf)")
    public int countByEmailOrCpf(@Param("email") String email, @Param("cpf") String cpf);

    @Query(value = "select count(user) from User user where (user.email = :email OR user.cpf = :cpf) AND user.id <> :id")
    public int countByEmailOrCpf(@Param("email") String email, @Param("cpf") String cpf, @Param("id") Integer id);
}
