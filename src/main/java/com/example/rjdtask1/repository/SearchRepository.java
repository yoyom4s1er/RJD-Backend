package com.example.rjdtask1.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SearchRepository {

    private final DataSource dataSource;
    private final String SEARCH_TABLE_NAMES = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema='public' " +
            "AND table_type='BASE TABLE' " +
            "AND table_name LIKE ? " +
            "ORDER BY table_name ";

    public List<String> searchTableNames(String query) {
        List<String> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SEARCH_TABLE_NAMES);
            preparedStatement.setString(1, query + "%");

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getString("table_name"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
