package no.maddin.mockjdbc.jooq;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class JooqTest {

    private static File outputDir;
    @BeforeAll
    static void setupPaths() {
        outputDir = new File(JooqTest.class.getResource("/jooq").getFile()).getAbsoluteFile();
    }

    @Test
    void simpleQuery() throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + outputDir.toString())
        ) {
            DSLContext context = DSL.using(con);
            Result<org.jooq.Record> records = context.select().from("mytable").fetch();
            assertThat(records.size(), is(equalTo(2)));
            assertThat(records.fields(),
                is(
                    arrayContaining(
                        hasProperty("name", equalTo("a")),
                        hasProperty("name", equalTo("b"))
                    )
                )
            );
        }
    }

    static Stream<Arguments> numberTypes() {
        return Stream.of(
            Arguments.of(Integer.class),
            Arguments.of(Double.class),
            Arguments.of(Float.class),
            Arguments.of(Long.class),
            Arguments.of(Short.class),
            Arguments.of(Byte.class),
            Arguments.of(BigDecimal.class)
        );
    }

    @ParameterizedTest(name = "{index} {arguments}")
    @DisplayName("Query: Get numbers")
    @MethodSource("numberTypes")
    <T extends Number> void queryGetNumbers(Class<T> argClass) throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + outputDir.toString())
        ) {
            DSLContext context = DSL.using(con);
            Result<Record2<String, T>> result = context.select(
                field("id", String.class),
                field("numberval", argClass)
            ).from(
                table("mytable")
            ).fetch();

            assertThat(result.size(), is(equalTo(4)));

            assertThat(result.fields(),
                is(
                    arrayContaining(
                        hasProperty("name", equalTo("id")),
                        hasProperty("name", equalTo("numberval"))
                    )
                )
            );
            for (Record2<String, T> rec : result) {
                assertThat(rec.field2(), hasProperty("dataType", hasProperty("type", equalTo(argClass))));
            }
        }
    }
}
