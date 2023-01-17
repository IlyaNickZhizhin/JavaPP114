package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService util = new UserServiceImpl();
        util.createUsersTable();
        UserService util2 = new UserServiceImpl();
        util2.saveUser("Ivan", "Ivanov", (byte) 18);
        UserService util3 = new UserServiceImpl();
        util3.saveUser("Ivan", "Ivanov", (byte) 18);
        UserService util4 = new UserServiceImpl();
        util4.saveUser("Ivan", "Ivanov", (byte) 18);
        UserService util5 = new UserServiceImpl();
        util5.saveUser("Ivan", "Ivanov", (byte) 18);
        UserService util6 = new UserServiceImpl();
        util6.saveUser("Ivan", "Ivanov", (byte) 18);
        UserService util7 = new UserServiceImpl();
        System.out.println(util7.getAllUsers());
        UserService util8 = new UserServiceImpl();
        util8.cleanUsersTable();

    }
}

