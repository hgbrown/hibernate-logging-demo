package dev.hbrown.hibernateloggingdemo;

import dev.hbrown.hibernateloggingdemo.model.Author;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestLogging {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private EntityManager em;

  @Test
  void sanityTest() {
    assertThat(em).isNotNull();
  }

  @Test
  @Transactional
  void selectAuthors() {
    log.info("... selectAuthors ...");

    final List<Author> authors = em.createQuery("SELECT a FROM Author a", Author.class).getResultList();

    for (Author author : authors) {
      log.info(author + " has written " + author.getBooks().size() + " books.");
    }

  }
}
