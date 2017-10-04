package no.maddin.mockjdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DriverTest {

    @Test
    public void loadDriverWithURL() throws Exception {
        java.sql.Driver drv = DriverManager.getDriver("jdbc:mock:csv;path=.");
        assertThat(drv, is(instanceOf(no.maddin.mockjdbc.Driver.class)));
    }

    @Test
    public void driverConnection() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mock:csv;path=.");
        assertThat(connection, is(instanceOf(MockConnection.class)));
    }

    @Test
    public void driverConnectionWithProperties() throws Exception {
        Properties props = new Properties();
        props.put("repeat", "2100");
        Connection connection = DriverManager.getConnection("jdbc:mock:csv;path=.", props);
        assertThat(connection, is(instanceOf(MockConnection.class)));
        assertThat(((MockConnection)connection).getConnectionProperties(), hasKey("repeat"));
    }
}
