package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Ilya", "Zhizhin", (byte) 36);
        userService.saveUser("Irina", "Zhizhina", (byte) 39);
        userService.saveUser("Stepan", "Pozdeev", (byte) 13);
        userService.saveUser("Michail", "Pozdeev", (byte) 10);
        userService.saveUser("Mark", "Zhizhin", (byte) 10);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

