package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
        MealsUtil.secondMeals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return repository.get(mealId).getUserId() == userId && repository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("GET user id {}", repository.get(mealId).getUserId());
        return repository.get(mealId).getUserId() != null && repository.get(mealId).getUserId() != userId ? null : repository.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId) == null ? new ArrayList<>() : repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}