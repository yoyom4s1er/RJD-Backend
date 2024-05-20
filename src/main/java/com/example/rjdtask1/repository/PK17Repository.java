package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.PK17AbdData;
import com.example.rjdtask1.model.PK17Data;
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
public class PK17Repository {

    private final DataSource dataSource;

    private final String SELECT = "select\n" +
            "'2019' as year,\n" +
            "case when ap.sob is null then 'NoABD' else p.adm end as adm,\n" +
            "\tp.num,\n" +
            "\tap.b_ind,\n" +
            "\tp.masbr \n" +
            "from\n" +
            "\t $thirdTable p\t\n" +
            "left join $thirdABD ap using (num)\n" +
            "where\n" +
            "\tp.prinadl = '1'\n" +
            "union all\n" +
            "select\n" +
            "\t'2020' as year,\n" +
            "case when ap.sob is null then 'NoABD' else p.adm end as adm,\n" +
            "\tp.num,\n" +
            "\tap.b_ind,\n" +
            "\tp.masbr \n" +
            "from\n" +
            "\t $secondTable p\n" +
            "left join $secondABD ap using (num)\n" +
            "where\n" +
            "\tp.prinadl = '1'\n" +
            "union all\n" +
            "select\n" +
            "\t'2021' as year,\n" +
            "case when ap.sob is null then 'NoABD' else p.adm end as adm,\n" +
            "\tp.num,\n" +
            "\tap.b_ind,\n" +
            "\tp.masbr \n" +
            "from\n" +
            "\t $mainTable p\n" +
            "left join $mainABD ap using (num)\n" +
            "where\n" +
            "\tp.prinadl = '1'";

    private final String SELECT_CONTAINERS_ABD_PK = "select distinct \n" +
            "\tlist.num,\n" +
            "\tlist.sob,\n" +
            "\tlist.b_ind\n" +
            "from\n" +
            "\t(select\n" +
            "\t\tnum,\n" +
            "\t\tsob,\n" +
            "\t\tb_ind\n" +
            "\tfrom\n" +
            "\t\t $thirdABD \n" +
            "\tunion all\n" +
            "\tselect\n" +
            "\t\tnum,\n" +
            "\t\tsob,\n" +
            "\t\tb_ind\n" +
            "\tfrom\n" +
            "\t\t $secondABD \n" +
            "\tunion all\n" +
            "\tselect\n" +
            "\t\tnum,\n" +
            "\t\tsob,\n" +
            "\t\tb_ind\n" +
            "\tfrom\n" +
            "\t\t $mainABD) list";

    public List<PK17Data> getData(String year) {
        List<PK17Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT
                    .replace("$mainTable", "perkon.per" + year)
                    .replace("$mainABD", "perkon.abd_pk" + year)
                    .replace("$secondTable", "perkon.per" + "2020")
                    .replace("$secondABD", "perkon.abd_pk" + "2020")
                    .replace("$thirdTable", "perkon.per" + "2019")
                    .replace("$thirdABD", "perkon.abd_pk" + "2019");
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

    public List<PK17AbdData> getContainersABDPK(String year) {
        List<PK17AbdData> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            String query =  SELECT_CONTAINERS_ABD_PK
                    .replace("$mainABD", "perkon.abd_pk" + year)
                    .replace("$secondABD", "perkon.abd_pk" + "2020")
                    .replace("$thirdABD", "perkon.abd_pk" + "2019");
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                data.add(extractABDData(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private PK17Data extract(ResultSet result) throws SQLException {
        return new PK17Data(
                result.getString(1),
                result.getString(2),
                result.getString(3).replaceAll("\\s+",""),
                result.getString(4),
                result.getString(5)
        );
    }

    private PK17AbdData extractABDData(ResultSet result) throws SQLException {
        return new PK17AbdData(
                result.getString(1),
                result.getString(2),
                result.getString(3)
        );
    }
}
