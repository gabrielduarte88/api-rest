package com.zup.comics.comicsmanager.controllers;

import java.util.Map;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import com.zup.comics.comicsmanager.persistence.models.User;
import com.zup.comics.comicsmanager.persistence.repositories.UserRepository;
import com.zup.comics.comicsmanager.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> list(Pageable pageable) {
        return ApiResponse.create(repository.findAll(pageable));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> create(@Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ApiResponse.create(errors, "Alguns dados são inválidos para a inserção");
        }

        if (repository.countByEmailOrCpf(user.getEmail(), user.getCpf()) > 0) {
            return ApiResponse.create("Já existem registros com os dados informados", HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.create(repository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> view(@PathVariable(value = "id") Integer id) {
        return repository.findById(id).map(entity -> ApiResponse.create(entity, HttpStatus.OK))
                .orElse(ApiResponse.create("Nenhum registro correspondente foi encontrado", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable(value = "id") Integer id, @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ApiResponse.create(errors, "Alguns dados são inválidos para a alteração");
        }

        if (repository.countByEmailOrCpf(user.getEmail(), user.getCpf(), user.getId()) > 0) {
            return ApiResponse.create("Já existem registros com os dados informados", HttpStatus.BAD_REQUEST);
        }

        return repository.findById(id).map(entity -> {
            entity.parse(user);

            repository.save(entity);

            return ApiResponse.create(entity, HttpStatus.OK);
        }).orElse(ApiResponse.create("Nenhum registro correspondente foi encontrado", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathParam(value = "id") Integer id) {
        return repository.findById(id).map(entity -> {
            entity.delete();

            repository.save(entity);

            return ApiResponse.create(entity, HttpStatus.OK);
        }).orElse(ApiResponse.create("Nenhum registro correspondente foi encontrado", HttpStatus.NOT_FOUND));
    }
}
