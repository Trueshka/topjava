package ru.javawebinar.topjava.CRUD;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealMemoryCrud implements Crud<Meal> {
    private final AtomicLong counter = new AtomicLong(0);
    private final Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

    @Override
    public Meal add(Meal meal) {
        Meal newMeal = new Meal(counter.getAndIncrement(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealMap.put(newMeal.getId(), newMeal);
        return newMeal;
    }

    @Override
    public Meal update(Meal meal) {
        return mealMap.replace(meal.getId(), meal) == null ? null : meal;
    }

    @Override
    public void delete(long mealId) {
        mealMap.remove(mealId);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal get(long mealId) {
        return mealMap.get(mealId);
    }
}