package com.myrest.restpractice.repository;

import java.util.List;

public interface JsonRepository<T> {

    List<T> getListFromJson();

    void putListToJson(List<T> objList);
}

