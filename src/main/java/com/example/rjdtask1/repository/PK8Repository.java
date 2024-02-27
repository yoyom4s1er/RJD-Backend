package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK8Data;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PK8Repository {

    private final DataSource dataSource;
    private final String PEREPIS_2021 =
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
                    "\t\t\"perkon\".per2021 p\n" +
                    "\t\tleft join \"perkon\".abd_pk2021 ap using(num)\n" +
                    "\t\tINNER JOIN local.adm as adm\n" +
                    "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
                    "\t\tINNER JOIN local.dor as dor\n" +
                    "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
                    "\twhere p.mesto = '7'" +
                    "and p.prinadl = '1')list\n" +
                    "group by grouping sets(rollup(list.sname, list.dor, list.sob))\n" +
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
                    "\t\t\"perkon\".per2021 p\n" +
                    "\t\tleft join \"perkon\".abd_pk2021 ap using(num)\n" +
                    "\t\tINNER JOIN local.adm as adm\n" +
                    "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
                    "\t\tINNER JOIN local.dor as dor\n" +
                    "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
                    "\twhere p.mesto = '7' \n" +
                    "and TO_NUMBER(p.masbr, '99') < 10 \n" +
                    "and p.prinadl = '1')list\n" +
                    "group by grouping sets(rollup(list.sname, list.dor, list.sob))\n" +
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
                    "\t\t\"perkon\".per2021 p\n" +
                    "\t\tleft join \"perkon\".abd_pk2021 ap using(num)\n" +
                    "\t\tINNER JOIN local.adm as adm\n" +
                    "\t\t\tON TO_NUMBER(p.adm_per, '999') = adm.kod\n" +
                    "\t\tINNER JOIN local.dor as dor\n" +
                    "\t\t\tON TO_NUMBER(p.dor_per, '999') = dor.kod\n" +
                    "\twhere p.mesto = '7' \n" +
                    "and TO_NUMBER(p.masbr, '99') >= 10 \n" +
                    "and p.prinadl = '1')list\n" +
                    "group by grouping sets(rollup(list.sname, list.dor, list.sob))\n" +
                    "order by list.sname desc, \tlist.dor desc, list.sob desc;";

    public List<PK8Data> getQueryResult() {
        List<PK8Data> data = new ArrayList<>();
        //System.out.println(PEREPIS_2021);

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(PEREPIS_2021);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK8Data> selectBySTK() {
        List<PK8Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT_BY_STK);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK8Data> selectByKTK() {
        List<PK8Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT_BY_KTK);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private PK8Data extract(ResultSet set) throws SQLException {
        return new PK8Data(
                set.getString(1),
                set.getString(2),
                set.getString(3),
                set.getInt(4)
        );
    }
}
