package com.myrest.restpractice.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JsonRepository<T> {

    List<T> getListFromJson();

    void putListToJson(List<T> objList);
}

