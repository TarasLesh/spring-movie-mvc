package com.movie.mvc.springmoviemvc.service;

import com.movie.mvc.springmoviemvc.dtos.MovieDTO;
import com.movie.mvc.springmoviemvc.model.Movie;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieService implements IMovieService {
    @Override
    public List<Movie> getAllMovies() {
        RestTemplate restTemplate = new RestTemplate();  // з допомогою RestTemplate робимо request на інший мікросервіс.
        String url = "http://localhost:8081/movies";  // опис URL

        HttpHeaders requestHeaders = new HttpHeaders();  // формуємо наші header. Тобто створимо об'єкт requestHeaders
        String authHeader = "Basic " + Base64.encodeBase64String("myuser:myuser".getBytes()); // закодували наш header (комбінація логін і пароль з basic) в base64. Тобто створимо об'єкт authenticationHeader
        requestHeaders.add("Authorization", authHeader);  // передати назву нашого header, тобто headerName (Authorization) і сам закодований header
        HttpEntity httpEntity = new HttpEntity(requestHeaders); // HttpEntity утримує в собі два об’єкти: реальний request-body який ми надсилаємо і header. Можна передати лише header(requestHeaders).

        ResponseEntity<MovieDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MovieDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {  // якщо статус responseEntity 200 і body наявний, то
            return responseEntity.getBody().getMovies();     // метод getBody() отримує весь MovieDTO. З body (MovieDTO) цього responseEntity отримуємо всі movies.
        } else {
            throw new RuntimeException("Error retrieving movies");  // інакше кидаємо помилку
        }

    }

    @Override
    public void createMovie(Movie movie) {
        RestTemplate restTemplate = new RestTemplate();  // з допомогою RestTemplate робимо request на інший мікросервіс.
        String url = "http://localhost:8081/movies/1";  // опис URL  (/1 є бо directorId потрібен)

        HttpHeaders requestHeaders = new HttpHeaders();  // формуємо наші header. Тобто створимо об'єкт requestHeaders
        String authHeader = "Basic " + Base64.encodeBase64String("myuser:myuser".getBytes()); // закодували наш header (комбінація логін і пароль з basic) в base64. Тобто створимо об'єкт authenticationHeader
        requestHeaders.add("Authorization", authHeader);  // передати назву нашого header, тобто headerName (Authorization) і сам закодований header

            HttpEntity httpEntity = new HttpEntity(movie, requestHeaders); // передати треба header s body. Головне, щоб на іншому мікросервісі був об'єкт, який прийме той тип, який прийде
        try {
            ResponseEntity<Movie> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Movie.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                if (responseEntity.getBody() != null) {
                    return;
                }
            } else {
                throw new RuntimeException("Error creating movie");  // інакше кидаємо помилку
            }
        } catch (Exception ex){
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }
}
