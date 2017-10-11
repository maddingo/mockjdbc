package no.maddin.mockjdbc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConnectionTest {

    public static final String SELECT_A_B_FROM_MYTABLE = "select a, b from mytable";
    private static File origPath;
    private static File checkPath;

    @BeforeClass
    public static void init() {
        String basePath = ConnectionTest.class.getResource("/").getFile();
        origPath = new File(basePath, "csv");
        checkPath = new File(basePath, "csv-check");
    }

    @Test
    public void selectTagAndValue() throws Exception {
        String checkFile = DriverTool.fileName(SELECT_A_B_FROM_MYTABLE);
        File f = new File(checkPath, checkFile);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try (PrintWriter fw = new PrintWriter(new FileWriter(f.getPath()))) {
            fw.println("# " + SELECT_A_B_FROM_MYTABLE);
            fw.println("a, b");
            try (
                Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + origPath.getAbsolutePath());
                PreparedStatement st = con.prepareStatement(SELECT_A_B_FROM_MYTABLE);
                ResultSet rs = st.executeQuery()
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
