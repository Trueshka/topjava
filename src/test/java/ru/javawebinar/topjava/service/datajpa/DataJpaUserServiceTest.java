package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getUserWithMeals() {
        User user = service.getUserWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
    }

    @Test
    public void getUserWithMealsNotFound() {
        assertThrows(NotFoundException.class, () -> service.getUserWithMeals(NOT_FOUND));
    }

    @Test
    public void getUserWithMealsMealEmpty() {
        User user = service.getUserWithMeals(ADMIN_EMPTY_MEAL_ID);
        USER_MATCHER.assertMatch(user, adminEmpty);
        MEAL_MATCHER.assertMatch(user.getMeals());
    }
}
