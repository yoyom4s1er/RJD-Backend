package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK21Data;
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
public class PK21Repository {

    private final DataSource dataSource;

    private final String SELECT = "select \n" +
            "\tlist.sname adm,\n" +
            "\tlist.dor dor, \n" +
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
            "\t(select distinct \n" +
            "\t\tadm.\"SNAME\" as sname, \n" +
            "\t\tdor.name as dor, \n" +
            "\t\tp.num,\n" +
            "\t\tp.masbr\n" +
            "\tfrom \n" +
            "\t\t perkon.per2020 p\n" +
            "\t\tleft join perkon.abd_pk2020 ap using(num)\n" +
            "\t\tINNER JOIN local.adm as adm\n" +
            "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
            "\t\tINNER JOIN local.dor as dor\n" +
            "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\t\t\n" +
            "\twhere \n" +
            "\t\tp.mesto = '1'\n" +
            "\t\tor p.mesto = '2'\n" +
            "\t\tor p.mesto = '4'\n" +
            "\t\tor p.mesto = '5'\n" +
            "\t\tor p.mesto = '8'\n" +
            "\t\tor p.mesto = '9'\n" +
            "\t\tor (p.mesto = '3' and p.prinadl = '1'))list\n" +
            "group by list.sname, list.dor\n" +
            "order by list.sname asc , \tlist.dor asc;";

    public List<PK21Data> getQueryResult(String year) {
        List<PK21Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$tableName", "perkon.per" + year)
                    .replace("$abdName", "perkon.abd_pk" + year);
            ResultSet result = statement.executeQuery(query);

            System.out.println(query);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private PK21Data extract(ResultSet result) throws SQLException {
        return new PK21Data(
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getInt(4),
                result.getInt(5),
                result.getInt(6),
                result.getInt(7),
                result.getInt(8),
                result.getInt(9)
        );
    }
}
