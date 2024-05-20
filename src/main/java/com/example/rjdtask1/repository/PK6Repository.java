package com.example.rjdtask1.repository;

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
public class PK6Repository {

    private final DataSource dataSource;
    private final String SELECT = "select \n" +
            "\tlist.sname adm,\n" +
            "\tlist.dor dor, \n" +
            "\tlist.sob, \n" +
            "\tcount(list.num)\n" +
            "from\n" +
            "\t(select distinct \n" +
            "\t\tadm.\"SNAME\" as sname, \n" +
            "\t\tdor.name as dor, \n" +
            "\t\tp.num, \n" +
            "\t\tcase when ap.sob is null then '0' else adm.mnemo_r end as sob\n" +
            "\tfrom \n" +
            "\t\t perkon.per2020 p\n" +
            "\t\tleft join perkon.abd_pk2020 ap using(num)\n" +
            "\t\tINNER JOIN local.adm as adm\n" +
            "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
            "\t\tINNER JOIN local.dor as dor\n" +
            "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
            "\twhere \n" +
            "\t\tp.mesto = '0'\n" +
            "\t\tand p.prinadl = '1')list\n" +
            "group by rollup(list.sname, list.dor, list.sob)\n" +
            "order by list.sname desc, \tlist.dor desc, list.sob desc;";

    private final String SELECT_BY_STK =
            "select \n" +
                    "\tlist.sname adm,\n" +
                    "\tlist.dor dor, \n" +
                    "\tlist.sob, \n" +
                    "\tcount(list.num)\n" +
                    "from\n" +
                    "\t(select distinct \n" +
                    "\t\tadm.\"SNAME\" as sname, \n" +
                    "\t\tdor.name as dor, \n" +
                    "\t\tp.num, \n" +
                    "\t\tcase when ap.sob is null then '0' else adm.mnemo_r end as sob\n" +
                    "\tfrom \n" +
                    "\t\t $tableName p\n" +
                    "\t\tleft join $abdName ap using(num)\n" +
                    "\t\tINNER JOIN local.adm as adm\n" +
                    "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
                    "\t\tINNER JOIN local.dor as dor\n" +
                    "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
                    "\twhere p.mesto = '0' \n" +
                    "and TO_NUMBER(p.masbr, '99') < 10 \n" +
                    "and p.prinadl = '1')list\n" +
                    "group by rollup(list.sname, list.dor, list.sob)\n" +
                    "order by list.sname desc, \tlist.dor desc, list.sob desc;";

    private final String SELECT_BY_KTK =
            "select \n" +
                    "\tlist.sname adm,\n" +
                    "\tlist.dor dor, \n" +
                    "\tlist.sob, \n" +
                    "\tcount(list.num)\n" +
                    "from\n" +
                    "\t(select distinct \n" +
                    "\t\tadm.\"SNAME\" as sname, \n" +
                    "\t\tdor.name as dor, \n" +
                    "\t\tp.num, \n" +
                    "\t\tcase when ap.sob is null then '0' else adm.mnemo_r end as sob\n" +
                    "\tfrom \n" +
                    "\t\t $tableName p\n" +
                    "\t\tleft join $abdName ap using(num)\n" +
                    "\t\tINNER JOIN local.adm as adm\n" +
                    "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
                    "\t\tINNER JOIN local.dor as dor\n" +
                    "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
                    "\twhere p.mesto = '0' \n" +
                    "and TO_NUMBER(p.masbr, '99') >= 10 \n" +
                    "and p.prinadl = '1')list\n" +
                    "group by rollup(list.sname, list.dor, list.sob)\n" +
                    "order by list.sname desc, \tlist.dor desc, list.sob desc;";

    public List<PK5_6_8Data> getQueryResult(String year) {
        List<PK5_6_8Data> data = new ArrayList<>();
        //System.out.println(PEREPIS_2021);

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

        System.out.println(data);
        return data;
    }

    public List<PK5_6_8Data> selectBySTK(String year) {
        List<PK5_6_8Data> data = new ArrayList<>();

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

    public List<PK5_6_8Data> selectByKTK(String year) {
        List<PK5_6_8Data> data = new ArrayList<>();

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

    private PK5_6_8Data extract(ResultSet set) throws SQLException {
        return new PK5_6_8Data(
                set.getString(1),
                set.getString(2),
                set.getString(3),
                set.getInt(4)
        );
    }
}
