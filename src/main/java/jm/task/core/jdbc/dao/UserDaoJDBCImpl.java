package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    static Connection connection;
    static private List<User> userList;
    //Queries fields
    static private String createTableQuery = """
            CREATE TABLE IF NOT EXISTS user (
                id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                name VARCHAR(30) NOT NULL,
                lastname VARCHAR(40) NOT NULL,
                age TINYINT NULL)""";
    static private String dropTableQuery = "DROP TABLE IF EXISTS user";
    static private String saveUserQuery = "INSERT INTO user (name, lastname, age) VALUES(?, ?, ?)";
    static private String removeUserQuery = "DELETE FROM user WHERE id = ?";
    static private String getAllUsersQuery = "SELECT id, name, lastname, age FROM user";
    static private String cleanTableQuery = "TRUNCATE TABLE user";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        connection = Util.getConnection();
        try (Statement state = connection.createStatement()) {
            state.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        connection = Util.getConnection();
        try (Statement state = connection.createStatement()) {
            state.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = Util.getConnection();
        try (PreparedStatement pState = connection.prepareStatement(saveUserQuery)) {
            pState.setString(1, name);
            pState.setString(2, lastName);
            pState.setByte(3, age);
            pState.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        connection = Util.getConnection();
        try (PreparedStatement pState = connection.prepareStatement(removeUserQuery)) {
            pState.setLong(1, id);
            pState.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        userList = new ArrayList<>();
        connection = Util.getConnection();
        try (Statement state = connection.createStatement()) {
            ResultSet resultSet = state.executeQuery(getAllUsersQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        connection = Util.getConnection();
        try (Statement state = connection.createStatement()) {
            state.executeUpdate(cleanTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}