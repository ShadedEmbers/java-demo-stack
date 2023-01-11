package robert.dev.demoapi.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import robert.dev.demoapi.model.Movie;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieControllerTest {

  @Autowired
  TestRestTemplate restTemplate;

  private ResponseEntity<List<Movie>> findAllMovies() {
    return restTemplate.exchange("/movies",
            HttpMethod.GET,
            new HttpEntity<>(null),
            new ParameterizedTypeReference<List<Movie>>() {});
  }

  @Test
  @Order(1)
  void testFindAll() {
    ResponseEntity<List<Movie>> response = findAllMovies();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(1, Objects.requireNonNull(response.getBody()).size());
  }

  @Test
  @Order(2)
  void findById() {
    Movie expectedMovie = Objects.requireNonNull(findAllMovies().getBody()).get(0);
    Movie actualMovie = restTemplate.getForObject("/movies/" + expectedMovie.id(), Movie.class);
    assertEquals(expectedMovie.id(), actualMovie.id());
    assertEquals(expectedMovie.title(), actualMovie.title());
    assertEquals(expectedMovie.description(), actualMovie.description());
  }

  @Test
  @Order(3)
  void create() {
    // create a new movie without passing an ID
    Movie newMovie = new Movie(
            null,
            "title",
            "description"
    );
    ResponseEntity<Movie> response = restTemplate.postForEntity("/movies/create", newMovie, Movie.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  @Order(4)
  void update () {
    Movie expectedMovie = Objects.requireNonNull(findAllMovies().getBody()).get(0);
    Movie updatedMovie = new Movie(
            expectedMovie.id(),
            "updated title",
            "updated description"
    );
    restTemplate.put("/movies/update/" + expectedMovie.id(), updatedMovie);
    Movie actualMovie = restTemplate.getForObject("/movies/" + expectedMovie.id(), Movie.class);
    assertEquals(expectedMovie.id(), actualMovie.id());
    assertEquals(updatedMovie.title(), actualMovie.title());
    assertEquals(updatedMovie.description(), actualMovie.description());
  }

  @Test
  @Order(5)
  void delete() {
    Movie expectedMovie = Objects.requireNonNull(findAllMovies().getBody()).get(0);
    restTemplate.delete("/movies/delete/" + expectedMovie.id());
    ResponseEntity<List<Movie>> response = findAllMovies();
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

}


