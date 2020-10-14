package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, 1));
        MealsUtil.secondMeals.forEach(m -> save(m, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealRepo = repository.computeIfAbsent(userId, key -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealRepo.put(meal.getId(), meal);
            repository.put(userId, mealRepo);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealRepo.computeIfPresent(userId, (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> mealRepo = repository.get(userId);
        return mealRepo != null && mealRepo.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> mealRepo = repository.get(userId);
        return mealRepo == null ? null : mealRepo.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getFilteredByPredicate(userId, meal -> DateTimeUtil.isBetweenOpen(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getFilteredByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealRepo = repository.get(userId);
        return mealRepo == null ? new ArrayList<>() : mealRepo.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}