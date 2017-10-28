package no.maddin.mockjdbc.jooq;

import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JooqTest {

    private static File outputDir;
    @BeforeAll
    static void setupPaths() {
        outputDir = new File(JooqTest.class.getResource("/jooq").getFile()).getAbsoluteFile();
    }

    @Test
    void jooqQuery() throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + outputDir.toString());
            DSLContext context = DSL.using(con)
        ) {
            Result<Record> records = context.select().from("mytable").fetch();
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
}
