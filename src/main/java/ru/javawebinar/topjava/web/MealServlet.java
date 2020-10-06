package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.CRUD.Crud;
import ru.javawebinar.topjava.CRUD.MealMemoryCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int CALORIES_DAY_LIMIT = 2000;

    private final Crud<Meal> mealCrud;

    public MealServlet() {
        mealCrud = new MealMemoryCrud();

        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealCrud.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getAction(request);

        switch (action.toLowerCase()) {
            case "add":
                Meal newMeal = new Meal(LocalDateTime.now(), "", 0);
                log.debug("Adding {}", newMeal);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "update":
                Meal meal = mealCrud.get(Long.parseLong(request.getParameter("mealId")));
                log.debug("Updating {}", meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("Delete mealId={}", Long.parseLong(request.getParameter("mealId")));
                mealCrud.delete(Long.parseLong(request.getParameter("mealId")));
                response.sendRedirect("meals");
                break;
            default:
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealCrud.getList(), LocalTime.MIN,
                        LocalTime.MAX, CALORIES_DAY_LIMIT));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                Long.parseLong(request.getParameter("mealId")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        String action = getAction(request);

        switch (action.toLowerCase()) {
            case "add":
                log.debug("Add {}", meal);
                mealCrud.add(meal);
                break;
            case "update":
                log.debug("Update {}", meal);
                mealCrud.update(meal);
                break;
            default:
                log.debug("Error action '{}' in doPost", action);
        }

        response.sendRedirect("meals");
    }

    private String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        return action != null ? action : "default";
    }
}