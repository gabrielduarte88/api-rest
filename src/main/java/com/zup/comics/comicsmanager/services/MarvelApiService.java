package com.zup.comics.comicsmanager.services;

import java.security.MessageDigest;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;
import com.zup.comics.comicsmanager.exceptions.MarvelApiException;
import com.zup.comics.comicsmanager.services.models.MarvelApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MarvelApiService {
    @Value("${marvel.api.url}")
    private String marvelUrl;
    @Value("${marvel.api.comics}")
    private String comicsEndpoint;

    public MarvelApiResponse getComic(long id) throws MarvelApiException {
        try {
            long timestamp = new Date().getTime();
            String publicKey = System.getenv("MARVEL_PUBLIC_KEY");
            String privateKey = System.getenv("MARVEL_PRIVATE_KEY");

            MessageDigest md = MessageDigest.getInstance("md5");
            md.update((timestamp + privateKey + publicKey).getBytes());

            String hash = DatatypeConverter.printHexBinary(md.digest()).toLowerCase();

            StringBuilder params = new StringBuilder();
            params.append(id);
            params.append("?ts=");
            params.append(timestamp);
            params.append("&apikey=");
            params.append(publicKey);
            params.append("&hash=");
            params.append(hash);

            WebClient client = WebClient.create();

            WebClient.ResponseSpec response = client.get().uri(marvelUrl + comicsEndpoint + params).accept(MediaType.APPLICATION_JSON).retrieve();

            MarvelApiResponse apiResponse = response.bodyToMono(MarvelApiResponse.class).block();

            return apiResponse;
        } catch (Exception ex) {
            throw new MarvelApiException(ex);
        }
    }
}
