package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASS = "ROOT";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";
    private static final String HBM2DDL = "update";
    private static final String SHOW_SQL = "true";
    private static final String CONNECTION_POOL_TIMEOUT = "0";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static SessionFactory getSession() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, Driver.class.getCanonicalName());
        properties.setProperty(Environment.URL, URL);
        properties.setProperty(Environment.USER, USER);
        properties.setProperty(Environment.PASS, PASS);
        properties.setProperty(Environment.DIALECT, DIALECT);
        properties.setProperty(Environment.HBM2DDL_AUTO, HBM2DDL);
        properties.setProperty(Environment.SHOW_SQL, SHOW_SQL);
        properties.setProperty(Environment.C3P0_TIMEOUT, CONNECTION_POOL_TIMEOUT);

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;

    }
}
