package smarthome.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import smarthome.models.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Class of type SimulationParameters
 */
public class SimulationParameters {
    private ArrayList<User> users = new ArrayList<>();
    private Date date;
    private String location;
    private User loggedAs;

    /**
     * Accessor for users
     *
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Accessor for Date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Accessor for location
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Accessor for loggedAs
     *
     * @return loggedAs
     */
    public User getLoggedAs() {
        return loggedAs;
    }

    /**
     * Class constructor
     *
     * @param app      Javalin object
     */
    public SimulationParameters(Javalin app) {
        app.post("/api/add-user", ctx -> {
            String name = ctx.formParam("name");
            String role = ctx.formParam("role");
            addUser(name, role);
            ctx.json(users);
        });

        app.post("/api/delete-user", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            removeUser(id);
            ctx.json(users);
        });
    }

    /**
     * Class constructor that doesn't expose an API.
     */
    public SimulationParameters() {
    }



    /**
     * Adds a users to the arraylist of users if user has a valid role
     *
     * @param name  Name of the new user to add
     * @param role  Role of the new user to add
     */
    public void addUser(String name, String role) {
        if(role.equals("Parent") || role.equals("Child") || role.equals("Guest") || role.equals("Stranger"))
            users.add(new User(name, role));
    }

    /**
     * Removes a user
     *
     * @param id    The id of the user to be removed
     */
    public void removeUser(int id) {
        User userToRemove = null;
        for (User user : users) {
            if (user.getId() == id) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
        }
    }

    /**
     * Get a user
     *
     * @param id    The id of the user to be returned
     */
    public User getUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Changes the date of the simulation
     *
     * @param date  The new Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Changes the location of the simulation
     *
     * @param location  The new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Logs in as a user
     *
     * @param id The ID of the user to be logged in as
     */
    public void logInAs(int id) {
        for(User user : users) {
            if (user.getId() == id)
                loggedAs = user;
        }
    }

}
