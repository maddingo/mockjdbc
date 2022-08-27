package no.maddin.mockjdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DriverRegistrationTest {

    /**
     * Ensure that DriverManager.getConnection returns the connection matching the driver url regardless of how many other drivers are registered.
     */
    @Test
    public void driverManagerGetConnection() throws Exception {

        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        List<Driver> drivers = new ArrayList<>();
        while(driverEnumeration.hasMoreElements()) {
            drivers.add(driverEnumeration.nextElement());
        }
        assertThat(drivers, iterableWithSize(greaterThan(1)));

        validateDrivers();

        // reverse order and run the test again
        for (Driver driver : drivers) {
            DriverManager.deregisterDriver(driver);
        }
        Collections.reverse(drivers);
        for (Driver driver : drivers) {
            DriverManager.registerDriver(driver);
        }

        validateDrivers();
    }

    private void validateDrivers() throws SQLException {
        Connection conn1 = DriverManager.getConnection("jdbc:mock:csv;path=.");
        assertThat(conn1, is(instanceOf(MockConnection.class)));

        Connection conn2 = DriverManager.getConnection("jdbc:hsqldb:mem:sampledb");
        assertThat(conn2, is(instanceOf(org.hsqldb.jdbc.JDBCConnection.class)));
    }
}
