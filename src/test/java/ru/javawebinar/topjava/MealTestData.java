package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int NOT_FOUND_MEAL = 10;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static final List<Meal> userMealAll30 = Arrays.asList(
            new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "User", 500),
            new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "User", 1000),
            new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "User", 500)
    );

    public static final Meal userMeal30 = new Meal(USER_MEAL_ID,
            LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "User", 500);

    public static final List<Meal> userMeals = Arrays.asList(

            new Meal(USER_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "User", 410),
            new Meal(USER_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "User", 500),
            new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "User", 1000),
            new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "User", 100),
            new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "User", 500),
            new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "User", 1000),
            new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "User", 500)
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0),
                "Meal", 1555);
    }

    public static Meal getOld() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "User old", 1555);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal30);
        updated.setDateTime(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0));
        updated.setDescription("Второй завтрак");
        updated.setCalories(250);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}