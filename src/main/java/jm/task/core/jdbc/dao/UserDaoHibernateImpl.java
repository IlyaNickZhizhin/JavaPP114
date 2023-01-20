package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    Logger logger = Logger.getLogger("UserDaoHibernateImpl logger");

    public UserDaoHibernateImpl() {
    }

    @Override
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

    @Override
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

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getUtil().getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            logger.warning("Adding of User " + name + " was NOT successfully." + e.getMessage());
        } finally {
//            Util.getUtil().getSessionFactory().close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getUtil().getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User" +
                    "where id = '" + id + "'").executeUpdate();
            System.out.println("Deleting of User id №" + id + " was successfully.");
        } catch (Exception e) {
            logger.warning("Deleting of User id №" + id + " was NOT successfully.");
        } finally {
//            Util.getUtil().getSessionFactory().close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.getUtil().getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            list = session.createQuery("from User").getResultList();
            System.out.println("Creating collection of Users from UserTable was successfully.");
            return list;
        } catch (Exception e) {
            logger.warning("Creating collection of Users from UserTable was NOT successfully.");
        } finally {
//            Util.getUtil().getSessionFactory().close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getUtil().getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            System.out.println("Clearing of UserTable was successfully.");
        } catch (Exception e) {
            logger.warning("Clearing of UserTable was NOT successfully.");
        } finally {
//            Util.getUtil().getSessionFactory().close();
        }
    }
}
