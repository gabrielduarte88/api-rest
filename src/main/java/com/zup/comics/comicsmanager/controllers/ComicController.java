package com.zup.comics.comicsmanager.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.websocket.server.PathParam;
import com.zup.comics.comicsmanager.exceptions.MarvelApiException;
import com.zup.comics.comicsmanager.persistence.models.Comic;
import com.zup.comics.comicsmanager.persistence.models.ComicAuthor;
import com.zup.comics.comicsmanager.persistence.models.User;
import com.zup.comics.comicsmanager.persistence.repositories.ComicAuthorRepository;
import com.zup.comics.comicsmanager.persistence.repositories.ComicRepository;
import com.zup.comics.comicsmanager.persistence.repositories.UserRepository;
import com.zup.comics.comicsmanager.services.MarvelApiService;
import com.zup.comics.comicsmanager.services.models.MarvelApiResponse;
import com.zup.comics.comicsmanager.services.models.MarvelApiResponseResult;
import com.zup.comics.comicsmanager.services.models.MarvelCreator;
import com.zup.comics.comicsmanager.services.models.MarvelPrice;
import com.zup.comics.comicsmanager.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComicController {
    Logger logger = LoggerFactory.getLogger(ComicController.class);

    @Autowired
    Validator validator;

    private final ComicRepository repository;
    private final UserRepository userRepository;
    private final ComicAuthorRepository authorRepository;

    private final MarvelApiService marvelApiService;

    ComicController(ComicRepository repository, UserRepository userRepository, ComicAuthorRepository authorRepository, MarvelApiService marvelApiService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.marvelApiService = marvelApiService;
    }

    @GetMapping("/comics")
    public ResponseEntity<Map<String, Object>> list(Pageable pageable) {
        return ApiResponse.create(repository.findAll(pageable));
    }

    @GetMapping("/users/{userId}/comics")
    public ResponseEntity<Map<String, Object>> list(@PathVariable(value = "userId") Integer userId, Pageable pageable) {
        return ApiResponse.create(repository.listByUserId(new User(userId), pageable).map(comic -> {
            String isbnDigit = comic.getIsbn().substring(comic.getIsbn().length() - 1, comic.getIsbn().length());

            DayOfWeek today = LocalDate.now().getDayOfWeek();

            if ((today == DayOfWeek.MONDAY && (isbnDigit.equals("0") || isbnDigit.equals("1"))) || (today == DayOfWeek.TUESDAY && (isbnDigit.equals("2") || isbnDigit.equals("3")))
                    || (today == DayOfWeek.WEDNESDAY && (isbnDigit.equals("4") || isbnDigit.equals("5")))
                    || (today == DayOfWeek.THURSDAY && (isbnDigit.equals("6") || isbnDigit.equals("7")))
                    || (today == DayOfWeek.FRIDAY && (isbnDigit.equals("8") || isbnDigit.equals("9")))) {
                comic.setDiscount(10f);
                comic.setPriceWithDiscount(comic.getPrice() - (comic.getPrice() / 100 * comic.getDiscount()));

                System.out.println(comic.getDiscount());
                System.out.println(comic.getPriceWithDiscount());
            }

            return comic;
        }));
    }

    @PostMapping(value = "/comics")
    @Transactional
    public ResponseEntity<Map<String, Object>> create(Integer userId, Integer comicId) throws MarvelApiException {
        User user = userId != null ? userRepository.getById(userId) : null;

        if (user == null) {
            return ApiResponse.create("Necessário informar o usuário para o registro", HttpStatus.BAD_REQUEST);
        }

        if (comicId == null) {
            return ApiResponse.create("Necessário informar a identificação da Comic", HttpStatus.BAD_REQUEST);
        }

        MarvelApiResponse response = marvelApiService.getComic(comicId);

        Set<ConstraintViolation<Object>> errors = validator.validate(response);

        if (!errors.isEmpty()) {
            return ApiResponse.create(errors, "Alguns dados são inválidos para a inserção/alteração");
        }

        MarvelApiResponseResult result = response.getData().getResults()[0];
        MarvelPrice price = result.getPrices()[0];

        Comic comic = repository.getByComicId(comicId);

        if (comic == null) {
            comic = new Comic(result.getId().intValue(), user, result.getTitle(), price.getPrice(), result.getIsbn(), result.getDescription());
        } else {
            Comic other = new Comic(result.getId().intValue(), user, result.getTitle(), price.getPrice(), result.getIsbn(), result.getDescription());

            comic.parse(other);

            authorRepository.deleteAll(comic.getAuthors());
        }

        HttpStatus status = comic.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;

        repository.save(comic);

        for (MarvelCreator creator : result.getCreators().getItems()) {
            ComicAuthor author = new ComicAuthor(comic, creator.getName(), creator.getRole());

            authorRepository.save(author);

            comic.getAuthors().add(author);
        }

        return ApiResponse.create(comic, status);
    }

    @GetMapping("/comics/{id}")
    public ResponseEntity<Map<String, Object>> view(@PathVariable(value = "id") Integer id) {
        return repository.findById(id).map(entity -> ApiResponse.create(entity, HttpStatus.OK))
                .orElse(ApiResponse.create("Nenhum registro correspondente foi encontrado", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/comics/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathParam(value = "id") Integer id) {
        return repository.findById(id).map(entity -> {
            entity.delete();

            repository.save(entity);

            return ApiResponse.create(entity, HttpStatus.OK);
        }).orElse(ApiResponse.create("Nenhum registro correspondente foi encontrado", HttpStatus.NOT_FOUND));
    }
}
