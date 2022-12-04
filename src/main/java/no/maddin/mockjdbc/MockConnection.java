package no.maddin.mockjdbc;

import java.sql.*;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection {
    private final Properties connectionProperties = new Properties();
    private Statement currentStatement;
    private final MockDatabaseMetaData metadata =new MockDatabaseMetaData();
    private final Properties clientInfo = new Properties();

    public MockConnection(String url, Properties info) {
        addPropsFromUrl(url);
        this.connectionProperties.putAll(info);
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
    public CallableStatement prepareCall(String sql) {
        throw new UnsupportedOperationException("prepareCall(String)");
    }

    @Override
    public String nativeSQL(String sql) {
        throw new UnsupportedOperationException("nativeSQL(String)");
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        // do nothing
    }

    @Override
    public boolean getAutoCommit() {
        throw new UnsupportedOperationException("getAutoCommit()");
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("commit()");
    }

    @Override
    public void rollback() {
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
    public DatabaseMetaData getMetaData() {
        return metadata;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        throw new UnsupportedOperationException("setReadOnly");
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void setCatalog(String catalog) {
        throw new UnsupportedOperationException("setCatalog(String)");
    }

    @Override
    public String getCatalog() {
        throw new UnsupportedOperationException("getCatalog()");
    }

    @Override
    public void setTransactionIsolation(int level) {
        throw new UnsupportedOperationException("steTransactionIsolation(int)");
    }

    @Override
    public int getTransactionIsolation() {
        throw new UnsupportedOperationException("getTransactionIsolation");
    }

    @Override
    public SQLWarning getWarnings() {
        throw new UnsupportedOperationException("getWarnings");
    }

    @Override
    public void clearWarnings() {
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
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) {
        throw new UnsupportedOperationException("prepareCall(String,int,int)");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() {
        return Collections.emptyMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) {
        throw new UnsupportedOperationException("setTypeMap(Map<String,Class<?>>)");
    }

    @Override
    public void setHoldability(int holdability) {
        throw new UnsupportedOperationException("setHoldability(int)");
    }

    @Override
    public int getHoldability() {
        throw new UnsupportedOperationException("getHoldability");
    }

    @Override
    public Savepoint setSavepoint() {
        throw new UnsupportedOperationException("setSavepoint");
    }

    @Override
    public Savepoint setSavepoint(String name) {
        throw new UnsupportedOperationException("setSavepoint(String)");
    }

    @Override
    public void rollback(Savepoint savepoint) {
        throw new UnsupportedOperationException("rollback(Savepoint)");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) {
        throw new UnsupportedOperationException("releaseSavepoint(Savepoint");
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
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) {
        throw new UnsupportedOperationException("prepareCall()");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) {
        throw new UnsupportedOperationException("prepareStatement");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) {
        throw new UnsupportedOperationException("prepareStatement(String, int[])");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) {
        throw new UnsupportedOperationException("prepare(String,String[])");
    }

    @Override
    public Clob createClob() {
        throw new UnsupportedOperationException("createClob()");
    }

    @Override
    public Blob createBlob() {
        throw new UnsupportedOperationException("createBlob()");
    }

    @Override
    public NClob createNClob() {
        throw new UnsupportedOperationException("createNClob()");
    }

    @Override
    public SQLXML createSQLXML() {
        throw new UnsupportedOperationException("createSQLXML()");
    }

    @Override
    public boolean isValid(int timeout) {
        throw new UnsupportedOperationException("isValid()");
    }

    @Override
    public void setClientInfo(String name, String value) {
        clientInfo.put(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) {
        clientInfo.clear();
        clientInfo.putAll(properties);
    }

    @Override
    public String getClientInfo(String name) {
        return clientInfo.getProperty(name);
    }

    @Override
    public Properties getClientInfo() {
        return clientInfo;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) {
        throw new UnsupportedOperationException("createArrayOf");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) {
        throw new UnsupportedOperationException("createStruct");
    }

    @Override
    public void setSchema(String schema) {
        throw new UnsupportedOperationException("setSchema");
    }

    @Override
    public String getSchema() {
        throw new UnsupportedOperationException("getSchema");
    }

    @Override
    public void abort(Executor executor) {
        throw new UnsupportedOperationException("abort");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) {
        throw new UnsupportedOperationException("setNetworkTimeout");
    }

    @Override
    public int getNetworkTimeout() {
        throw new UnsupportedOperationException("getNetworkTimeout");
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException("unwrap");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException("isWrapperFor");
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    private static class MockDatabaseMetaData implements DatabaseMetaData {

        private String url;

        @Override
        public boolean allProceduresAreCallable() {
            return false;
        }

        @Override
        public boolean allTablesAreSelectable() {
            return true;
        }

        private void setURL(String url) {
            this.url = url;
        }
        @Override
        public String getURL() {
            return url;
        }

        @Override
        public String getUserName() {
            return "";
        }

        @Override
        public boolean isReadOnly() {
            return true;
        }

        @Override
        public boolean nullsAreSortedHigh() {
            return false;
        }

        @Override
        public boolean nullsAreSortedLow() {
            return true;
        }

        @Override
        public boolean nullsAreSortedAtStart() {
            return true;
        }

        @Override
        public boolean nullsAreSortedAtEnd() {
            return false;
        }

        @Override
        public String getDatabaseProductName() {
            return "Mock Database";

        }

        @Override
        public String getDatabaseProductVersion() {
            return "0.1";
        }

        @Override
        public String getDriverName() {
            return "MockDriver";

        }

        @Override
        public String getDriverVersion() {
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
        public boolean usesLocalFiles() {
            return true;
        }

        @Override
        public boolean usesLocalFilePerTable() {
            return true;
        }

        @Override
        public boolean supportsMixedCaseIdentifiers() {
            return false;

        }

        @Override
        public boolean storesUpperCaseIdentifiers() {
            throw new UnsupportedOperationException("storesUpperCaseIdentifiers");

        }

        @Override
        public boolean storesLowerCaseIdentifiers() {
            throw new UnsupportedOperationException("storesLowerCaseIdentifiers");

        }

        @Override
        public boolean storesMixedCaseIdentifiers() {
            throw new UnsupportedOperationException("storesMixedCaseIdentifiers");

        }

        @Override
        public boolean supportsMixedCaseQuotedIdentifiers() {
            throw new UnsupportedOperationException("supportsMixedCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesUpperCaseQuotedIdentifiers() {
            throw new UnsupportedOperationException("storesUpperCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesLowerCaseQuotedIdentifiers() {
            throw new UnsupportedOperationException("storesLowerCaseQuotedIdentifiers");

        }

        @Override
        public boolean storesMixedCaseQuotedIdentifiers() {
            throw new UnsupportedOperationException("storesMixedCaseQuotedIdentifiers");

        }

        @Override
        public String getIdentifierQuoteString() {
            throw new UnsupportedOperationException("getIdentifierQuoteString");

        }

        @Override
        public String getSQLKeywords() {
            throw new UnsupportedOperationException("getSQLKeywords");

        }

        @Override
        public String getNumericFunctions() {
            throw new UnsupportedOperationException("getNumericFunctions");

        }

        @Override
        public String getStringFunctions() {
            throw new UnsupportedOperationException("getStringFunctions");

        }

        @Override
        public String getSystemFunctions() {
            throw new UnsupportedOperationException("getSystemFunctions");

        }

        @Override
        public String getTimeDateFunctions() {
            throw new UnsupportedOperationException("getTimeDateFunctions");

        }

        @Override
        public String getSearchStringEscape() {
            throw new UnsupportedOperationException("getSearchStringEscape");

        }

        @Override
        public String getExtraNameCharacters() {
            throw new UnsupportedOperationException("getExtraNameCharacters");

        }

        @Override
        public boolean supportsAlterTableWithAddColumn() {
            throw new UnsupportedOperationException("supportsAlterTableWithAddColumn");

        }

        @Override
        public boolean supportsAlterTableWithDropColumn() {
            throw new UnsupportedOperationException("supportsAlterTableWithDropColumn");

        }

        @Override
        public boolean supportsColumnAliasing() {
            throw new UnsupportedOperationException("supportsColumnAliasing");

        }

        @Override
        public boolean nullPlusNonNullIsNull() {
            throw new UnsupportedOperationException("nullPlusNonNullIsNull");

        }

        @Override
        public boolean supportsConvert() {
            throw new UnsupportedOperationException("supportsConvert");

        }

        @Override
        public boolean supportsConvert(int fromType, int toType) {
            throw new UnsupportedOperationException("supportsConvert");

        }

        @Override
        public boolean supportsTableCorrelationNames() {
            throw new UnsupportedOperationException("supportsTableCorrelationNames");

        }

        @Override
        public boolean supportsDifferentTableCorrelationNames() {
            throw new UnsupportedOperationException("supportsDifferentTableCorrelationNames");

        }

        @Override
        public boolean supportsExpressionsInOrderBy() {
            throw new UnsupportedOperationException("supportsExpressionsInOrderBy");

        }

        @Override
        public boolean supportsOrderByUnrelated() {
            throw new UnsupportedOperationException("supportsOrderByUnrelated");

        }

        @Override
        public boolean supportsGroupBy() {
            throw new UnsupportedOperationException("supportsGroupBy");

        }

        @Override
        public boolean supportsGroupByUnrelated() {
            throw new UnsupportedOperationException("supportsGroupByUnrelated");

        }

        @Override
        public boolean supportsGroupByBeyondSelect() {
            throw new UnsupportedOperationException("supportsGroupByBeyondSelect");

        }

        @Override
        public boolean supportsLikeEscapeClause() {
            throw new UnsupportedOperationException("supportsLikeEscapeClause");

        }

        @Override
        public boolean supportsMultipleResultSets() {
            throw new UnsupportedOperationException("supportsMultipleResultSets");

        }

        @Override
        public boolean supportsMultipleTransactions() {
            throw new UnsupportedOperationException("supportsMultipleTransactions");

        }

        @Override
        public boolean supportsNonNullableColumns() {
            throw new UnsupportedOperationException("supportsNonNullableColumns");

        }

        @Override
        public boolean supportsMinimumSQLGrammar() {
            throw new UnsupportedOperationException("supportsMinimumSQLGrammar");

        }

        @Override
        public boolean supportsCoreSQLGrammar() {
            throw new UnsupportedOperationException("supportsCoreSQLGrammar");

        }

        @Override
        public boolean supportsExtendedSQLGrammar() {
            throw new UnsupportedOperationException("supportsExtendedSQLGrammar");

        }

        @Override
        public boolean supportsANSI92EntryLevelSQL() {
            throw new UnsupportedOperationException("supportsANSI92EntryLevelSQL");

        }

        @Override
        public boolean supportsANSI92IntermediateSQL() {
            throw new UnsupportedOperationException("supportsANSI92IntermediateSQL");

        }

        @Override
        public boolean supportsANSI92FullSQL() {
            throw new UnsupportedOperationException("supportsANSI92FullSQL");

        }

        @Override
        public boolean supportsIntegrityEnhancementFacility() {
            throw new UnsupportedOperationException("supportsIntegrityEnhancementFacility");

        }

        @Override
        public boolean supportsOuterJoins() {
            throw new UnsupportedOperationException("supportsOuterJoins");

        }

        @Override
        public boolean supportsFullOuterJoins() {
            throw new UnsupportedOperationException("supportsFullOuterJoins");

        }

        @Override
        public boolean supportsLimitedOuterJoins() {
            throw new UnsupportedOperationException("supportsLimitedOuterJoins");

        }

        @Override
        public String getSchemaTerm() {
            throw new UnsupportedOperationException("getSchemaTerm");

        }

        @Override
        public String getProcedureTerm() {
            throw new UnsupportedOperationException("getProcedureTerm");

        }

        @Override
        public String getCatalogTerm() {
            throw new UnsupportedOperationException("getCatalogTerm");

        }

        @Override
        public boolean isCatalogAtStart() {
            throw new UnsupportedOperationException("isCatalogAtStart");

        }

        @Override
        public String getCatalogSeparator() {
            throw new UnsupportedOperationException("getCatalogSeparator");

        }

        @Override
        public boolean supportsSchemasInDataManipulation() {
            throw new UnsupportedOperationException("supportsSchemasInDataManipulation");

        }

        @Override
        public boolean supportsSchemasInProcedureCalls() {
            throw new UnsupportedOperationException("supportsSchemasInProcedureCalls");

        }

        @Override
        public boolean supportsSchemasInTableDefinitions() {
            throw new UnsupportedOperationException("supportsSchemasInTableDefinitions");

        }

        @Override
        public boolean supportsSchemasInIndexDefinitions() {
            throw new UnsupportedOperationException("supportsSchemasInIndexDefinitions");

        }

        @Override
        public boolean supportsSchemasInPrivilegeDefinitions() {
            throw new UnsupportedOperationException("supportsSchemasInPrivilegeDefinitions");

        }

        @Override
        public boolean supportsCatalogsInDataManipulation() {
            throw new UnsupportedOperationException("supportsCatalogsInDataManipulation");

        }

        @Override
        public boolean supportsCatalogsInProcedureCalls() {
            throw new UnsupportedOperationException("supportsCatalogsInProcedureCalls");

        }

        @Override
        public boolean supportsCatalogsInTableDefinitions() {
            throw new UnsupportedOperationException("supportsCatalogsInTableDefinitions");

        }

        @Override
        public boolean supportsCatalogsInIndexDefinitions() {
            throw new UnsupportedOperationException("supportsCatalogsInIndexDefinitions");

        }

        @Override
        public boolean supportsCatalogsInPrivilegeDefinitions() {
            throw new UnsupportedOperationException("supportsCatalogsInPrivilegeDefinitions");

        }

        @Override
        public boolean supportsPositionedDelete() {
            throw new UnsupportedOperationException("supportsPositionedDelete");

        }

        @Override
        public boolean supportsPositionedUpdate() {
            throw new UnsupportedOperationException("supportsPositionedUpdate");

        }

        @Override
        public boolean supportsSelectForUpdate() {
            throw new UnsupportedOperationException("supportsSelectForUpdate");

        }

        @Override
        public boolean supportsStoredProcedures() {
            throw new UnsupportedOperationException("supportsStoredProcedures");

        }

        @Override
        public boolean supportsSubqueriesInComparisons() {
            throw new UnsupportedOperationException("supportsSubqueriesInComparisons");

        }

        @Override
        public boolean supportsSubqueriesInExists() {
            throw new UnsupportedOperationException("supportsSubqueriesInExists");

        }

        @Override
        public boolean supportsSubqueriesInIns() {
            throw new UnsupportedOperationException("supportsSubqueriesInIns");

        }

        @Override
        public boolean supportsSubqueriesInQuantifieds() {
            throw new UnsupportedOperationException("supportsSubqueriesInQuantifieds");

        }

        @Override
        public boolean supportsCorrelatedSubqueries() {
            throw new UnsupportedOperationException("supportsCorrelatedSubqueries");

        }

        @Override
        public boolean supportsUnion() {
            throw new UnsupportedOperationException("supportsUnion");

        }

        @Override
        public boolean supportsUnionAll() {
            throw new UnsupportedOperationException("supportsUnionAll");

        }

        @Override
        public boolean supportsOpenCursorsAcrossCommit() {
            throw new UnsupportedOperationException("supportsOpenCursorsAcrossCommit");

        }

        @Override
        public boolean supportsOpenCursorsAcrossRollback() {
            throw new UnsupportedOperationException("supportsOpenCursorsAcrossRollback");

        }

        @Override
        public boolean supportsOpenStatementsAcrossCommit() {
            throw new UnsupportedOperationException("supportsOpenStatementsAcrossCommit");

        }

        @Override
        public boolean supportsOpenStatementsAcrossRollback() {
            throw new UnsupportedOperationException("supportsOpenStatementsAcrossRollback");

        }

        @Override
        public int getMaxBinaryLiteralLength() {
            throw new UnsupportedOperationException("getMaxBinaryLiteralLength");

        }

        @Override
        public int getMaxCharLiteralLength() {
            throw new UnsupportedOperationException("getMaxCharLiteralLength");

        }

        @Override
        public int getMaxColumnNameLength() {
            throw new UnsupportedOperationException("getMaxColumnNameLength");

        }

        @Override
        public int getMaxColumnsInGroupBy() {
            throw new UnsupportedOperationException("getMaxColumnsInGroupBy");

        }

        @Override
        public int getMaxColumnsInIndex() {
            throw new UnsupportedOperationException("getMaxColumnsInIndex");

        }

        @Override
        public int getMaxColumnsInOrderBy() {
            throw new UnsupportedOperationException("getMaxColumnsInOrderBy");

        }

        @Override
        public int getMaxColumnsInSelect() {
            throw new UnsupportedOperationException("getMaxColumnsInSelect");

        }

        @Override
        public int getMaxColumnsInTable() {
            throw new UnsupportedOperationException("getMaxColumnsInTable");

        }

        @Override
        public int getMaxConnections() {
            throw new UnsupportedOperationException("getMaxConnections");

        }

        @Override
        public int getMaxCursorNameLength() {
            throw new UnsupportedOperationException("getMaxCursorNameLength");

        }

        @Override
        public int getMaxIndexLength() {
            throw new UnsupportedOperationException("getMaxIndexLength");

        }

        @Override
        public int getMaxSchemaNameLength() {
            throw new UnsupportedOperationException("getMaxSchemaNameLength");

        }

        @Override
        public int getMaxProcedureNameLength() {
            throw new UnsupportedOperationException("getMaxProcedureNameLength");

        }

        @Override
        public int getMaxCatalogNameLength() {
            throw new UnsupportedOperationException("getMaxCatalogNameLength");

        }

        @Override
        public int getMaxRowSize() {
            throw new UnsupportedOperationException("getMaxRowSize");

        }

        @Override
        public boolean doesMaxRowSizeIncludeBlobs() {
            throw new UnsupportedOperationException("doesMaxRowSizeIncludeBlobs");

        }

        @Override
        public int getMaxStatementLength() {
            throw new UnsupportedOperationException("getMaxStatementLength");

        }

        @Override
        public int getMaxStatements() {
            throw new UnsupportedOperationException("getMaxStatements");

        }

        @Override
        public int getMaxTableNameLength() {
            throw new UnsupportedOperationException("getMaxTableNameLength");

        }

        @Override
        public int getMaxTablesInSelect() {
            throw new UnsupportedOperationException("getMaxTablesInSelect");

        }

        @Override
        public int getMaxUserNameLength() {
            throw new UnsupportedOperationException("getMaxUserNameLength");

        }

        @Override
        public int getDefaultTransactionIsolation() {
            throw new UnsupportedOperationException("getDefaultTransactionIsolation");

        }

        @Override
        public boolean supportsTransactions() {
            throw new UnsupportedOperationException("supportsTransactions");

        }

        @Override
        public boolean supportsTransactionIsolationLevel(int level) {
            throw new UnsupportedOperationException("supportsTransactionIsolationLevel");

        }

        @Override
        public boolean supportsDataDefinitionAndDataManipulationTransactions() {
            throw new UnsupportedOperationException("supportsDataDefinitionAndDataManipulationTransactions");

        }

        @Override
        public boolean supportsDataManipulationTransactionsOnly() {
            throw new UnsupportedOperationException("supportsDataManipulationTransactionsOnly");

        }

        @Override
        public boolean dataDefinitionCausesTransactionCommit() {
            throw new UnsupportedOperationException("dataDefinitionCausesTransactionCommit");

        }

        @Override
        public boolean dataDefinitionIgnoredInTransactions() {
            throw new UnsupportedOperationException("dataDefinitionIgnoredInTransactions");

        }

        @Override
        public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) {
            throw new UnsupportedOperationException("getProcedures");

        }

        @Override
        public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) {
            throw new UnsupportedOperationException("getProcedureColumns");

        }

        @Override
        public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
            throw new UnsupportedOperationException("getTables");

        }

        @Override
        public ResultSet getSchemas() {
            throw new UnsupportedOperationException("getSchemas");

        }

        @Override
        public ResultSet getCatalogs() {
            throw new UnsupportedOperationException("getCatalogs");

        }

        @Override
        public ResultSet getTableTypes() {
            throw new UnsupportedOperationException("getTableTypes");

        }

        @Override
        public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
            throw new UnsupportedOperationException("getColumns");

        }

        @Override
        public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) {
            throw new UnsupportedOperationException("getColumnPrivileges");

        }

        @Override
        public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) {
            throw new UnsupportedOperationException("getTablePrivileges");

        }

        @Override
        public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) {
            throw new UnsupportedOperationException("getBestRowIdentifier");

        }

        @Override
        public ResultSet getVersionColumns(String catalog, String schema, String table) {
            throw new UnsupportedOperationException("getVersionColumns");

        }

        @Override
        public ResultSet getPrimaryKeys(String catalog, String schema, String table) {
            throw new UnsupportedOperationException("getPrimaryKeys");

        }

        @Override
        public ResultSet getImportedKeys(String catalog, String schema, String table) {
            throw new UnsupportedOperationException("getImportedKeys");

        }

        @Override
        public ResultSet getExportedKeys(String catalog, String schema, String table) {
            throw new UnsupportedOperationException("getExportedKeys");

        }

        @Override
        public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) {
            throw new UnsupportedOperationException("getCrossReference");

        }

        @Override
        public ResultSet getTypeInfo() {
            throw new UnsupportedOperationException("getTypeInfo");

        }

        @Override
        public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) {
            throw new UnsupportedOperationException("getIndexInfo");

        }

        @Override
        public boolean supportsResultSetType(int type) {
            throw new UnsupportedOperationException("supportsResultSetType");

        }

        @Override
        public boolean supportsResultSetConcurrency(int type, int concurrency) {
            throw new UnsupportedOperationException("supportsResultSetConcurrency");

        }

        @Override
        public boolean ownUpdatesAreVisible(int type) {
            throw new UnsupportedOperationException("ownUpdatesAreVisible");

        }

        @Override
        public boolean ownDeletesAreVisible(int type) {
            throw new UnsupportedOperationException("ownDeletesAreVisible");

        }

        @Override
        public boolean ownInsertsAreVisible(int type) {
            throw new UnsupportedOperationException("ownInsertsAreVisible");

        }

        @Override
        public boolean othersUpdatesAreVisible(int type) {
            throw new UnsupportedOperationException("othersUpdatesAreVisible");

        }

        @Override
        public boolean othersDeletesAreVisible(int type) {
            throw new UnsupportedOperationException("othersDeletesAreVisible");

        }

        @Override
        public boolean othersInsertsAreVisible(int type) {
            throw new UnsupportedOperationException("othersInsertsAreVisible");

        }

        @Override
        public boolean updatesAreDetected(int type) {
            throw new UnsupportedOperationException("updatesAreDetected");

        }

        @Override
        public boolean deletesAreDetected(int type) {
            throw new UnsupportedOperationException("deletesAreDetected");

        }

        @Override
        public boolean insertsAreDetected(int type) {
            throw new UnsupportedOperationException("insertsAreDetected");

        }

        @Override
        public boolean supportsBatchUpdates() {
            throw new UnsupportedOperationException("supportsBatchUpdates");

        }

        @Override
        public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) {
            throw new UnsupportedOperationException("getUDTs");

        }

        @Override
        public Connection getConnection() {
            throw new UnsupportedOperationException("getConnection");

        }

        @Override
        public boolean supportsSavepoints() {
            throw new UnsupportedOperationException("supportsSavepoints");

        }

        @Override
        public boolean supportsNamedParameters() {
            throw new UnsupportedOperationException("supportsNamedParameters");

        }

        @Override
        public boolean supportsMultipleOpenResults() {
            throw new UnsupportedOperationException("supportsMultipleOpenResults");

        }

        @Override
        public boolean supportsGetGeneratedKeys() {
            throw new UnsupportedOperationException("supportsGetGeneratedKeys");

        }

        @Override
        public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) {
            throw new UnsupportedOperationException("getSuperTypes");

        }

        @Override
        public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) {
            throw new UnsupportedOperationException("getSuperTables");

        }

        @Override
        public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) {
            throw new UnsupportedOperationException("getAttributes");

        }

        @Override
        public boolean supportsResultSetHoldability(int holdability) {
            throw new UnsupportedOperationException("supportsResultSetHoldability");

        }

        @Override
        public int getResultSetHoldability() {
            throw new UnsupportedOperationException("getResultSetHoldability");

        }

        @Override
        public int getDatabaseMajorVersion() {
            return -1;

        }

        @Override
        public int getDatabaseMinorVersion() {
            return -1;
        }

        @Override
        public int getJDBCMajorVersion() {
            throw new UnsupportedOperationException("getJDBCMajorVersion");

        }

        @Override
        public int getJDBCMinorVersion() {
            throw new UnsupportedOperationException("getJDBCMinorVersion");

        }

        @Override
        public int getSQLStateType() {
            throw new UnsupportedOperationException("getSQLStateType");

        }

        @Override
        public boolean locatorsUpdateCopy() {
            throw new UnsupportedOperationException("locatorsUpdateCopy");

        }

        @Override
        public boolean supportsStatementPooling() {
            throw new UnsupportedOperationException("supportsStatementPooling");

        }

        @Override
        public RowIdLifetime getRowIdLifetime() {
            throw new UnsupportedOperationException("getRowIdLifetime");

        }

        @Override
        public ResultSet getSchemas(String catalog, String schemaPattern) {
            throw new UnsupportedOperationException("getSchemas");

        }

        @Override
        public boolean supportsStoredFunctionsUsingCallSyntax() {
            throw new UnsupportedOperationException("supportsStoredFunctionsUsingCallSyntax");

        }

        @Override
        public boolean autoCommitFailureClosesAllResultSets() {
            throw new UnsupportedOperationException("autoCommitFailureClosesAllResultSets");

        }

        @Override
        public ResultSet getClientInfoProperties() {
            throw new UnsupportedOperationException("getClientInfoProperties");

        }

        @Override
        public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) {
            throw new UnsupportedOperationException("getFunctions");

        }

        @Override
        public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) {
            throw new UnsupportedOperationException("getFunctionColumns");

        }

        @Override
        public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) {
            throw new UnsupportedOperationException("getPseudoColumns");

        }

        @Override
        public boolean generatedKeyAlwaysReturned() {
            throw new UnsupportedOperationException("generatedKeyAlwaysReturned");

        }

        @Override
        public <T> T unwrap(Class<T> iface) {
            throw new UnsupportedOperationException("unwrap");

        }

        @Override
        public boolean isWrapperFor(Class<?> iface) {
            throw new UnsupportedOperationException("isWrapperFor");

        }
    }
}
