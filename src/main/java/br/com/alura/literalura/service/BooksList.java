package br.com.alura.literalura.service;

import br.com.alura.literalura.model.Books;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksList {
    public List<Books> results;

    public List<Books> getResults() {
        return results;
    }

    public void setResults(List<Books> results) {
        this.results = results;
    }
}
