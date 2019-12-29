package no.maddin.mockjdbc;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class MockStatement implements CallableStatement {

    private File currentFile;
    private Properties connectionProperties;
    private SQLWarning warnings;
    private MockResultSet currentResultSet;
    private AtomicBoolean isClosed = new AtomicBoolean(true);

    MockStatement(Properties connectionProperties, String sql) {
        this.connectionProperties = connectionProperties;
        if (sql != null) {
            openCurrentFile(sql);
        }
    }

    private synchronized void openCurrentFile(String sql) {
        if (currentFile != null) {
            throw new IllegalArgumentException("file already set");
        }
        currentFile = new File(connectionProperties.getProperty("path"), DriverTool.fileName(sql) + ".csv");
        isClosed.set(false);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("wasNull");

    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getString");

    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getBoolean");

    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getByte");

    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getShort");

    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getInt");

    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getLong");

    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getFloat");

    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getDouble");

    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        throw new UnsupportedOperationException("getBigDecimal");

    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getBytes");

    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getDate");

    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getTime");

    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getTimestamp");

    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getBigDecimal");

    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getRef");

    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getBlob");

    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getClob");

    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getArray");

    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getDate");

    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getTime");

    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getTimestamp");

    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        throw new UnsupportedOperationException("registerOutParameter");

    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getURL");

    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        throw new UnsupportedOperationException("setURL");

    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        throw new UnsupportedOperationException("setNull");

    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        throw new UnsupportedOperationException("setBoolean");

    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        throw new UnsupportedOperationException("setByte");

    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        throw new UnsupportedOperationException("setShort");

    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        throw new UnsupportedOperationException("setInt");

    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        throw new UnsupportedOperationException("setLong");

    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        throw new UnsupportedOperationException("setFloat");

    }

    @Override
    public void setDouble(String parameterName, double x) throws SQLException {
        throw new UnsupportedOperationException("setDouble");

    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException("setBigDecimal");

    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        throw new UnsupportedOperationException("setString");

    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        throw new UnsupportedOperationException("setBytes");

    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        throw new UnsupportedOperationException("setDate");

    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        throw new UnsupportedOperationException("setTime");

    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException("setTimestamp");

    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setDate");

    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setTime");

    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setTimestamp");

    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        throw new UnsupportedOperationException("setNull");

    }

    @Override
    public String getString(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getString");

    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getBoolean");

    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getByte");

    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getShort");

    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getInt");

    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getLong");

    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getFloat");

    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getDouble");

    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getBytes");

    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getDate");

    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getTime");

    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getTimestamp");

    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getBigDecimal");

    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getRef");

    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getBlob");

    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getClob");

    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getArray");

    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getDate");

    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getTime");

    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("getTimestamp");

    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getURL");

    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getRowId");

    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getRowId");

    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        throw new UnsupportedOperationException("setRowId");

    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        throw new UnsupportedOperationException("setNString");

    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        throw new UnsupportedOperationException("setNCharacterStream");

    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getNClob");

    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getNClob");

    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("setSQLXML");

    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getSQLXML");

    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getSQLXML");

    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getNString");

    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getNString");

    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getNCharacterStream");

    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getNCharacterStream");

    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        throw new UnsupportedOperationException("getCharacterStream");

    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        throw new UnsupportedOperationException("getCharacterStream");

    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        throw new UnsupportedOperationException("setNCharacterStream");

    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("getObject");

    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return new MockResultSet(currentFile);
    }

    @Override
    public int executeUpdate() throws SQLException {
        throw new UnsupportedOperationException("executeUpdate");

    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        throw new UnsupportedOperationException("setNull");

    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        throw new UnsupportedOperationException("setBoolean");

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException("setByte");

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        throw new UnsupportedOperationException("setShort");

    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        throw new UnsupportedOperationException("setInt");

    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        throw new UnsupportedOperationException("setLong");

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        throw new UnsupportedOperationException("setFloat");

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        throw new UnsupportedOperationException("setDouble");

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException("setBigDecimal");

    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        throw new UnsupportedOperationException("setString");

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        throw new UnsupportedOperationException("setBytes");

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        throw new UnsupportedOperationException("setDate");

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        throw new UnsupportedOperationException("setTime");

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException("setTimestamp");

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("setUnicodeStream");

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void clearParameters() throws SQLException {
        throw new UnsupportedOperationException("clearParameters");

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public boolean execute() throws SQLException {
        if (currentFile.exists()) {
            currentResultSet = new MockResultSet(currentFile);
            return true;
        } else {
            currentResultSet = null;
            return false;
        }
    }

    @Override
    public void addBatch() throws SQLException {
        throw new UnsupportedOperationException("addBatch");

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        throw new UnsupportedOperationException("setRef");

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        throw new UnsupportedOperationException("setArray");

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;

    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setDate");

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setTime");

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("setTimestamp");

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        throw new UnsupportedOperationException("setNull");

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        throw new UnsupportedOperationException("setURL");

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw new UnsupportedOperationException("getParameterMetaData");

    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        throw new UnsupportedOperationException("setRowId");

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        throw new UnsupportedOperationException("setNString");

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        throw new UnsupportedOperationException("setNCharacterStream");

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("setSQLXML");

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        throw new UnsupportedOperationException("setObject");

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("setAsciiStream");

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("setBinaryStream");

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setCharacterStream");

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        throw new UnsupportedOperationException("setNCharacterStream");

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setClob");

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("setBlob");

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("setNClob");

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        openCurrentFile(sql);
        currentResultSet = new MockResultSet(currentFile);
        return currentResultSet;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        throw new UnsupportedOperationException("executeUpdate");

    }

    @Override
    public void close() throws SQLException {
        if (currentFile != null) {
            currentFile = null;
        }
        isClosed.set(true);
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException("getMaxFieldSize");

    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException("setMaxFieldSize");

    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException("getMaxRows");

    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw new UnsupportedOperationException("setMaxRows");

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException("setEscapeProcessing");

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new UnsupportedOperationException("getQueryTimeout");

    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("setQueryTimeout");

    }

    @Override
    public void cancel() throws SQLException {
        throw new UnsupportedOperationException("cancel");

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return warnings;
    }

    @Override
    public void clearWarnings() throws SQLException {
        warnings = null;

    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException("setCursorName");

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        openCurrentFile(sql);
        return execute();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException("getUpdateCount");

    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException("getMoreResults");

    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("setFetchDirection");

    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("getFetchDirection");

    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("setFetchSize");

    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("getFetchSize");

    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException("getResultSetConcurrency");

    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException("getResultSetType");

    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new UnsupportedOperationException("addBatch");

    }

    @Override
    public void clearBatch() throws SQLException {
        throw new UnsupportedOperationException("clearBatch");

    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new UnsupportedOperationException("executeBatch");

    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new UnsupportedOperationException("getConnection");

    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException("getMoreResults");

    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException("getGeneratedKeys");

    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("executeUpdate");

    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("executeUpdate");

    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("executeUpdate");

    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return execute(sql);

    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return execute(sql);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return execute(sql);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException("getResultSetHoldability");

    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed.get();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException("setPoolable");

    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException("isPoolable");

    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("closeOnCompletion");

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("isCloseOnCompletion");

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("unwrap");

    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("isWrapperFor");

    }
}
