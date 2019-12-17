package no.maddin.mockjdbc;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.Test;


class DriverTest {

    @Test
    void loadDriverWithURL() throws Exception {
        java.sql.Driver drv = DriverManager.getDriver("jdbc:mock:csv;path=.");
        assertThat(drv, is(instanceOf(no.maddin.mockjdbc.Driver.class)));
    }

    @Test
    void driverConnection() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mock:csv;path=.");
        assertThat(connection, is(instanceOf(MockConnection.class)));
    }

    @Test
    void driverConnectionWithProperties() throws Exception {
        Properties props = new Properties();
        props.put("repeat", "2100");
        Connection connection = DriverManager.getConnection("jdbc:mock:csv;path=.", props);
        assertThat(connection, is(instanceOf(MockConnection.class)));
        assertThat(((MockConnection)connection).getConnectionProperties(), hasKey("repeat"));
    }

    @Test
    void driverConnectionWithOtherUrl() throws Exception {
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl")) {
            assertThat(con, is(notNullValue()));
        } catch (Exception ex) {
            assertThat(ex, hasProperty("message", containsString("not accepted")));
        }
    }
}
