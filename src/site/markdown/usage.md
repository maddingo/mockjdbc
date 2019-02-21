# Usage
The goal of mock-jdbc is to provide a simple way of delivering data during testing through JDBC.

## JDBC URL
`jdbc:mock:csv;path=<path to data files>`

## File Format
Each SQL query expects a file name with the hash of the query. 
```
col1|type,col2,....
val11,val12,...
val21, val22,...
...
```

## Determine the file name 
`java -jar mock-jdbc.jar no.maddin.mockjdbc.DriverTool "select 1 from dual` would generate 2a44ecfe. 
I.e. the data for the query has to be in `<path to data file>/2a44ecfe.csv`.
## Example

```java
import javax.sql.*;
import java.io.*;

class MockJdbcTest {

    private static final String dataDbDir = new File(MockJdbcTest.class.getResource("/mockdb").getFile()).getAbsolutePath();
    
    @Test        
    public void showMockData() {
        try (Connection con = DriverManager.getConnection("jdbc:mock:csv;path=" + dataDbDir);
            PreparedStatement st = con.prepareStatement("select id, val from testTable");
            ResultSet rs = st.executeQuery()
        ) {
            while(rs.next()) {
                assertThat(rs.getMetaData().getColumnLabel(2), is(equalTo("val")));
                assertThat(rs.getMetaData().getColumnType(2), is(equalTo(columnType)));
                T val = getValue.getValue(rs);
                assertThat(val, is(instanceOf(targetClass)));
            }
        }
    }
}
```