package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete( int mealId) {
        checkNotFoundWithId(repository.delete(mealId), mealId);
    }

    public Meal get(int mealId) {
        return checkNotFoundWithId(repository.get( mealId), mealId);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return (List<Meal>) repository.getFiltered(userId, startDate, endDate);
    }

    public void update( Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}