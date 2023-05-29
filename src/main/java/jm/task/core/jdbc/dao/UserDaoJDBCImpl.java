package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    private Statement state;

    {
        try {
            state = getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try {
            state.executeUpdate("CREATE TABLE IF NOT EXISTS Users(id MEDIUMINT not null AUTO_INCREMENT,name VARCHAR(30)not null,lastName Varchar(30),age MEDIUMINT,Primary Key (id)); ");
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        try {
            state.executeUpdate("DROP TABLE users");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO users (`name`, `lastName`, `age`) VALUES (?, ?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {

        }
    }


    public void removeUserById(long id) {
        try {
            state.executeUpdate("DELETE FROM Users WHERE id='id';");
        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        List<User> userList = null;
        try {
            userList = new ArrayList<>();
            ResultSet rs = state.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {

        }
        return userList;
    }


    public void cleanUsersTable() {
        try {
            state.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {

        }
    }


}

