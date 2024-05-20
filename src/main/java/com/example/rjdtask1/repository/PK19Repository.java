package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK10Data;
import com.example.rjdtask1.model.PK19Data;
import com.example.rjdtask1.model.PK5_6_8Data;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PK19Repository {

    private final DataSource dataSource;

    private final String SELECT = "select\n" +
            "\tabd.sob,\n" +
            "\tp.prinadl,\n" +
            "\tabd.y_postr,\n" +
            "\tp.num\n" +
            "from\n" +
            "\t $tableName p\n" +
            "left join \n" +
            "\t $abdName abd using(num)\n" +
            "where p.prinadl  = '1';";

    private final String SELECT_BY_STK = "select\n" +
            "\tabd.sob,\n" +
            "\tp.prinadl,\n" +
            "\tabd.y_postr,\n" +
            "\tp.num\n" +
            "from\n" +
            "\t $tableName p\n" +
            "left join \n" +
            "\t $abdName abd using(num)\n" +
            "where \n" +
            "\tp.prinadl  = '1'\n" +
            "\tand TO_NUMBER(p.masbr, '99') < 10;";

    private final String SELECT_BY_KTK = "select\n" +
            "\tabd.sob,\n" +
            "\tp.prinadl,\n" +
            "\tabd.y_postr,\n" +
            "\tp.num\n" +
            "from\n" +
            "\t $tableName p\n" +
            "left join \n" +
            "\t $abdName abd using(num)\n" +
            "where \n" +
            "\tp.prinadl  = '1'\n" +
            "\tand TO_NUMBER(p.masbr, '99') >= 10;";

    public List<PK19Data> getQueryResult(String year) {
        List<PK19Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK19Data> selectBySTK(String year) {
        List<PK19Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT_BY_STK
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK19Data> selectByKTK(String year) {
        List<PK19Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT_BY_KTK
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
    private PK19Data extract(ResultSet set) throws SQLException {
        return new PK19Data(
                set.getString(1),
                set.getString(2),
                set.getString(3)
        );
    }
}
