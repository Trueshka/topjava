package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.mealcrud.CrudInterface;
import ru.javawebinar.topjava.mealcrud.MealMemoryCrudInterface;
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
import java.time.temporal.ChronoUnit;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final int dayMaxCalories = 2000;

    private CrudInterface<Meal> mealCrud;

    public void init() {
        mealCrud = new MealMemoryCrudInterface();

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
                Meal newMeal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                log.debug("Adding {}", newMeal);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "update":
                Meal meal = mealCrud.get(getMealId(request));
                log.debug("Updating {}", meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("Delete mealId={}", getMealId(request));
                mealCrud.delete(getMealId(request));
                response.sendRedirect("meals");
                break;
            default:
                request.setAttribute("meals", MealsUtil.filteredByStreams(mealCrud.getList(), LocalTime.MIN,
                        LocalTime.MAX, dayMaxCalories));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                getMealId(request),
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

    private long getMealId(HttpServletRequest request) {
        return Long.parseLong(request.getParameter("mealId"));
    }

}