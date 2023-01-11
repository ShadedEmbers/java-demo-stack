package robert.dev.demoapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.dev.demoapi.model.Movie;
import robert.dev.demoapi.repository.MovieRepository;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

  private final MovieRepository repository;

  public MovieController(MovieRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Movie> findAll() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Movie findById(@PathVariable String id) {
    return repository.findById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
  public Movie create(@RequestBody Movie movie) {
    return repository.addOne(movie);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/update/{id}")
  public Movie update(@PathVariable String id, @RequestBody Movie movie) {
    return repository.updateOne(id, movie);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable String id) {
    repository.deleteOne(id);
  }
}
