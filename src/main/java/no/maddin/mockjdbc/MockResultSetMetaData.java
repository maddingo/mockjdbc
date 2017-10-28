package no.maddin.mockjdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class MockResultSetMetaData implements ResultSetMetaData {

    public static final int DEFAULT_COLUMN_PRECISION = 60;
    private static final int DEFAULT_COLUMN_SCALE = 0;
    private static final String DEFAULT_COLUMN_TYPENAME = "varchar";
    private final String[] columnNames;
    private final String tableName;

    public MockResultSetMetaData(String tableName, String headerLine) {
        columnNames = headerLine.split("\\s*,\\s*");
        this.tableName = tableName;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnNames.length;
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new UnsupportedOperationException("isAutoIncrement");

    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException("isCaseSensitive");

    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException("isSearchable");

    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException("isCurrency");

    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new UnsupportedOperationException("isNullable");

    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException("isSigned");

    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException("getColumnDisplaySize");

    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return getColumnName(column);
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columnNames[column-1];
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return "";
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return DEFAULT_COLUMN_PRECISION;
    }

    @Override
    public int getScale(int column) throws SQLException {
        return DEFAULT_COLUMN_SCALE;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return tableName;
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException("getCatalogName");

    }

    @Override
    public int getColumnType(int column) throws SQLException {
        throw new UnsupportedOperationException("getColumnType");

    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return DEFAULT_COLUMN_TYPENAME;
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new UnsupportedOperationException("isReadOnly");

    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("isWritable");

    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("isDefinitelyWritable");

    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new UnsupportedOperationException("getColumnClassName");

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("unwrap");

    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("isWrapperFor");

    }

    public String[] getColumnNames() {
        return columnNames;
    }
}
