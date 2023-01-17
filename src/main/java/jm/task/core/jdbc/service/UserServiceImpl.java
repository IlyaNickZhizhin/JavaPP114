package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends Util implements UserService {
    public void createUsersTable() throws SQLException {
        Connection connection = getConnection();
        System.out.println("Creating table... ");
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE `" + super.URL + "`.`UsersTable` (" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(45) NOT NULL," +
                "  `lastName` VARCHAR(45) NOT NULL," +
                "  `age` INT NULL," +
                "  PRIMARY KEY (`id`));";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println("Creation of table was unsuccessfully.\n Table is already exists");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {connection.close();}
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = getConnection();
        System.out.println("Dropping table... ");
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE `" + super.URL + "`.`UsersTable`";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Table dropped successfully.");
        } catch (SQLException e) {
            System.out.println("Dropping of table was NOT successfully. Table does not exists");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {connection.close();}
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO `" + super.URL + "`.`UsersTable` " +
                "(`name`, `lastName`, `age`) " +
                "VALUES ('" + name + "', '" + lastName + "', '" + age + "');";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Adding of User \"" + name + "\" was successfully.");
        } catch (SQLException e) {
            System.out.println("Adding of User \"" + name + "\" was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {connection.close();}
        }
    }
    public void removeUserById(long id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM `" + super.URL + "`.`UsersTable` WHERE (`id` = '" + id +"');";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Deleting of User id №" + id + " was successfully.");
        } catch (SQLException e) {
            System.out.println("Deleting of User id №" + id + " was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {connection.close();}
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = getConnection();
        List<User> result = new ArrayList<>();
        Statement statement = null;
        String sql = "SELECT * FROM " + super.URL + ".UsersTable";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                result.add(user);
            }
            System.out.println("Creating collection of Users from UserTable was successfully.");
        } catch (SQLException e) {
            System.out.println("Creating collection of Users from UserTable was NOT successfully.");
        } finally {
            if (statement != null) {statement.close();}
            if (connection != null) {connection.close();}
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM `" + super.URL + "`.`UsersTable`";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Clearing of UserTable was successfully.");
        } catch (SQLException e) {
            System.out.println("Clearing of UserTable was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {connection.close();}
        }
    }
}
