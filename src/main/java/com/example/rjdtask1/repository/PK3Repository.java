package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK3Data;
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
public class PK3Repository {

    private final DataSource dataSource;

    private final String SELECT = "select\n" +
            "\tlist.adm_per,\n" +
            "\tlist.mesto,\n" +
            "\tcount(*),\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 3 and TO_NUMBER(list.masbr, '99') < 5) as \"3\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 5 and TO_NUMBER(list.masbr, '99') < 10) as \"5\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 10 and TO_NUMBER(list.masbr, '99') < 20) as \"10\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 20 and TO_NUMBER(list.masbr, '99') < 24) as \"20\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') = 24) as \"24\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 25 and TO_NUMBER(list.masbr, '99') < 30) as \"25\",\n" +
            "\tcount(*) FILTER (WHERE TO_NUMBER(list.masbr, '99') >= 30) as \">=30\"\n" +
            "from\n" +
            "\t(select distinct\n" +
            "\t\t\tp.mesto,\n" +
            "\t\t\tp.adm as adm_per,\n" +
            "\t\t\tp.num,\n" +
            "\t\t\tap.sob,\n" +
            "\t\t\tp.masbr\n" +
            "\t\tfrom\n" +
            "\t\t\t $tableName p\n" +
            "\t\tleft join \n" +
            "\t\t\t $abdName ap using(num)\n" +
            "where p.mesto <> '9'" +
            "\t) list\n" +
            "group by rollup(list.adm_per, list.mesto)\n" +
            "order by list.adm_per desc, list.mesto desc;";

    public List<PK3Data> getQueryResult(String year) {
        List<PK3Data> data = new ArrayList<>();

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

    private PK3Data parseRow(ResultSet result) throws SQLException {
        return new PK3Data(
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getInt(4),
                result.getInt(5),
                result.getInt(6),
                result.getInt(7),
                result.getInt(8),
                result.getInt(9),
                result.getInt(10)
                );
    }
}
