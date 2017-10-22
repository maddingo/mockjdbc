package no.maddin.mockjdbc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

import org.apache.commons.beanutils.MethodUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ConnectionTest {

    private static final String SELECT_A_B_FROM_MYTABLE = "select a, b from mytable";
    private static File origPath;
    private static File checkPath;

    @Parameterized.Parameter(0)
    public String testName;

    @Parameterized.Parameter(1)
    public ConnectionCall<Connection, Statement> createStatementCall;

    @Parameterized.Parameter(2)
    public ConnectionCall<Statement, ResultSet> createResultSetCall;

    private static class ConnectionCall<S, T> {
        private final String method;
        private final Object[] args;

        private ConnectionCall(String method, Object[] args) {
            this.method = method;
            this.args = args;
        }

        private static ConnectionCall create(String methodName, Object... args) {
            return new ConnectionCall(methodName, args);
        }

        private T createObject(S srcObject) throws SQLException {
            try {
                return (T) MethodUtils.invokeMethod(srcObject, method, args);
            } catch (ReflectiveOperationException e) {
                throw new SQLException(e);
            }
//            Class<?>[] argTypes = new Class[args.length];
//            int i = 0;
//            for (Object arg : args) {
//                argTypes[i++] = arg.getClass();
//            }
//            try {
//                Method m = srcObject.getClass().getMethod(method, argTypes);
//                return (T) m.invoke(srcObject, args);
//            } catch (ReflectiveOperationException e) {
//                throw new SQLException(e);
//            }

        }
    }

    @Parameterized.Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[] {
            "prepareStatement",
            ConnectionCall.create("prepareStatement", SELECT_A_B_FROM_MYTABLE),
            ConnectionCall.create("executeQuery")
        });
        data.add(new Object[] {
            "createStatement",
            ConnectionCall.create("createStatement"),
            ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
        });
        data.add(new Object[] {
            "createStatement(2)",
            ConnectionCall.create("createStatement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY),
            ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
        });
        data.add(new Object[] {
            "createStatement(3)",
            ConnectionCall.create("createStatement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT),
            ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
        });
        return data;
    }


    @BeforeClass
    public static void init() {
        String basePath = ConnectionTest.class.getResource("/").getFile();
        origPath = new File(basePath, "csv");
        checkPath = new File(basePath, "csv-check");
    }

    /**
     * This test reads a test database, writes the result to a file and re-reads the result and compares the results.
     */
    @Test
    public void connectionTest() throws Exception {
        String checkFile = DriverTool.fileName(SELECT_A_B_FROM_MYTABLE);
        File f = new File(checkPath, checkFile + ".csv");

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try (PrintWriter fw = new PrintWriter(new FileWriter(f.getPath()))) {
            fw.println("a, b");
            try (
                Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + origPath.getAbsolutePath());
                Statement st = createStatementCall.createObject(con);
                ResultSet rs = createResultSetCall.createObject(st)
            ) {
                while (rs.next()) {
                    String a = rs.getString("a");
                    String b = rs.getString("b");
                    map.put(a, b);
                    fw.printf("%s, %s\n", a, b);
                }
            }
        }

        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + checkPath.getAbsolutePath());
            PreparedStatement st = con.prepareStatement(SELECT_A_B_FROM_MYTABLE);
            ResultSet rs = st.executeQuery()
        ) {
            Iterator<Map.Entry<String, String>> entryIterator = map.entrySet().iterator();
            while (rs.next()) {
                Map.Entry<String, String> entry = entryIterator.next();
                String a = rs.getString("a");
                String b = rs.getString("b");
                assertThat(a, is(equalTo(entry.getKey())));
                assertThat(b, is(equalTo(entry.getValue())));
            }
        }
        assertThat(map.entrySet(), is(not(empty())));
    }

//    @Test
    public void createStatement2Args() throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + origPath.getAbsolutePath());
            Statement st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet rs = st.executeQuery(SELECT_A_B_FROM_MYTABLE);
            while (rs.next()) {
                assertThat(rs.getString("a"), is(notNullValue()));
            }
        }
    }

//    @Test
    public void createStatement3Args() throws Exception {
        try (
            Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + origPath.getAbsolutePath());
            Statement st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT)
        ) {
            ResultSet rs = st.executeQuery(SELECT_A_B_FROM_MYTABLE);
            while (rs.next()) {
                assertThat(rs.getString("a"), is(notNullValue()));
            }
        }
    }
}
