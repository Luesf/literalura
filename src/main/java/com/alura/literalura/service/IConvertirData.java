package com.alura.literalura.service;

public interface IConvertirData {
    <T> T getData(String json, Class<T> clase);
}
