package ru.javawebinar.topjava.CRUD;

import java.util.List;

public interface Crud<T> {
    T add(T object);

    T update(T object);

    void delete(long id);

    List<T> getList();

    T get(long id);
}