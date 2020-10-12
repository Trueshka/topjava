package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public interface MealRepository {
    // null if not found, when updated
    Meal save( Meal meal);

    // false if not found
    boolean delete(int mealId);

    // null if not found
    Meal get(int mealId);
    Collection<Meal> getAll(int userId);

    Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate);
}
