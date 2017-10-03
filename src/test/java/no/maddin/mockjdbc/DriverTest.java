package no.maddin.mockjdbc;

import org.junit.Test;

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
}
