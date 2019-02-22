package no.maddin.mockjdbc;

import java.sql.*;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection {
    private static final Properties EMPTY_PROPERTIES = new Properties();
    private final Properties connectionProperties = new Properties();
    private final String url;
    private Statement currentStatement;
    private final MockDatabaseMetaData metadata;

    public MockConnection(String url, Properties info) {
        this.url = url;
        addPropsFromUrl(url);
        this.connectionProperties.putAll(info);
        this.metadata = new MockDatabaseMetaData();
        metadata.setURL(url);
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
        return createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new UnsupportedOperationException("prepareCall(String)");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("nativeSQL(String)");
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        // do nothing
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        throw new UnsupportedOperationException("getAutoCommit()");
    }

    @Override
    public void commit() throws SQLException {
        throw new UnsupportedOperationException("commit()");
    }

    @Override
    public void rollback() throws SQLException {
        throw new UnsupportedOperationException("rollback()");
    }

    @Override
    public void close() throws SQLException {
        if (currentStatement != null) {
            currentStatement.close();
            currentStatement = null;
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        return currentStatement == null || currentStatement.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return metadata;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new UnsupportedOperationException("setReadOnly");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return true;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("setCatalog(String)");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("getCatalog()");
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        throw new UnsupportedOperationException("steTransactionIsolation(int)");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("getTransactionIsolation");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("getWarnings");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("clearWarnings");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return createStatement(resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return prepareStatement(sql, resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("prepareCall(String,int,int)");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return Collections.emptyMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("setTypeMap(Map<String,Class<?>>)");
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
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY) {
            throw new SQLException("Unsupported resultSetType " + resultSetType);
        }
        if (resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLException("Unsupported resultSetConcurrency " + resultSetConcurrency);
        }
        if (resultSetHoldability != ResultSet.CLOSE_CURSORS_AT_COMMIT) {
            throw new SQLException("Unsupported resultSetHoldability " + resultSetHoldability);
        }
        if (currentStatement != null && !currentStatement.isClosed()) {
            throw new SQLException("previous statement not closed");
        }
        currentStatement = new MockStatement(connectionProperties, null);
        return currentStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY) {
            throw new SQLException("Unsupported resultSetType " + resultSetType);
        }
        if (resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLException("Unsupported resultSetConcurrency " + resultSetConcurrency);
        }
        if (resultSetHoldability != ResultSet.CLOSE_CURSORS_AT_COMMIT) {
            throw new SQLException("Unsupported resultSetHoldability " + resultSetHoldability);
        }
        if (currentStatement != null && !currentStatement.isClosed()) {
            throw new SQLException("previous connection not closed");
        }
        currentStatement = new MockStatement(connectionProperties, sql);

        return (PreparedStatement)currentStatement;
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
        throw new UnsupportedOperationException("prepareStatement(String, int[])");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("prepare(String,String[])");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("createClob()");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("createBlob()");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("createNClob()");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("createSQLXML()");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        throw new UnsupportedOperationException("isValid()");
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
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return EMPTY_PROPERTIES;
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

    private static class MockDatabaseMetaData implements DatabaseMetaData {

        private String url;

        @Override
        public boolean allProceduresAreCallable() throws SQLException {
            return false;
        }

        @Override
        public boolean allTablesAreSelectable() throws SQLException {
            return true;
        }

        private void setURL(String url) {
            this.url = url;
        }
        @Override
        public String getURL() throws SQLException {
            return url;
        }

        @Override
        public String getUserName() throws SQLException {
            return "";
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return true;
        }

        @Override
        public boolean nullsAreSortedHigh() throws SQLException {
            return false;
        }

        @Override
        public boolean nullsAreSortedLow() throws SQLException {
            return true;
        }

        @Override
        public boolean nullsAreSortedAtStart() throws SQLException {
            return true;
        }

        @Override
        public boolean nullsAreSortedAtEnd() throws SQLException {
            return false;
        }

        @Override
        public String getDatabaseProductName() throws SQLException {
            return "Mock Database";

        }

        @Override
        public String getDatabaseProductVersion() throws SQLException {
            return "0.1";
        }

        @Override
        public String getDriverName() throws SQLException {
            return "MockDriver";

        }

        @Override
        public String getDriverVersion() throws SQLException {
            return "0.1";

        }

        @Override
        public int getDriverMajorVersion() {
            return 1;

        }

        @Override
        public int getDriverMinorVersion() {
            return 0;
        }

        @Override
        public boolean usesLocalFiles() throws SQLException {
            return true;
        }

        @Override
        public boolean usesLocalFilePerTable() throws SQLException {
            return true;
        }

        @Override
        public boolean supportsMixedCaseIdentifiers() throws SQLException {
            return false;

        }

        @Override
        public boolean storesUpperCaseIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesUpperCaseIdentifiers");

        }

        @Override
        public boolean storesLowerCaseIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesLowerCaseIdentifiers");

        }

        @Override
        public boolean storesMixedCaseIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesMixedCaseIdentifiers");

        }

        @Override
        public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("supportsMixedCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesUpperCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesLowerCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
            throw new UnsupportedOperationException("storesMixedCaseQuotedIdentifiers");

        }

        @Override
        public String getIdentifierQuoteString() throws SQLException {
            throw new UnsupportedOperationException("getIdentifierQuoteString");

        }

        @Override
        public String getSQLKeywords() throws SQLException {
            throw new UnsupportedOperationException("getSQLKeywords");

        }

        @Override
        public String getNumericFunctions() throws SQLException {
            throw new UnsupportedOperationException("getNumericFunctions");

        }

        @Override
        public String getStringFunctions() throws SQLException {
            throw new UnsupportedOperationException("getStringFunctions");

        }

        @Override
        public String getSystemFunctions() throws SQLException {
            throw new UnsupportedOperationException("getSystemFunctions");

        }

        @Override
        public String getTimeDateFunctions() throws SQLException {
            throw new UnsupportedOperationException("getTimeDateFunctions");

        }

        @Override
        public String getSearchStringEscape() throws SQLException {
            throw new UnsupportedOperationException("getSearchStringEscape");

        }

        @Override
        public String getExtraNameCharacters() throws SQLException {
            throw new UnsupportedOperationException("getExtraNameCharacters");

        }

        @Override
        public boolean supportsAlterTableWithAddColumn() throws SQLException {
            throw new UnsupportedOperationException("supportsAlterTableWithAddColumn");

        }

        @Override
        public boolean supportsAlterTableWithDropColumn() throws SQLException {
            throw new UnsupportedOperationException("supportsAlterTableWithDropColumn");

        }

        @Override
        public boolean supportsColumnAliasing() throws SQLException {
            throw new UnsupportedOperationException("supportsColumnAliasing");

        }

        @Override
        public boolean nullPlusNonNullIsNull() throws SQLException {
            throw new UnsupportedOperationException("nullPlusNonNullIsNull");

        }

        @Override
        public boolean supportsConvert() throws SQLException {
            throw new UnsupportedOperationException("supportsConvert");

        }

        @Override
        public boolean supportsConvert(int fromType, int toType) throws SQLException {
            throw new UnsupportedOperationException("supportsConvert");

        }

        @Override
        public boolean supportsTableCorrelationNames() throws SQLException {
            throw new UnsupportedOperationException("supportsTableCorrelationNames");

        }

        @Override
        public boolean supportsDifferentTableCorrelationNames() throws SQLException {
            throw new UnsupportedOperationException("supportsDifferentTableCorrelationNames");

        }

        @Override
        public boolean supportsExpressionsInOrderBy() throws SQLException {
            throw new UnsupportedOperationException("supportsExpressionsInOrderBy");

        }

        @Override
        public boolean supportsOrderByUnrelated() throws SQLException {
            throw new UnsupportedOperationException("supportsOrderByUnrelated");

        }

        @Override
        public boolean supportsGroupBy() throws SQLException {
            throw new UnsupportedOperationException("supportsGroupBy");

        }

        @Override
        public boolean supportsGroupByUnrelated() throws SQLException {
            throw new UnsupportedOperationException("supportsGroupByUnrelated");

        }

        @Override
        public boolean supportsGroupByBeyondSelect() throws SQLException {
            throw new UnsupportedOperationException("supportsGroupByBeyondSelect");

        }

        @Override
        public boolean supportsLikeEscapeClause() throws SQLException {
            throw new UnsupportedOperationException("supportsLikeEscapeClause");

        }

        @Override
        public boolean supportsMultipleResultSets() throws SQLException {
            throw new UnsupportedOperationException("supportsMultipleResultSets");

        }

        @Override
        public boolean supportsMultipleTransactions() throws SQLException {
            throw new UnsupportedOperationException("supportsMultipleTransactions");

        }

        @Override
        public boolean supportsNonNullableColumns() throws SQLException {
            throw new UnsupportedOperationException("supportsNonNullableColumns");

        }

        @Override
        public boolean supportsMinimumSQLGrammar() throws SQLException {
            throw new UnsupportedOperationException("supportsMinimumSQLGrammar");

        }

        @Override
        public boolean supportsCoreSQLGrammar() throws SQLException {
            throw new UnsupportedOperationException("supportsCoreSQLGrammar");

        }

        @Override
        public boolean supportsExtendedSQLGrammar() throws SQLException {
            throw new UnsupportedOperationException("supportsExtendedSQLGrammar");

        }

        @Override
        public boolean supportsANSI92EntryLevelSQL() throws SQLException {
            throw new UnsupportedOperationException("supportsANSI92EntryLevelSQL");

        }

        @Override
        public boolean supportsANSI92IntermediateSQL() throws SQLException {
            throw new UnsupportedOperationException("supportsANSI92IntermediateSQL");

        }

        @Override
        public boolean supportsANSI92FullSQL() throws SQLException {
            throw new UnsupportedOperationException("supportsANSI92FullSQL");

        }

        @Override
        public boolean supportsIntegrityEnhancementFacility() throws SQLException {
            throw new UnsupportedOperationException("supportsIntegrityEnhancementFacility");

        }

        @Override
        public boolean supportsOuterJoins() throws SQLException {
            throw new UnsupportedOperationException("supportsOuterJoins");

        }

        @Override
        public boolean supportsFullOuterJoins() throws SQLException {
            throw new UnsupportedOperationException("supportsFullOuterJoins");

        }

        @Override
        public boolean supportsLimitedOuterJoins() throws SQLException {
            throw new UnsupportedOperationException("supportsLimitedOuterJoins");

        }

        @Override
        public String getSchemaTerm() throws SQLException {
            throw new UnsupportedOperationException("getSchemaTerm");

        }

        @Override
        public String getProcedureTerm() throws SQLException {
            throw new UnsupportedOperationException("getProcedureTerm");

        }

        @Override
        public String getCatalogTerm() throws SQLException {
            throw new UnsupportedOperationException("getCatalogTerm");

        }

        @Override
        public boolean isCatalogAtStart() throws SQLException {
            throw new UnsupportedOperationException("isCatalogAtStart");

        }

        @Override
        public String getCatalogSeparator() throws SQLException {
            throw new UnsupportedOperationException("getCatalogSeparator");

        }

        @Override
        public boolean supportsSchemasInDataManipulation() throws SQLException {
            throw new UnsupportedOperationException("supportsSchemasInDataManipulation");

        }

        @Override
        public boolean supportsSchemasInProcedureCalls() throws SQLException {
            throw new UnsupportedOperationException("supportsSchemasInProcedureCalls");

        }

        @Override
        public boolean supportsSchemasInTableDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsSchemasInTableDefinitions");

        }

        @Override
        public boolean supportsSchemasInIndexDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsSchemasInIndexDefinitions");

        }

        @Override
        public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsSchemasInPrivilegeDefinitions");

        }

        @Override
        public boolean supportsCatalogsInDataManipulation() throws SQLException {
            throw new UnsupportedOperationException("supportsCatalogsInDataManipulation");

        }

        @Override
        public boolean supportsCatalogsInProcedureCalls() throws SQLException {
            throw new UnsupportedOperationException("supportsCatalogsInProcedureCalls");

        }

        @Override
        public boolean supportsCatalogsInTableDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsCatalogsInTableDefinitions");

        }

        @Override
        public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsCatalogsInIndexDefinitions");

        }

        @Override
        public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
            throw new UnsupportedOperationException("supportsCatalogsInPrivilegeDefinitions");

        }

        @Override
        public boolean supportsPositionedDelete() throws SQLException {
            throw new UnsupportedOperationException("supportsPositionedDelete");

        }

        @Override
        public boolean supportsPositionedUpdate() throws SQLException {
            throw new UnsupportedOperationException("supportsPositionedUpdate");

        }

        @Override
        public boolean supportsSelectForUpdate() throws SQLException {
            throw new UnsupportedOperationException("supportsSelectForUpdate");

        }

        @Override
        public boolean supportsStoredProcedures() throws SQLException {
            throw new UnsupportedOperationException("supportsStoredProcedures");

        }

        @Override
        public boolean supportsSubqueriesInComparisons() throws SQLException {
            throw new UnsupportedOperationException("supportsSubqueriesInComparisons");

        }

        @Override
        public boolean supportsSubqueriesInExists() throws SQLException {
            throw new UnsupportedOperationException("supportsSubqueriesInExists");

        }

        @Override
        public boolean supportsSubqueriesInIns() throws SQLException {
            throw new UnsupportedOperationException("supportsSubqueriesInIns");

        }

        @Override
        public boolean supportsSubqueriesInQuantifieds() throws SQLException {
            throw new UnsupportedOperationException("supportsSubqueriesInQuantifieds");

        }

        @Override
        public boolean supportsCorrelatedSubqueries() throws SQLException {
            throw new UnsupportedOperationException("supportsCorrelatedSubqueries");

        }

        @Override
        public boolean supportsUnion() throws SQLException {
            throw new UnsupportedOperationException("supportsUnion");

        }

        @Override
        public boolean supportsUnionAll() throws SQLException {
            throw new UnsupportedOperationException("supportsUnionAll");

        }

        @Override
        public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
            throw new UnsupportedOperationException("supportsOpenCursorsAcrossCommit");

        }

        @Override
        public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
            throw new UnsupportedOperationException("supportsOpenCursorsAcrossRollback");

        }

        @Override
        public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
            throw new UnsupportedOperationException("supportsOpenStatementsAcrossCommit");

        }

        @Override
        public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
            throw new UnsupportedOperationException("supportsOpenStatementsAcrossRollback");

        }

        @Override
        public int getMaxBinaryLiteralLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxBinaryLiteralLength");

        }

        @Override
        public int getMaxCharLiteralLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxCharLiteralLength");

        }

        @Override
        public int getMaxColumnNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnNameLength");

        }

        @Override
        public int getMaxColumnsInGroupBy() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnsInGroupBy");

        }

        @Override
        public int getMaxColumnsInIndex() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnsInIndex");

        }

        @Override
        public int getMaxColumnsInOrderBy() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnsInOrderBy");

        }

        @Override
        public int getMaxColumnsInSelect() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnsInSelect");

        }

        @Override
        public int getMaxColumnsInTable() throws SQLException {
            throw new UnsupportedOperationException("getMaxColumnsInTable");

        }

        @Override
        public int getMaxConnections() throws SQLException {
            throw new UnsupportedOperationException("getMaxConnections");

        }

        @Override
        public int getMaxCursorNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxCursorNameLength");

        }

        @Override
        public int getMaxIndexLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxIndexLength");

        }

        @Override
        public int getMaxSchemaNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxSchemaNameLength");

        }

        @Override
        public int getMaxProcedureNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxProcedureNameLength");

        }

        @Override
        public int getMaxCatalogNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxCatalogNameLength");

        }

        @Override
        public int getMaxRowSize() throws SQLException {
            throw new UnsupportedOperationException("getMaxRowSize");

        }

        @Override
        public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
            throw new UnsupportedOperationException("doesMaxRowSizeIncludeBlobs");

        }

        @Override
        public int getMaxStatementLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxStatementLength");

        }

        @Override
        public int getMaxStatements() throws SQLException {
            throw new UnsupportedOperationException("getMaxStatements");

        }

        @Override
        public int getMaxTableNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxTableNameLength");

        }

        @Override
        public int getMaxTablesInSelect() throws SQLException {
            throw new UnsupportedOperationException("getMaxTablesInSelect");

        }

        @Override
        public int getMaxUserNameLength() throws SQLException {
            throw new UnsupportedOperationException("getMaxUserNameLength");

        }

        @Override
        public int getDefaultTransactionIsolation() throws SQLException {
            throw new UnsupportedOperationException("getDefaultTransactionIsolation");

        }

        @Override
        public boolean supportsTransactions() throws SQLException {
            throw new UnsupportedOperationException("supportsTransactions");

        }

        @Override
        public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
            throw new UnsupportedOperationException("supportsTransactionIsolationLevel");

        }

        @Override
        public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
            throw new UnsupportedOperationException("supportsDataDefinitionAndDataManipulationTransactions");

        }

        @Override
        public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
            throw new UnsupportedOperationException("supportsDataManipulationTransactionsOnly");

        }

        @Override
        public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
            throw new UnsupportedOperationException("dataDefinitionCausesTransactionCommit");

        }

        @Override
        public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
            throw new UnsupportedOperationException("dataDefinitionIgnoredInTransactions");

        }

        @Override
        public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getProcedures");

        }

        @Override
        public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getProcedureColumns");

        }

        @Override
        public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
            throw new UnsupportedOperationException("getTables");

        }

        @Override
        public ResultSet getSchemas() throws SQLException {
            throw new UnsupportedOperationException("getSchemas");

        }

        @Override
        public ResultSet getCatalogs() throws SQLException {
            throw new UnsupportedOperationException("getCatalogs");

        }

        @Override
        public ResultSet getTableTypes() throws SQLException {
            throw new UnsupportedOperationException("getTableTypes");

        }

        @Override
        public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getColumns");

        }

        @Override
        public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getColumnPrivileges");

        }

        @Override
        public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getTablePrivileges");

        }

        @Override
        public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
            throw new UnsupportedOperationException("getBestRowIdentifier");

        }

        @Override
        public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
            throw new UnsupportedOperationException("getVersionColumns");

        }

        @Override
        public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
            throw new UnsupportedOperationException("getPrimaryKeys");

        }

        @Override
        public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
            throw new UnsupportedOperationException("getImportedKeys");

        }

        @Override
        public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
            throw new UnsupportedOperationException("getExportedKeys");

        }

        @Override
        public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
            throw new UnsupportedOperationException("getCrossReference");

        }

        @Override
        public ResultSet getTypeInfo() throws SQLException {
            throw new UnsupportedOperationException("getTypeInfo");

        }

        @Override
        public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
            throw new UnsupportedOperationException("getIndexInfo");

        }

        @Override
        public boolean supportsResultSetType(int type) throws SQLException {
            throw new UnsupportedOperationException("supportsResultSetType");

        }

        @Override
        public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
            throw new UnsupportedOperationException("supportsResultSetConcurrency");

        }

        @Override
        public boolean ownUpdatesAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("ownUpdatesAreVisible");

        }

        @Override
        public boolean ownDeletesAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("ownDeletesAreVisible");

        }

        @Override
        public boolean ownInsertsAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("ownInsertsAreVisible");

        }

        @Override
        public boolean othersUpdatesAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("othersUpdatesAreVisible");

        }

        @Override
        public boolean othersDeletesAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("othersDeletesAreVisible");

        }

        @Override
        public boolean othersInsertsAreVisible(int type) throws SQLException {
            throw new UnsupportedOperationException("othersInsertsAreVisible");

        }

        @Override
        public boolean updatesAreDetected(int type) throws SQLException {
            throw new UnsupportedOperationException("updatesAreDetected");

        }

        @Override
        public boolean deletesAreDetected(int type) throws SQLException {
            throw new UnsupportedOperationException("deletesAreDetected");

        }

        @Override
        public boolean insertsAreDetected(int type) throws SQLException {
            throw new UnsupportedOperationException("insertsAreDetected");

        }

        @Override
        public boolean supportsBatchUpdates() throws SQLException {
            throw new UnsupportedOperationException("supportsBatchUpdates");

        }

        @Override
        public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
            throw new UnsupportedOperationException("getUDTs");

        }

        @Override
        public Connection getConnection() throws SQLException {
            throw new UnsupportedOperationException("getConnection");

        }

        @Override
        public boolean supportsSavepoints() throws SQLException {
            throw new UnsupportedOperationException("supportsSavepoints");

        }

        @Override
        public boolean supportsNamedParameters() throws SQLException {
            throw new UnsupportedOperationException("supportsNamedParameters");

        }

        @Override
        public boolean supportsMultipleOpenResults() throws SQLException {
            throw new UnsupportedOperationException("supportsMultipleOpenResults");

        }

        @Override
        public boolean supportsGetGeneratedKeys() throws SQLException {
            throw new UnsupportedOperationException("supportsGetGeneratedKeys");

        }

        @Override
        public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getSuperTypes");

        }

        @Override
        public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getSuperTables");

        }

        @Override
        public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getAttributes");

        }

        @Override
        public boolean supportsResultSetHoldability(int holdability) throws SQLException {
            throw new UnsupportedOperationException("supportsResultSetHoldability");

        }

        @Override
        public int getResultSetHoldability() throws SQLException {
            throw new UnsupportedOperationException("getResultSetHoldability");

        }

        @Override
        public int getDatabaseMajorVersion() throws SQLException {
            throw new UnsupportedOperationException("getDatabaseMajorVersion");

        }

        @Override
        public int getDatabaseMinorVersion() throws SQLException {
            throw new UnsupportedOperationException("getDatabaseMinorVersion");

        }

        @Override
        public int getJDBCMajorVersion() throws SQLException {
            throw new UnsupportedOperationException("getJDBCMajorVersion");

        }

        @Override
        public int getJDBCMinorVersion() throws SQLException {
            throw new UnsupportedOperationException("getJDBCMinorVersion");

        }

        @Override
        public int getSQLStateType() throws SQLException {
            throw new UnsupportedOperationException("getSQLStateType");

        }

        @Override
        public boolean locatorsUpdateCopy() throws SQLException {
            throw new UnsupportedOperationException("locatorsUpdateCopy");

        }

        @Override
        public boolean supportsStatementPooling() throws SQLException {
            throw new UnsupportedOperationException("supportsStatementPooling");

        }

        @Override
        public RowIdLifetime getRowIdLifetime() throws SQLException {
            throw new UnsupportedOperationException("getRowIdLifetime");

        }

        @Override
        public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
            throw new UnsupportedOperationException("getSchemas");

        }

        @Override
        public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
            throw new UnsupportedOperationException("supportsStoredFunctionsUsingCallSyntax");

        }

        @Override
        public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
            throw new UnsupportedOperationException("autoCommitFailureClosesAllResultSets");

        }

        @Override
        public ResultSet getClientInfoProperties() throws SQLException {
            throw new UnsupportedOperationException("getClientInfoProperties");

        }

        @Override
        public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getFunctions");

        }

        @Override
        public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getFunctionColumns");

        }

        @Override
        public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
            throw new UnsupportedOperationException("getPseudoColumns");

        }

        @Override
        public boolean generatedKeyAlwaysReturned() throws SQLException {
            throw new UnsupportedOperationException("generatedKeyAlwaysReturned");

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
}
