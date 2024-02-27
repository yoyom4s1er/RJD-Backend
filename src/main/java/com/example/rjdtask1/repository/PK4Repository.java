package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK4Data;
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
public class PK4Repository {

    private final DataSource dataSource;

    private final String SELECT = "select\n" +
            "\tlist.adm_per,\n" +
            "\tlist.mesto,\n" +
            "\tlist.sob,\n" +
            "\tcount(*)\n" +
            "from\n" +
            "\t(\n" +
            "\tselect distinct\n" +
            "\t\t\tp.mesto,\n" +
            "\t\t\tp.adm as adm_per,\n" +
            "\t\t\tp.num,\n" +
            "\t\t\tcase when ap.sob is null then '0' else ap.sob end as sob\n" +
            "\t\tfrom\n" +
            "\t\t\t $tableName p\n" +
            "\t\tleft join \n" +
            "\t\t\t $abdName ap using(num)\n" +
            "where p.mesto <> '9'" +
            "and p.prinadl = '1'" +
            "\t) list\n" +
            "group by list.adm_per, list.mesto, list.sob\n" +
            "order by list.adm_per desc, list.mesto desc, list.sob desc;";

    private final String SELECT_BY_STK = "select\n" +
            "\tlist.adm_per,\n" +
            "\tlist.mesto,\n" +
            "\tlist.sob,\n" +
            "\tcount(*)\n" +
            "from\n" +
            "\t(\n" +
            "\tselect distinct\n" +
            "\t\t\tp.mesto,\n" +
            "\t\t\tp.adm as adm_per,\n" +
            "\t\t\tp.num,\n" +
            "\t\t\tcase when ap.sob is null then '0' else ap.sob end as sob\n" +
            "\t\tfrom\n" +
            "\t\t\t $tableName p\n" +
            "\t\tleft join \n" +
            "\t\t\t $abdName ap using(num)\n" +
            "where p.mesto <> '9'" +
            "and p.prinadl = '1'" +
            "and TO_NUMBER(p.masbr, '99') < 10" +
            "\t) list\n" +
            "group by list.adm_per, list.mesto, list.sob\n" +
            "order by list.adm_per desc, list.mesto desc, list.sob desc;";

    private final String SELECT_BY_KTK = "select\n" +
            "\tlist.adm_per,\n" +
            "\tlist.mesto,\n" +
            "\tlist.sob,\n" +
            "\tcount(*)\n" +
            "from\n" +
            "\t(\n" +
            "\tselect distinct\n" +
            "\t\t\tp.mesto,\n" +
            "\t\t\tp.adm as adm_per,\n" +
            "\t\t\tp.num,\n" +
            "\t\t\tcase when ap.sob is null then '0' else ap.sob end as sob\n" +
            "\t\tfrom\n" +
            "\t\t\t $tableName p\n" +
            "\t\tleft join \n" +
            "\t\t\t $abdName ap using(num)\n" +
            "where p.mesto <> '9'" +
            "and p.prinadl = '1'" +
            "and TO_NUMBER(p.masbr, '99') >= 10" +
            "\t) list\n" +
            "group by list.adm_per, list.mesto, list.sob\n" +
            "order by list.adm_per desc, list.mesto desc, list.sob desc;";

    public List<PK4Data> getQueryResult(String year) {
        List<PK4Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(parseRow(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK4Data> selectBySTK(String year) {
        List<PK4Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(parseRow(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK4Data> selectByKTK(String year) {
        List<PK4Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(parseRow(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private PK4Data parseRow(ResultSet result) throws SQLException {
        return new PK4Data(
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getInt(4)
        );
    }
}
