package no.maddin.mockjdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.sql.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import no.maddin.mockjdbc.jooq.JooqTest;

public class DataTypesTest {

    private static File csvFileDir;

    @BeforeAll
    static void setupPaths() {
        csvFileDir = new File(JooqTest.class.getResource("/csv").getFile()).getAbsoluteFile();
    }

    static Stream<Arguments> testParams() {
        return Stream.of(
            Arguments.of(
                "inttable",
                Types.INTEGER,
                (GetValue<Integer>) rs -> rs.getInt(2),
                Integer.class
            ),
            Arguments.of(
                "stringtable",
                Types.VARCHAR,
                (GetValue<String>) rs -> rs.getString(2),
                String.class
            ),
            Arguments.of(
                "doubletable",
                Types.DOUBLE,
                (GetValue<Double>) rs -> rs.getDouble(2),
                Double.class),
            Arguments.of(
                "timetable",
                Types.TIME,
                (GetValue<Time>) rs -> rs.getTime(2),
                Time.class),
            Arguments.of(
                "timestamptable",
                Types.TIMESTAMP,
                (GetValue<Timestamp>) rs -> rs.getTimestamp(2),
                Timestamp.class),
            Arguments.of(
                "datetable",
                Types.DATE,
                (GetValue<Date>) rs -> rs.getDate(2),
                Date.class)
        );
    }

    @ParameterizedTest()
    @DisplayName("Query with valueTypes")
    @MethodSource("testParams")
    <T> void parseCsvFileWithDataTypes(String tableName, int columnType, GetValue<T> getValue, Class<T> targetClass) throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + csvFileDir.toString());
            PreparedStatement st = con.prepareStatement("select id, val from " + tableName);
            ResultSet rs = st.executeQuery()
        ) {
            while(rs.next()) {
                assertThat(rs.getMetaData().getColumnLabel(2), is(equalTo("val")));
                assertThat(rs.getMetaData().getColumnType(2), is(equalTo(columnType)));
                T val = getValue.getValue(rs);
                assertThat(val, is(instanceOf(targetClass)));
            }
        }
    }

    @FunctionalInterface
    interface GetValue<T> {
        T getValue(ResultSet resultSet) throws SQLException;
    }
}