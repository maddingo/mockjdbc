package no.maddin.mockjdbc;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection {
    private final Properties connectionProperties = new Properties();
    private final String url;
    private Statement currentStatement;

    public MockConnection(String url, Properties info) {
        this.url = url;
        addPropsFromUrl(url);
        this.connectionProperties.putAll(info);
    }

    private void addPropsFromUrl(String url) {
        int propSeparator = url.indexOf(';');
        String[] propPairs = url.substring(propSeparator + 1).split("\\s*,\\s*");

        for (String propPair : propPairs) {
            String[] keyValue = propPair.split("\\s*=\\s*");
            connectionProperties.put(keyValue[0], keyValue[1]);
        }
    }

    @Override
    public Statement createStatement() throws SQLException {
        if (currentStatement != null) {
            throw new IllegalArgumentException("previous connection not closed");
        }
        currentStatement = new MockStatement(connectionProperties, null);
        return currentStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (currentStatement != null) {
            throw new IllegalArgumentException("previous connection not closed");
        }
        currentStatement = new MockStatement(connectionProperties, sql);
        return (PreparedStatement)currentStatement;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void commit() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void rollback() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void close() throws SQLException {
        currentStatement.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("not yet");
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }
}
