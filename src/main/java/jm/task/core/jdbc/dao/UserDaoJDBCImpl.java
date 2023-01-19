package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    Logger logger = Logger.getLogger("UserDaoJDBCImpl log");

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        System.out.println("Creating table... ");
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE `" + Util.getUtil().getURL() + "`.`UsersTable` (" +
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
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        System.out.println("Dropping table... ");
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE `" + Util.getUtil().getURL() + "`.`UsersTable`";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Table dropped successfully.");
        } catch (SQLException e) {
            logger.warning("Dropping of table was NOT successfully. Table does not exists");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO `" + Util.getUtil().getURL() + "`.`UsersTable` " +
                "(`name`, `lastName`, `age`) " +
                "VALUES ('" + name + "', '" + lastName + "', '" + age + "');";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            logger.warning("Adding of User \"" + name + "\" was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM `" + Util.getUtil().getURL() + "`.`UsersTable` WHERE (`id` = '" + id +"');";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Deleting of User id №" + id + " was successfully.");
        } catch (SQLException e) {
            logger.warning("Deleting of User id №" + id + " was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        List<User> result = new ArrayList<>();
        Statement statement = null;
        String sql = "SELECT * FROM " + Util.getUtil().getURL() + ".UsersTable";
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
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getUtil().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM `" + Util.getUtil().getURL() + "`.`UsersTable`";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Clearing of UserTable was successfully.");
        } catch (SQLException e) {
            logger.warning("Clearing of UserTable was NOT successfully.");
        } finally {
            if (preparedStatement != null) {preparedStatement.close();}
            if (connection != null) {Util.getUtil().closeConnection(connection);}
        }
    }
}
