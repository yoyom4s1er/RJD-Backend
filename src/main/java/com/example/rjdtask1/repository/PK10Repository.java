package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK10Data;
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
public class PK10Repository {

    private final DataSource dataSource;

    private final String SELECT =
            "select\n" +
            "\tp.adm,\n" +
            "\tp.num,\n" +
            "\tp.masbr,\n" +
            "\tp.kontrol\n" +
            "from\n" +
            "\t perkon.per2020 p\n" +
            "where p.prinadl = '1'\t\n" +
            "\tand p.vid_k = '1'";

    public List<PK10Data> getQueryResult(String year) {
        List<PK10Data> data = new ArrayList<>();

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
    private PK10Data extract(ResultSet set) throws SQLException {
        return new PK10Data(
                set.getString(1),
                set.getString(2),
                set.getString(3),
                set.getBoolean(4)
        );
    }
}
