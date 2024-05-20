package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK5_6_8Data;
import com.example.rjdtask1.model.PK7Data;
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
public class PK7Repository {

    private final DataSource dataSource;

    private final String SELECT = "select\n" +
            "\tlist.sob,\n" +
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
            "\t\t\tap.sob,\n" +
            "\t\t\tp.masbr\n" +
            "\t\tfrom\n" +
            "\t\t\t $tableName p\n" +
            "\t\tleft join \n" +
            "\t\t\t $abdName ap using(num)\n" +
            "\t\twhere p.mesto = '7'\t\n" +
            "\t\t\tand p.vid_k = '1') list\n" +
            "group by list.sob\n" +
            "order by list.sob desc;";

    public List<PK7Data> getQueryResult(String year) {
        List<PK7Data> data = new ArrayList<>();
        //System.out.println(PEREPIS_2021);

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

    private PK7Data extract(ResultSet set) throws SQLException {
        return new PK7Data(
                set.getString(1),
                set.getInt(2),
                set.getInt(3),
                set.getInt(4),
                set.getInt(5),
                set.getInt(6),
                set.getInt(7),
                set.getInt(8),
                set.getInt(9)
        );
    }
}
