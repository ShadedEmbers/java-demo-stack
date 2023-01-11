package robert.dev.demoapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {

  @Test
  public void testMovie() {

    Movie movie = new Movie(
            "1",
            "title",
            "description"
    );

    assertEquals("1", movie.id());
    assertEquals("title", movie.title());
    assertEquals("description", movie.description());
  }
}
