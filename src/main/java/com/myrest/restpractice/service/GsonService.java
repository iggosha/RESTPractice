package com.myrest.restpractice.service;

import java.util.List;

public interface GsonService<T> {

    List<T> getListFromJson();

    void putListToJson(List<T> objList);
}

