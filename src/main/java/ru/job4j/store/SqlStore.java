package ru.job4j.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Account;
import ru.job4j.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class SqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();
    private final Logger log = LoggerFactory.getLogger(SqlStore.class);

    private SqlStore() {
        Properties cfg = new Properties();
        try(BufferedReader reader = new BufferedReader(new FileReader("db.properties"))) {
            cfg.load(reader);
        } catch (IOException e) {
            log.error("Exception in SqlStore:", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            log.error("Exception in SqlStore:", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private static final class Lazy {
        private static final Store INST = new SqlStore();
    }

    @Override
    public Account findTheAccount(String phone) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from account where phone = ?")) {
            statement.setString(1, phone);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("Пользователь по номеру телефона не найден");
                }
                return new Account(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("email"), resultSet.getString("phone"));
            }
        } catch (SQLException ex) {
            log.error("Exception in SqlStore:", ex);
        }
        return null;
    }

    @Override
    public List<Ticket> findTickets(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "select * from ticket where session_id = ?")) {
            statement.setInt(1, sessionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(resultSet.getInt("id"), resultSet.getInt("session_id"),
                            resultSet.getInt("row"), resultSet.getInt("cell"),
                            resultSet.getInt("account_id")));
                }
            }
        } catch (SQLException ex) {
            log.error("Exception in SqlStore:", ex);
        }
        return tickets;
    }

    @Override
    public boolean addTickets(List<Ticket> tickets) {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into ticket(session_id, row, cell, account_id) VALUES (?,?,?,?)")) {
            connection.setAutoCommit(false);
            for (Ticket ticket : tickets) {
                statement.setInt(1, ticket.getSessionId());
                statement.setInt(2, ticket.getRow());
                statement.setInt(3, ticket.getCell());
                statement.setInt(4, ticket.getAccountId());
                statement.execute();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                }
            }
            connection.commit();
        }  catch (SQLException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Ошибка при покупке билета(билетов).");
        }
        return true;
    }

}
