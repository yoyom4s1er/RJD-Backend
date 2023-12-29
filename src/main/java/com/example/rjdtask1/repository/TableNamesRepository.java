package com.example.rjdtask1.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TableNamesRepository {

    private final DataSource dataSource;

    private final String SELECT_DIRECTORY_NAMES = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema='public'" +
            "AND table_type='BASE TABLE'" +
            "AND table_name LIKE 'd%'" +
            "ORDER BY table_name ASC";

    private final String SELECT_CLASSIFIER_NAMES = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema='public'" +
            "AND table_type='BASE TABLE'" +
            "AND table_name LIKE 'k%'" +
            "ORDER BY table_name ASC";

    public List<String> getDirectoryTables() {
        List<String> names = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT_DIRECTORY_NAMES);

            while (result.next()) {
                names.add(result.getString("table_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return names;
    }

    public List<String> getClassifierTables() {
        List<String> names = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT_CLASSIFIER_NAMES);

            while (result.next()) {
                names.add(result.getString("table_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return names;
    }
}
