package no.maddin.mockjdbc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.support.ReflectionSupport;
import org.junit.platform.commons.util.ExceptionUtils;

class ConnectionTest {

    private static final String SELECT_A_B_FROM_MYTABLE = "select a, b from mytable";
    private static File origPath;
    private static File checkPath;

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

        @SuppressWarnings("unchecked")
        private T createObject(S srcObject) throws SQLException {
                Class[] argTypes = Stream.of(args)
                    .map(o -> {
                        Class<?> oClass = o.getClass();
                        switch(oClass.getSimpleName()) {
                            case "Integer":
                                return int.class;
                            default:
                                return oClass;
                        }
                    })
                    .collect(Collectors.toList())
                    .toArray(new Class[0]);
                Optional<Method> m = ReflectionSupport.findMethod(srcObject.getClass(), method, argTypes);

                return (T) ReflectionSupport.invokeMethod(
                    m.orElseThrow(() -> ExceptionUtils.throwAsUncheckedException(new SQLException("Missing function: " + method))),
                    srcObject,
                    args);
        }
    }

    private static Stream<Arguments> data() {

        return Stream.of(
            Arguments.of(
                "prepareStatement",
                ConnectionCall.create("prepareStatement", SELECT_A_B_FROM_MYTABLE),
                ConnectionCall.create("executeQuery")
            ),
            Arguments.of(
                "prepareStatement(2)",
                ConnectionCall.create("prepareStatement", SELECT_A_B_FROM_MYTABLE, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY),
                ConnectionCall.create("executeQuery")
            ),
            Arguments.of(
                "prepareStatement(3)",
                ConnectionCall.create("prepareStatement", SELECT_A_B_FROM_MYTABLE, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT),
                ConnectionCall.create("executeQuery")
            ),
            Arguments.of(
                "createStatement",
                ConnectionCall.create("createStatement"),
                ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
            ),
            Arguments.of(
                "createStatement(2)",
                ConnectionCall.create("createStatement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY),
                ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
            ),
            Arguments.of(
                "createStatement(3)",
                ConnectionCall.create("createStatement", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT),
                ConnectionCall.create("executeQuery", SELECT_A_B_FROM_MYTABLE)
            )
        );
    }


    @BeforeAll
    static void init() {
        String basePath = ConnectionTest.class.getResource("/").getFile();
        origPath = new File(basePath, "csv");
        checkPath = new File(basePath, "csv-check");
    }


    /**
     *     public String testName;

     public ConnectionCall<Connection, Statement> createStatementCall;

     public ConnectionCall<Statement, ResultSet> createResultSetCall;


     * This test reads a test database, writes the result to a file and re-reads the result and compares the results.
     */
    @DisplayName("Connection Tests")
    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("data")
    void connectionTest(
        String testName,
        ConnectionCall<Connection, Statement> createStatementCall,
        ConnectionCall<Statement, ResultSet> createResultSetCall
    ) throws Exception {
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
}
