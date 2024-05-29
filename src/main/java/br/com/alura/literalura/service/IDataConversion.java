package br.com.alura.literalura.service;

public interface IDataConversion {
    <T> T getData(String json, Class<T> classe);
}
