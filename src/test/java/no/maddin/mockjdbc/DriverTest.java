package no.maddin.mockjdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
