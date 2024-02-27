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
            "\tperkon.per2019 p\t\n" +
            "left join perkon.abd_pk2019 ap using (num)\n" +
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
            "\tperkon.per2020 p\n" +
            "left join perkon.abd_pk2020 ap using (num)\n" +
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
            "\tperkon.per2021 p\n" +
            "left join perkon.abd_pk2021 ap using (num)\n" +
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
            "\t\tperkon.abd_pk2019\n" +
            "\tunion all\n" +
            "\tselect\n" +
            "\t\tnum,\n" +
            "\t\tsob,\n" +
            "\t\tb_ind\n" +
            "\tfrom\n" +
            "\t\tperkon.abd_pk2020\n" +
            "\tunion all\n" +
            "\tselect\n" +
            "\t\tnum,\n" +
            "\t\tsob,\n" +
            "\t\tb_ind\n" +
            "\tfrom\n" +
            "\t\tperkon.abd_pk2019) list";

    public List<PK17Data> getData() {
        List<PK17Data> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT);

            while (result.next()) {
                data.add(extract(result));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    public List<PK17AbdData> getContainersABDPK() {
        List<PK17AbdData> data = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SELECT_CONTAINERS_ABD_PK);

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
