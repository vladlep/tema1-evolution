/* =============================================================
 * SmallSQL : a free Java DBMS library for the Java(tm) platform
 * =============================================================
 *
 * (C) Copyright 2004-2007, by Volker Berlin.
 *
 * Project Info:  http://www.smallsql.de/
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------
 * SSDatabaseMetaData.java
 * ---------------
 * Author: Volker Berlin
 * 
 */
package smallsql.database;

import java.sql.*;
import java.util.ArrayList;


final class SSDatabaseMetaData implements DatabaseMetaData {
	final private SSConnection con;
	final private SSStatement st;
	
	
    /**
     * @throws SQLException Exception can be throw if the Connection already closed.
     */
    SSDatabaseMetaData(SSConnection con) throws SQLException{
		this.con = con;
		st = new SSStatement(con);
	}
	
    @Override
	public boolean allProceduresAreCallable() {
    	return true;
    }
	
	
    @Override
	public boolean allTablesAreSelectable() {
    	return true;
    }
	
	
    @Override
	public String getURL() throws SQLException {
    	Database database = con.getDatabase(true);
    	if(database == null)
			return SSDriver.URL_PREFIX;
    	return SSDriver.URL_PREFIX + ':' + database.getName();
    }
	
	
    @Override
	public String getUserName() {
    	return "";
    }
	
	
    @Override
	public boolean isReadOnly() {
    	return false;
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
		return false;
    }
	
	
    @Override
	public boolean nullsAreSortedAtEnd() {
		return false;
    }
	
	
    @Override
	public String getDatabaseProductName() {
    	return "SmallSQL Database";
    }
	
	
    @Override
	public String getDatabaseProductVersion() {
    	return getDriverVersion();
    }
	
	
    @Override
	public String getDriverName(){
    	return "SmallSQL Driver";
    }
	
	
    @Override
	public String getDriverVersion() {
    	return getDriverMajorVersion() + "." + SSDriver.drv.getMinorVersion();
    }
	
	
    @Override
	public int getDriverMajorVersion() {
    	return SSDriver.drv.getMajorVersion();
    }
	
	
    @Override
	public int getDriverMinorVersion() {
		return SSDriver.drv.getMinorVersion();
    }
	
	
    @Override
	public boolean usesLocalFiles() {
    	return false;
    }
	
	
    @Override
	public boolean usesLocalFilePerTable() {
    	return false;
    }
	
	
    @Override
	public boolean supportsMixedCaseIdentifiers() {
    	return true;
    }
	
	
    @Override
	public boolean storesUpperCaseIdentifiers() {
    	return false;
    }
	
	
    @Override
	public boolean storesLowerCaseIdentifiers() {
    	return false;
    }
	
	
    @Override
	public boolean storesMixedCaseIdentifiers() {
    	return true;
    }
	
	
    @Override
	public boolean supportsMixedCaseQuotedIdentifiers() {
    	return true;
    }
	
	
    @Override
	public boolean storesUpperCaseQuotedIdentifiers() {
    	return false;
    }
	
	
    @Override
	public boolean storesLowerCaseQuotedIdentifiers() {
    	return false;
    }
	
	
    @Override
	public boolean storesMixedCaseQuotedIdentifiers() {
    	return true;
    }
	
	
    @Override
	public String getIdentifierQuoteString() {
    	return "\"";
    }
	
	
    @Override
	public String getSQLKeywords() {
    	return "database,use";
    }
    
    
    private String getFunctions(int from, int to){
		StringBuffer buf = new StringBuffer();
		for(int i=from; i<=to; i++){
			if(i != from) buf.append(',');
			buf.append( SQLTokenizer.getKeyWord(i) );
		}
		return buf.toString();
    }
    
    
    @Override
	public String getNumericFunctions() {
    	return getFunctions(SQLTokenizer.ABS, SQLTokenizer.TRUNCATE);
    }
    
    
    @Override
	public String getStringFunctions() {
		return getFunctions(SQLTokenizer.ASCII, SQLTokenizer.UCASE);
    }
    
    
    @Override
	public String getSystemFunctions() {
		return getFunctions(SQLTokenizer.IFNULL, SQLTokenizer.IIF);
    }
    
    
    @Override
	public String getTimeDateFunctions() {
		return getFunctions(SQLTokenizer.CURDATE, SQLTokenizer.YEAR);
    }
    
    
    @Override
	public String getSearchStringEscape() {
    	return "\\";
    }
    
    
    @Override
	public String getExtraNameCharacters() {
    	return "#$Ã€Ã�Ã‚ÃƒÃ„Ã…Ã†Ã‡ÃˆÃ‰ÃŠÃ‹ÃŒÃ�ÃŽÃ�Ã�Ã‘Ã’Ã“Ã”Ã•Ã–Ã˜Ã™ÃšÃ›ÃœÃ�ÃžÃŸÃ Ã¡Ã¢Ã£Ã¤Ã¥Ã¦Ã§Ã¨Ã©ÃªÃ«Ã¬Ã­Ã®Ã¯Ã°Ã±Ã²Ã³Ã´ÃµÃ¶Ã¸Ã¹ÃºÃ»Ã¼Ã½Ã¾Ã¿";
    }
	
	
    @Override
	public boolean supportsAlterTableWithAddColumn() {
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method supportsAlterTableWithAddColumn() not yet implemented.");
    }
    @Override
	public boolean supportsAlterTableWithDropColumn() {
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method supportsAlterTableWithDropColumn() not yet implemented.");
    }
	
	
    @Override
	public boolean supportsColumnAliasing() {
    	return true;
    }
	
	
    @Override
	public boolean nullPlusNonNullIsNull() {
    	return true;
    }
	
	
    @Override
	public boolean supportsConvert() {
    	return true;
    }
	
	
    @Override
	public boolean supportsConvert(int fromType, int toType) {
    	return true;
    }
	
	
    @Override
	public boolean supportsTableCorrelationNames() {
    	return true;
    }
	
	
    @Override
	public boolean supportsDifferentTableCorrelationNames() {
    	return true;
    }
	
	
    @Override
	public boolean supportsExpressionsInOrderBy() {
    	return true;
    }
	
	
    @Override
	public boolean supportsOrderByUnrelated() {
    	return true;
    }
	
	
    @Override
	public boolean supportsGroupBy() {
    	return true;
    }
	
	
    @Override
	public boolean supportsGroupByUnrelated() {
    	return true;
    }
	
	
    @Override
	public boolean supportsGroupByBeyondSelect() {
    	return true;
    }
	
	
    @Override
	public boolean supportsLikeEscapeClause() {
    	return true;
    }
	
	
    @Override
	public boolean supportsMultipleResultSets() {
    	return true;
    }
	
	
    @Override
	public boolean supportsMultipleTransactions() {
    	return true;
    }
	
	
    @Override
	public boolean supportsNonNullableColumns() {
    	return true;
    }
	
	
    @Override
	public boolean supportsMinimumSQLGrammar() {
    	return true;
    }
	
	
    @Override
	public boolean supportsCoreSQLGrammar() {
		return true;
    }
	
	
    @Override
	public boolean supportsExtendedSQLGrammar() {
    	return true;
    }
	
	
    @Override
	public boolean supportsANSI92EntryLevelSQL() {
    	return true;
    }
	
	
    @Override
	public boolean supportsANSI92IntermediateSQL() {
    	return true;
    }
	
	
    @Override
	public boolean supportsANSI92FullSQL() {
    	return true;
    }
	
	
    @Override
	public boolean supportsIntegrityEnhancementFacility() {
    	return true;
    }
	
	
    @Override
	public boolean supportsOuterJoins() {
    	return true;
    }
	
	
    @Override
	public boolean supportsFullOuterJoins() {
    	return true;
    }
	
	
    @Override
	public boolean supportsLimitedOuterJoins() {
    	return true;
    }
	
	
    @Override
	public String getSchemaTerm() {
    	return "owner";
    }
	
	
    @Override
	public String getProcedureTerm() {
    	return "procedure";
    }
	
	
    @Override
	public String getCatalogTerm() {
    	return "database";
    }
	
	
    @Override
	public boolean isCatalogAtStart() {
    	return true;
    }
	
	
    @Override
	public String getCatalogSeparator() {
    	return ".";
    }
	
	
    @Override
	public boolean supportsSchemasInDataManipulation() {
    	return false;
    }
	
	
    @Override
	public boolean supportsSchemasInProcedureCalls() {
    	return false;
    }
	
	
    @Override
	public boolean supportsSchemasInTableDefinitions() {
    	return false;
    }
	
	
    @Override
	public boolean supportsSchemasInIndexDefinitions() {
    	return false;
    }
	
	
    @Override
	public boolean supportsSchemasInPrivilegeDefinitions() {
    	return false;
    }
	
	
    @Override
	public boolean supportsCatalogsInDataManipulation() {
		return true;
    }
	
	
    @Override
	public boolean supportsCatalogsInProcedureCalls() {
    	return true;
    }
	
	
    @Override
	public boolean supportsCatalogsInTableDefinitions() {
    	return true;
    }
	
	
    @Override
	public boolean supportsCatalogsInIndexDefinitions() {
		return true;
    }
	
	
    @Override
	public boolean supportsCatalogsInPrivilegeDefinitions() {
		return true;
    }
	
	
    @Override
	public boolean supportsPositionedDelete() {
    	return true;
    }
	
	
    @Override
	public boolean supportsPositionedUpdate() {
		return true;
    }
	
	
    @Override
	public boolean supportsSelectForUpdate() {
		return true;
    }
	
	
    @Override
	public boolean supportsStoredProcedures() {
    	return false;
    }
	
	
    @Override
	public boolean supportsSubqueriesInComparisons() {
		return true;
    }
	
	
    @Override
	public boolean supportsSubqueriesInExists() {
		return true;
    }
	
	
    @Override
	public boolean supportsSubqueriesInIns() {
		return true;
    }
	
	
    @Override
	public boolean supportsSubqueriesInQuantifieds() {
		return true;
    }
	
	
    @Override
	public boolean supportsCorrelatedSubqueries() {
		return true;
    }
	
	
    @Override
	public boolean supportsUnion() {
		return true;
    }
	
	
    @Override
	public boolean supportsUnionAll() {
		return true;
    }
	
	
    @Override
	public boolean supportsOpenCursorsAcrossCommit() {
    	return true;
    }
	
	
    @Override
	public boolean supportsOpenCursorsAcrossRollback() {
    	return true;
    }
	
	
    @Override
	public boolean supportsOpenStatementsAcrossCommit() {
    	return true;
    }
	
	
    @Override
	public boolean supportsOpenStatementsAcrossRollback() {
    	return true;
    }
	
	
    @Override
	public int getMaxBinaryLiteralLength() {
    	return 0;
    }
	
	
    @Override
	public int getMaxCharLiteralLength() {
    	return 0;
    }
	
	
    @Override
	public int getMaxColumnNameLength() {
    	return 255;
    }
	
	
    @Override
	public int getMaxColumnsInGroupBy() {
    	return 0;
    }
	
	
    @Override
	public int getMaxColumnsInIndex() {
    	return 0;
    }
	
	
    @Override
	public int getMaxColumnsInOrderBy() {
    	return 0;
    }
	
	
    @Override
	public int getMaxColumnsInSelect() {
    	return 0;
    }
	
	
    @Override
	public int getMaxColumnsInTable() {
    	return 0;
    }
	
	
    @Override
	public int getMaxConnections() {
    	return 0;
    }
	
	
    @Override
	public int getMaxCursorNameLength() {
    	return 0;
    }
	
	
    @Override
	public int getMaxIndexLength() {
    	return 0;
    }
	
	
    @Override
	public int getMaxSchemaNameLength() {
    	return 255;
    }
	
	
    @Override
	public int getMaxProcedureNameLength() {
    	return 255;
    }
	
	
    @Override
	public int getMaxCatalogNameLength() {
    	return 255;
    }
	
	
    @Override
	public int getMaxRowSize() {
    	return 0;
    }
	
	
    @Override
	public boolean doesMaxRowSizeIncludeBlobs() {
    	return false;
    }
	
	
    @Override
	public int getMaxStatementLength() {
    	return 0;
    }
	
	
    @Override
	public int getMaxStatements() {
    	return 0;
    }
	
	
    @Override
	public int getMaxTableNameLength() {
    	return 255;
    }
	
	
    @Override
	public int getMaxTablesInSelect() {
    	return 0;
    }
	
	
    @Override
	public int getMaxUserNameLength() {
    	return 0;
    }
	
	
    @Override
	public int getDefaultTransactionIsolation() {
    	return Connection.TRANSACTION_READ_COMMITTED;
    }
	
	
    @Override
	public boolean supportsTransactions() {
    	return true;
    }
	
	
    @Override
	public boolean supportsTransactionIsolationLevel(int level) {
    	switch(level){
			case Connection.TRANSACTION_NONE:
			case Connection.TRANSACTION_READ_UNCOMMITTED:
    		case Connection.TRANSACTION_READ_COMMITTED:
			case Connection.TRANSACTION_REPEATABLE_READ:
			case Connection.TRANSACTION_SERIALIZABLE:
				return true;
    	}
    	return false;
    }
	
	
    @Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions() {
    	return true;
    }
	
	
    @Override
	public boolean supportsDataManipulationTransactionsOnly() {
    	return false;
    }
	
	
    @Override
	public boolean dataDefinitionCausesTransactionCommit() {
    	return false;
    }
	
	
    @Override
	public boolean dataDefinitionIgnoredInTransactions() {
    	return false;
    }
	
	
    @Override
	public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
		String[] colNames = {"PROCEDURE_CAT", "PROCEDURE_SCHEM", "PROCEDURE_NAME", "", "", "", "REMARKS", "PROCEDURE_TYPE"};  
		Object[][] data   = new Object[0][];
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
	
    @Override
	public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
		String[] colNames = {"PROCEDURE_CAT", "PROCEDURE_SCHEM", "PROCEDURE_NAME", "COLUMN_NAME", "COLUMN_TYPE", "DATA_TYPE", "TYPE_NAME", "PRECISION", "LENGTH", "SCALE", "RADIX", "NULLABLE", "REMARKS" };
		Object[][] data   = new Object[0][];
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
	
    @Override
	public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
		String[] colNames = {"TABLE_CAT","TABLE_SCHEM","TABLE_NAME","TABLE_TYPE","REMARKS","TYPE_CAT","TYPE_SCHEM","TYPE_NAME","SELF_REFERENCING_COL_NAME","REF_GENERATION"};
		Database database;
		if(catalog == null){ 
			database = con.getDatabase(true);
			if(database != null)
				catalog = database.getName();
    	}else{
			database = Database.getDatabase(catalog, con, false);
    	}
		ArrayList rows = new ArrayList();
		boolean isTypeTable = types == null;
		boolean isTypeView = types == null;
		for(int i=0; types != null && i<types.length; i++){
			if("TABLE".equalsIgnoreCase(types[i])) isTypeTable = true;
			if("VIEW" .equalsIgnoreCase(types[i])) isTypeView  = true;
		}
		
		if(database != null){
			Strings tables = database.getTables(tableNamePattern);
			for(int i=0; i<tables.size(); i++){
				String table = tables.get(i);
				Object[] row = new Object[10];
				row[0] = catalog;
				row[2] = table;
				try{
					if(database.getTableView( con, table) instanceof View){
						if(isTypeView){
							row[3] = "VIEW";
							rows.add(row);
						}
					}else{
						if(isTypeTable){
							row[3] = "TABLE";					
							rows.add(row);
						}
					}
				}catch(Exception e){
					//TODO invalid VIEWS does not show because it can't load.
				}
			}
		}
		Object[][] data = new Object[rows.size()][];
		rows.toArray(data);
		CommandSelect cmdSelect = Utils.createMemoryCommandSelect( con, colNames, data);
		Expressions order = new Expressions();
		order.add( new ExpressionName("TABLE_TYPE") );
		order.add( new ExpressionName("TABLE_NAME") );
		cmdSelect.setOrder( order );
		return new SSResultSet( st, cmdSelect);
    }
	
	
    @Override
	public ResultSet getSchemas() throws SQLException {
		String[] colNames = {"TABLE_SCHEM"};
		Object[][] data   = new Object[0][];
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
    
    @Override
	public ResultSet getCatalogs() throws SQLException {
    	String[] colNames = {"TABLE_CAT"};
    	Object[][] data   = Database.getCatalogs(con.getDatabase(true));
    	return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
    
    @Override
	public ResultSet getTableTypes() throws SQLException {
		String[] colNames = {"TABLE_TYPE"};
		Object[][] data   = {{"SYSTEM TABLE"}, {"TABLE"}, {"VIEW"}};
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
    
    @Override
	public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
		try {
			String[] colNames = {"TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "DATA_TYPE", "TYPE_NAME", "COLUMN_SIZE", "BUFFER_LENGTH", "DECIMAL_DIGITS", "NUM_PREC_RADIX", "NULLABLE", "REMARKS", "COLUMN_DEF", "SQL_DATA_TYPE", "SQL_DATETIME_SUB", "CHAR_OCTET_LENGTH", "ORDINAL_POSITION", "IS_NULLABLE"};
			Object[][] data   = con.getDatabase(false).getColumns(con, tableNamePattern, columnNamePattern);
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        String[] colNames = {"TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "GRANTOR", "GRANTEE", "PRIVILEGE", "IS_GRANTABLE"};
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method getColumnPrivileges() not yet implemented.");
    }
    
    
    @Override
	public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        String[] colNames = {"TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "GRANTOR", "GRANTEE", "PRIVILEGE", "IS_GRANTABLE"};
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method getTablePrivileges() not yet implemented.");
    }
	
	
    @Override
	public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
		try {
			String[] colNames = {"SCOPE", "COLUMN_NAME", "DATA_TYPE", "TYPE_NAME", "COLUMN_SIZE", "BUFFER_LENGTH", "DECIMAL_DIGITS", "PSEUDO_COLUMN"};
			Object[][] data   = con.getDatabase(false).getBestRowIdentifier(con, table);
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
		try {
			String[] colNames = {"SCOPE", "COLUMN_NAME", "DATA_TYPE", "TYPE_NAME", "COLUMN_SIZE", "BUFFER_LENGTH", "DECIMAL_DIGITS", "PSEUDO_COLUMN"};
			Object[][] data   = new Object[0][0];
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
		try {
			String[] colNames = {"TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "KEY_SEQ", "PK_NAME"};
			Object[][] data   = con.getDatabase(false).getPrimaryKeys(con, table);
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
		return getCrossReference( null, null, null, null, null, table );
    }
	
	
    @Override
	public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
		return getCrossReference( null, null, table, null, null, null );
    }
	
	
    @Override
	public ResultSet getCrossReference(String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
		try {
			String[] colNames = {"PKTABLE_CAT", "PKTABLE_SCHEM", "PKTABLE_NAME", "PKCOLUMN_NAME", "FKTABLE_CAT", "FKTABLE_SCHEM", "FKTABLE_NAME", "FKCOLUMN_NAME", "KEY_SEQ", "UPDATE_RULE", "DELETE_RULE", "FK_NAME", "PK_NAME", "DEFERRABILITY"};
			Object[][] data   = con.getDatabase(false).getReferenceKeys(con, primaryTable, foreignTable);
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public ResultSet getTypeInfo() throws SQLException {
		String[] colNames = {		"TYPE_NAME", 				"DATA_TYPE", 																	"PRECISION", 	"LITERAL_PREFIX", "LITERAL_SUFFIX", 		"CREATE_PARAMS", "NULLABLE", 	 "CASE_SENSITIVE", "SEARCHABLE", "UNSIGNED_ATTRIBUTE", "FIXED_PREC_SCALE", "AUTO_INCREMENT", "LOCAL_TYPE_NAME", "MINIMUM_SCALE", "MAXIMUM_SCALE", "SQL_DATA_TYPE", "SQL_DATETIME_SUB", "NUM_PREC_RADIX"};
		Object[][] data   = {
		 {SQLTokenizer.getKeyWord(SQLTokenizer.UNIQUEIDENTIFIER),Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.UNIQUEIDENTIFIER)), Utils.getInteger(36),      	"'",  "'",  null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null,          Boolean.FALSE, Boolean.FALSE, null, null,                null,                null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.BIT),             Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.BIT) ),             Utils.getInteger(1),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null,          Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.TINYINT),         Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.TINYINT) ),         Utils.getInteger(3),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.TRUE,  Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.BIGINT),          Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.BIGINT) ),          Utils.getInteger(19),     	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.LONGVARBINARY),   Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.LONGVARBINARY) ),   Utils.getInteger(2147483647),	"0x", null, null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.VARBINARY),   	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.VARBINARY) ),   	  Utils.getInteger(65535),	    "0x", null, "max length", 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.BINARY),   	 	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.BINARY) ),   	  	  Utils.getInteger(65535),	    "0x", null, "length", 			Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.LONGVARCHAR),     Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.LONGVARCHAR) ),     Utils.getInteger(2147483647),	"'",  "'",  null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.LONGNVARCHAR),    Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.LONGNVARCHAR) ),    Utils.getInteger(2147483647),	"'",  "'",  null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.CHAR),         	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.CHAR) ),         	  Utils.getInteger(65535),   	"'",  "'",  "length", 			Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.NCHAR),         	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.NCHAR) ),           Utils.getInteger(65535),   	"'",  "'",  "length", 			Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.NUMERIC),         Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.NUMERIC) ),         Utils.getInteger(38),     	null, null, "precision,scale", 	Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(38),null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.DECIMAL),         Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.DECIMAL) ),         Utils.getInteger(38),     	null, null, "precision,scale", 	Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(38),null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.MONEY),           Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.MONEY) ),           Utils.getInteger(19),     	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(4), Utils.getInteger(4), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.SMALLMONEY),      Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.SMALLMONEY) ),      Utils.getInteger(10),     	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(4), Utils.getInteger(4), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.INT),             Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.INT) ),             Utils.getInteger(10),     	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.SMALLINT),        Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.SMALLINT) ),        Utils.getInteger(5),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.FLOAT),        	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.FLOAT) ),           Utils.getInteger(15),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.REAL),        	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.REAL) ),        	  Utils.getInteger(7),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.DOUBLE),          Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.DOUBLE) ),          Utils.getInteger(15),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.VARCHAR),         Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.VARCHAR) ),         Utils.getInteger(65535),   	"'",  "'",  "max length", 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.NVARCHAR),        Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.NVARCHAR) ),        Utils.getInteger(65535),   	"'",  "'",  "max length", 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.BOOLEAN),         Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.BOOLEAN) ),         Utils.getInteger(1),      	null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null,          Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(0), Utils.getInteger(0), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.DATE),   	 	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.DATE) ), 	  		  Utils.getInteger(10),	    	"'",  "'",  null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.TIME),   	 	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.TIME) ), 	  		  Utils.getInteger(8),	    	"'",  "'",  null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.TIMESTAMP),   	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.TIMESTAMP) ), 	  Utils.getInteger(23),	    	"'",  "'",  null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, Utils.getInteger(3), Utils.getInteger(3), null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.SMALLDATETIME),   Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.SMALLDATETIME) ),   Utils.getInteger(16),	    	"'",  "'",  null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.JAVA_OBJECT),   	 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.JAVA_OBJECT) ),     Utils.getInteger(65535),	    null, null, null, 				Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.BLOB),   		 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.BLOB) ),   		  Utils.getInteger(2147483647),	"0x", null, null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.CLOB),     		 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.CLOB) ),     		  Utils.getInteger(2147483647),	"'",  "'",  null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		 {SQLTokenizer.getKeyWord(SQLTokenizer.NCLOB),     		 Utils.getShort(SQLTokenizer.getSQLDataType( SQLTokenizer.NCLOB) ),     	  Utils.getInteger(2147483647),	"'",  "'",  null, 		 		Utils.getShort(typeNullable), Boolean.FALSE, Utils.getShort(typeSearchable), null, 			Boolean.FALSE, Boolean.FALSE, null, null, 				 null, 				  null, null, null},
		};
		//TODO add more data types to the list
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
	
    @Override
	public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
		try {
			String[] colNames = {"TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "NON_UNIQUE", "INDEX_QUALIFIER", "INDEX_NAME", "TYPE", "ORDINAL_POSITION", "COLUMN_NAME", "ASC_OR_DESC", "CARDINALITY", "PAGES", "FILTER_CONDITION"};
			Object[][] data   = con.getDatabase(false).getIndexInfo(con, table, unique);
			return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
		} catch (Exception e) {
			throw SmallSQLException.createFromException(e);
		}
    }
	
	
    @Override
	public boolean supportsResultSetType(int type) {
		switch(type){
			case ResultSet.TYPE_FORWARD_ONLY:
			case ResultSet.TYPE_SCROLL_INSENSITIVE:
			case ResultSet.TYPE_SCROLL_SENSITIVE:
				return true;
		}
		return false;
    }
	
	
    @Override
	public boolean supportsResultSetConcurrency(int type, int concurrency) {
		if(type >= ResultSet.TYPE_FORWARD_ONLY && type <= ResultSet.TYPE_SCROLL_SENSITIVE &&
			concurrency >= ResultSet.CONCUR_READ_ONLY && concurrency <= ResultSet.CONCUR_UPDATABLE)
			return true;
		return false;
    }
	
	
    @Override
	public boolean ownUpdatesAreVisible(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean ownDeletesAreVisible(int type) {
		return supportsResultSetType(type);
    }

	
	@Override
	public boolean ownInsertsAreVisible(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean othersUpdatesAreVisible(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean othersDeletesAreVisible(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean othersInsertsAreVisible(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean updatesAreDetected(int type) {
		return false;
    }
	
	
    @Override
	public boolean deletesAreDetected(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean insertsAreDetected(int type) {
		return supportsResultSetType(type);
    }
	
	
    @Override
	public boolean supportsBatchUpdates() {
		return true;
    }
	
	
    @Override
	public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
		String[] colNames = {"TYPE_CAT", "TYPE_SCHEM", "TYPE_NAME", "CLASS_NAME", "DATA_TYPE", "REMARKS"};        
		Object[][] data   = new Object[0][];
		return new SSResultSet( st, Utils.createMemoryCommandSelect( con, colNames, data));
    }
	
	
    @Override
	public Connection getConnection() {
    	return con;
    }
	
	
    @Override
	public boolean supportsSavepoints() {
    	return false;
    }
	
	
    @Override
	public boolean supportsNamedParameters() {
		return true;
    }
	
	
    @Override
	public boolean supportsMultipleOpenResults() {
		return true;
    }
	
	
    @Override
	public boolean supportsGetGeneratedKeys() {
		return true;
    }
	
	
    @Override
	public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method getSuperTypes() not yet implemented.");
    }
    @Override
	public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method getSuperTables() not yet implemented.");
    }
    @Override
	public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        /**@todo: Implement this java.sql.DatabaseMetaData method*/
        throw new java.lang.UnsupportedOperationException("Method getAttributes() not yet implemented.");
    }
	
	
    @Override
	public boolean supportsResultSetHoldability(int holdability) {
		return true;
    }
	
	
    @Override
	public int getResultSetHoldability() {
		return ResultSet.HOLD_CURSORS_OVER_COMMIT;
    }
	
	
    @Override
	public int getDatabaseMajorVersion() {
    	return getDriverMajorVersion();
    }
	
	
    @Override
	public int getDatabaseMinorVersion() {
		return getDriverMinorVersion();
    }
	
	
    @Override
	public int getJDBCMajorVersion() {
    	return 3;
    }
	
	
    @Override
	public int getJDBCMinorVersion() {
    	return 0;
    }
	
	
    @Override
	public int getSQLStateType() {
		return sqlStateSQL99;
    }
	
	
    @Override
	public boolean locatorsUpdateCopy() {
		return false;
    }
	
	
    @Override
	public boolean supportsStatementPooling() {
		return false;
    }

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getFunctionColumns(String arg0, String arg1, String arg2,
			String arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getFunctions(String arg0, String arg1, String arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getSchemas(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultSet getPseudoColumns(String catalog, String schemaPattern,
			String tableNamePattern, String columnNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean generatedKeyAlwaysReturned() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}