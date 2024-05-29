package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long> {
    List<Books> findByLanguageContaining(String languages);
}
