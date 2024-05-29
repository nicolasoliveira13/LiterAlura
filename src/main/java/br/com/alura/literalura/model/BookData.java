package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(String title,
                       @JsonAlias("download_count")
                       Integer downloadCount,
                       String languages){

    @Override
    public String toString() {
        return "Title: " + title +
                ", Download Count: " + downloadCount +
                ", Launguage: " + languages;
    }
}
