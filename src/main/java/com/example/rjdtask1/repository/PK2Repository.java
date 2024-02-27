package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK2Data;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PK2Repository {

    private final DataSource dataSource;
    private final String SELECT = "select\n" +
            "\tlist.vid_k,\n" +
            "\tcase when adm.\"SNAME\" is null then 'НеАБД' else adm.\"SNAME\" end as sname,\n" +
            "\tlist.prinadl,\n" +
            "\tcount(*),\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 3 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 5) as \"3\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 5 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 10) as \"5\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 10 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 20) as \"10\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 20 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 24) as \"20\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') = 24) as \"24\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 25 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 30) as \"25\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 30) as \">=30\"\n" +
            "from\n" +
            "\t(\n" +
            "\tselect distinct\n" +
            "\t\tp.vid_k,\n" +
            "\t\tcase when ap.sob is null then null else p.adm end as adm_per,\n" +
            "\t\tp.num,\n" +
            "\t\tap.sob,\n" +
            "\t\tp.masbr,\n" +
            "\t\tp.prinadl\n" +
            "\tfrom\n" +
            "\t\t $tableName p\n" +
            "\tleft join \n" +
            "\t\t $abdName ap using(num)) list\n" +
            "LEFT JOIN local.adm as adm\n" +
            "\tON TO_NUMBER(list.adm_per, '999') = adm.kod\n" +
            "group by list.vid_k, adm.\"SNAME\", list.prinadl\n" +
            "order by list.vid_k desc, adm.\"SNAME\" asc, list.prinadl;";

    private final String SELECT_BY_SOB = "select\n" +
            "\tlist.vid_k,\n" +
            "\tcase when adm.\"SNAME\" is null then 'НеАБД' else adm.\"SNAME\" end as sname,\n" +
            "\tlist.prinadl,\n" +
            "\tcount(*),\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 3 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 5) as \"3\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 5 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 10) as \"5\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 10 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 20) as \"10\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 20 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 24) as \"20\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') = 24) as \"24\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 25 \n" +
            "\t\tand TO_NUMBER(list.masbr, '99') < 30) as \"25\",\n" +
            "\tcount(*) FILTER (\n" +
            "\t\tWHERE TO_NUMBER(list.masbr, '99') >= 30) as \">=30\"\n" +
            "from\n" +
            "\t(\n" +
            "\tselect distinct\n" +
            "\t\tp.vid_k,\n" +
            "\t\tcase when ap.sob is null then null else p.adm end as adm_per,\n" +
            "\t\tp.num,\n" +
            "\t\tap.sob,\n" +
            "\t\tp.masbr,\n" +
            "\t\tp.prinadl\n" +
            "\tfrom\n" +
            "\t\t $tableName p\n" +
            "\tleft join \n" +
            "\t\t $abdName ap using(num)\n" +
            "\twhere p.adm = ?) list\n" +
            "LEFT JOIN local.adm as adm\n" +
            "\tON TO_NUMBER(list.adm_per, '999') = adm.kod\n" +
            "group by list.vid_k, adm.\"SNAME\", list.prinadl\n" +
            "order by list.vid_k desc, adm.\"SNAME\" desc, list.prinadl asc;";

    public List<PK2Data> select(String year) {
        List<PK2Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(getRow(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK2Data> selectBySob(int sob, String year) {
        List<PK2Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String query =  SELECT_BY_SOB
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, String.valueOf(sob));
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                data.add(getRow(result));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private PK2Data getRow(ResultSet set) throws SQLException {
        return new PK2Data(
                set.getString(1),
                set.getString(2),
                set.getString(3),
                set.getInt(4),
                set.getInt(5),
                set.getInt(6),
                set.getInt(7),
                set.getInt(8),
                set.getInt(9),
                set.getInt(10),
                set.getInt(11)
        );
    }
}
