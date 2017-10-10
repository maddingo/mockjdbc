package no.maddin.mockjdbc;

import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {


    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            PrintWriter pw = DriverManager.getLogWriter();
            if (pw != null) {
                e.printStackTrace(pw);
            } else {
                e.printStackTrace();
            }
        }
    }

    public Connection connect(String url, Properties info) throws SQLException {
        return new MockConnection(url, info);
    }

    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith("jdbc:mock:");
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 1;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean jdbcCompliant() {
        return false;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

}
