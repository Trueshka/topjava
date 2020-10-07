package ru.javawebinar.topjava.mealcrud;

import java.util.List;

public interface CrudInterface<T> {
    T add(T object);

    T update(T object);

    void delete(long id);

    List<T> getList();

    T get(long id);
}