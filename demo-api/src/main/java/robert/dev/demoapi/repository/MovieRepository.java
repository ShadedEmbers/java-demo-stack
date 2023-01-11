package robert.dev.demoapi.repository;

import org.springframework.stereotype.Repository;
import robert.dev.demoapi.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieRepository {

  List<Movie> movies = new ArrayList<>();

  public MovieRepository() {
    movies.add(new Movie("1", "Wolf of Wall Street", "some description"));
  };

  public List<Movie> findAll() {
    return movies;
  };

  public Movie findById(String id) {
    return movies.stream().filter(movie -> movie.id().equals(id)).findFirst().orElse(null);
  };

  public Movie addOne(Movie movie) {
    Movie newMovie = new Movie(UUID.randomUUID().toString(), movie.title(), movie.description());
    movies.add(newMovie);
    return newMovie;
  };

  public Movie updateOne(String id, Movie movie) {
    Movie updatedMovie = new Movie(id, movie.title(), movie.description());
    int movieIndex = movies.indexOf(movies.stream().filter(m -> m.id().equals(id)).findFirst().orElse(null));

    if (movieIndex != -1) {
      movies.set(movieIndex, updatedMovie);
    }

    return updatedMovie;
  }

  public void deleteOne(String id) {
    movies.removeIf(movie -> movie.id().equals(id));
  };
};
