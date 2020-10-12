package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private static int userId = 1;

    public static int authUserId() {
        return userId;
    }

    public static void setAuthUserId(int userId) {
        log.info("User id{}", userId);
        SecurityUtil.userId = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}