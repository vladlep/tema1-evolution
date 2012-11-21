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
 * SSResultSet.java
 * ---------------
 * Author: Volker Berlin
 * 
 */
package smallsql.database;

import java.sql.*;
import java.math.*;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Calendar;
import java.net.URL;
import smallsql.database.language.Language;

public class SSResultSet implements ResultSet {

    SSResultSetMetaData metaData = new SSResultSetMetaData();
    private CommandSelect cmd;
    private boolean wasNull;
    SSStatement st;
    private boolean isUpdatable;
    private boolean isInsertRow;
    private ExpressionValue[] values;
    private int fetchDirection;
    private int fetchSize;

    SSResultSet( SSStatement st, CommandSelect cmd ){
        this.st = st;
        metaData.columns = cmd.columnExpressions;
        this.cmd = cmd;
		isUpdatable = st != null && st.rsConcurrency == CONCUR_UPDATABLE && !cmd.isGroupResult();
    }

/*==============================================================================

    Public Interface

==============================================================================*/

    @Override
	public void close(){
    	st.con.log.println("ResultSet.close");
        cmd = null;
    }
    
    
    @Override
	public boolean wasNull(){
        return wasNull;
    }
    
    
    @Override
	public String getString(int columnIndex) throws SQLException {
        try{
            String obj = getValue(columnIndex).getString();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public boolean getBoolean(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            return expr.getBoolean();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public byte getByte(int columnIndex) throws SQLException {
        return (byte)getInt( columnIndex );
    }
    @Override
	public short getShort(int columnIndex) throws SQLException {
        return (short)getInt( columnIndex );
    }
    @Override
	public int getInt(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            return expr.getInt();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public long getLong(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            return expr.getLong();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public float getFloat(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            return expr.getFloat();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public double getDouble(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            return expr.getDouble();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        try{
            MutableNumeric obj = getValue(columnIndex).getNumeric();
            wasNull = obj == null;
            if(wasNull) return null;
            return obj.toBigDecimal(scale);
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public byte[] getBytes(int columnIndex) throws SQLException {
        try{
            byte[] obj = getValue(columnIndex).getBytes();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Date getDate(int columnIndex) throws SQLException {
        try{
			Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
			if(wasNull) return null;
			return DateTime.getDate( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public Time getTime(int columnIndex) throws SQLException {
        try{
			Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
			if(wasNull) return null;
			return DateTime.getTime( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
        try{
			Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
			if(wasNull) return null;
			return DateTime.getTimestamp( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getAsciiStream method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "getAsciiStream");
    }
    
    
    @Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getUnicodeStream method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "getUnicodeStream");
    }
    
    
    @Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getBinaryStream method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "getBinaryStream");
    }
    
    
    @Override
	public String getString(String columnName) throws SQLException {
        return getString( findColumn( columnName ) );
    }
    @Override
	public boolean getBoolean(String columnName) throws SQLException {
        return getBoolean( findColumn( columnName ) );
    }
    @Override
	public byte getByte(String columnName) throws SQLException {
        return getByte( findColumn( columnName ) );
    }
    @Override
	public short getShort(String columnName) throws SQLException {
        return getShort( findColumn( columnName ) );
    }
    @Override
	public int getInt(String columnName) throws SQLException {
        return getInt( findColumn( columnName ) );
    }
    @Override
	public long getLong(String columnName) throws SQLException {
        return getLong( findColumn( columnName ) );
    }
    @Override
	public float getFloat(String columnName) throws SQLException {
        return getFloat( findColumn( columnName ) );
    }
    @Override
	public double getDouble(String columnName) throws SQLException {
        return getDouble( findColumn( columnName ) );
    }
    @Override
	public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
        return getBigDecimal( findColumn( columnName ), scale );
    }
    @Override
	public byte[] getBytes(String columnName) throws SQLException {
        return getBytes( findColumn( columnName ) );
    }
    @Override
	public Date getDate(String columnName) throws SQLException {
        return getDate( findColumn( columnName ) );
    }
    @Override
	public Time getTime(String columnName) throws SQLException {
        return getTime( findColumn( columnName ) );
    }
    @Override
	public Timestamp getTimestamp(String columnName) throws SQLException {
        return getTimestamp( findColumn( columnName ) );
    }
    @Override
	public InputStream getAsciiStream(String columnName) throws SQLException {
        return getAsciiStream( findColumn( columnName ) );
    }
    @Override
	public InputStream getUnicodeStream(String columnName) throws SQLException {
        return getUnicodeStream( findColumn( columnName ) );
    }
    @Override
	public InputStream getBinaryStream(String columnName) throws SQLException {
        return getBinaryStream( findColumn( columnName ) );
    }
    
    
    @Override
	public SQLWarning getWarnings(){
        return null;
    }
    
    
    @Override
	public void clearWarnings(){
        //TODO support for Warnings
    }
    
    
    @Override
	public String getCursorName(){
        return null;
    }
    
    
    @Override
	public ResultSetMetaData getMetaData(){
        return metaData;
    }
    
    
    @Override
	public Object getObject(int columnIndex) throws SQLException {
        try{
            Object obj = getValue(columnIndex).getApiObject();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Object getObject(String columnName) throws SQLException {
        return getObject( findColumn( columnName ) );
    }
    
    
    @Override
	public int findColumn(String columnName) throws SQLException {
    	return getCmd().findColumn(columnName) + 1;
    }
    

    @Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getCharacterStream method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "getCharacterStream");
    }
    
    
    @Override
	public Reader getCharacterStream(String columnName) throws SQLException {
        return getCharacterStream( findColumn( columnName ) );
    }
    
    
    @Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        try{
            MutableNumeric obj = getValue(columnIndex).getNumeric();
            wasNull = obj == null;
            if(wasNull) return null;
            return obj.toBigDecimal();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return getBigDecimal( findColumn( columnName ) );
    }
    @Override
	public boolean isBeforeFirst() throws SQLException {
		return getCmd().isBeforeFirst();
    }
    
    
    @Override
	public boolean isAfterLast() throws SQLException {
        try{
            return getCmd().isAfterLast();
        }catch(Exception e){
            throw SmallSQLException.createFromException(e);
        }
    }
    
    
    @Override
	public boolean isFirst() throws SQLException {
    	return getCmd().isFirst();
    }
    
    
    @Override
	public boolean isLast() throws SQLException {
    	try{
    		return getCmd().isLast();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
    }
    
    
    @Override
	public void beforeFirst() throws SQLException {
    	try{
            moveToCurrentRow();
    		getCmd().beforeFirst();
    	}catch(Exception e){
    		throw SmallSQLException.createFromException(e);
    	}
    }
    
    
    @Override
	public boolean first() throws SQLException {
		try{
			if(st.rsType == ResultSet.TYPE_FORWARD_ONLY) throw SmallSQLException.create(Language.RSET_FWDONLY);
            moveToCurrentRow();
			return getCmd().first();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
    }
    
    
	@Override
	public boolean previous() throws SQLException {
		try{
            moveToCurrentRow();
			return getCmd().previous();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
	}
    
    
	@Override
	public boolean next() throws SQLException {
		try{
            moveToCurrentRow();
            return getCmd().next();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
	}
	
	
    @Override
	public boolean last() throws SQLException {
		try{
            moveToCurrentRow();
            return getCmd().last();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
    }
    
    
	@Override
	public void afterLast() throws SQLException {
		try{
			if(st.rsType == ResultSet.TYPE_FORWARD_ONLY) throw SmallSQLException.create(Language.RSET_FWDONLY);
            moveToCurrentRow();
            getCmd().afterLast();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
	}
    
    
    @Override
	public boolean absolute(int row) throws SQLException {
		try{
            moveToCurrentRow();
			return getCmd().absolute(row);
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
    }
    
    
    @Override
	public boolean relative(int rows) throws SQLException {
		try{
            moveToCurrentRow();
			return getCmd().relative(rows);
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
    }
    
    
	@Override
	public int getRow() throws SQLException {
		try{
			return getCmd().getRow();
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
	}
    
    
    @Override
	public void setFetchDirection(int direction){
        fetchDirection = direction;
    }
    
    
    @Override
	public int getFetchDirection(){
        return fetchDirection;
    }
    
    
    @Override
	public void setFetchSize(int rows){
        fetchSize = rows;
    }
    
    
    @Override
	public int getFetchSize(){
        return fetchSize;
    }
    
    
    @Override
	public int getType() throws SQLException {
    	return getCmd().from.isScrollable() ? ResultSet.TYPE_SCROLL_SENSITIVE : ResultSet.TYPE_FORWARD_ONLY;
    }
    
    
    @Override
	public int getConcurrency(){
    	return isUpdatable ? ResultSet.CONCUR_UPDATABLE : ResultSet.CONCUR_READ_ONLY;
    }
    
    
    @Override
	public boolean rowUpdated(){
    	return false;
    }
    
    
    @Override
	public boolean rowInserted() throws SQLException {
    	return getCmd().from.rowInserted();
    }
    
    
    @Override
	public boolean rowDeleted() throws SQLException {
    	return getCmd().from.rowDeleted();
    }
    
    
    @Override
	public void updateNull(int columnIndex) throws SQLException {
		updateValue( columnIndex, null, SQLTokenizer.NULL);
    }
    @Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		updateValue( columnIndex, x ? Boolean.TRUE : Boolean.FALSE, SQLTokenizer.BOOLEAN);
    }
    @Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		updateValue( columnIndex, Utils.getShort(x), SQLTokenizer.TINYINT);
    }
    @Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		updateValue( columnIndex, Utils.getShort(x), SQLTokenizer.SMALLINT);
    }
    @Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		updateValue( columnIndex, Utils.getInteger(x), SQLTokenizer.INT);
    }
    @Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		updateValue( columnIndex, new Long(x), SQLTokenizer.BIGINT);
    }
    @Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		updateValue( columnIndex, new Float(x), SQLTokenizer.REAL);
    }
    @Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		updateValue( columnIndex, new Double(x), SQLTokenizer.DOUBLE);
    }
    @Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		updateValue( columnIndex, x, SQLTokenizer.DECIMAL);
    }
    @Override
	public void updateString(int columnIndex, String x) throws SQLException {
		updateValue( columnIndex, x, SQLTokenizer.VARCHAR);
    }
    @Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		updateValue( columnIndex, x, SQLTokenizer.VARBINARY);
    }
    @Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		updateValue( columnIndex, DateTime.valueOf(x), SQLTokenizer.DATE);
    }
    @Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		updateValue( columnIndex, DateTime.valueOf(x), SQLTokenizer.TIME);
    }
    @Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		updateValue( columnIndex, DateTime.valueOf(x), SQLTokenizer.TIMESTAMP);
    }
    @Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		updateValue( columnIndex, x, SQLTokenizer.LONGVARCHAR, length);
    }
    @Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		updateValue( columnIndex, x, SQLTokenizer.LONGVARBINARY, length);
    }
    
    
    @Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.updateCharacterStream method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Reader object");
    }
    
    
    @Override
	public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
    	//TODO scale to consider
		updateValue( columnIndex, x, -1);
    }
    
    
    @Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
    	updateValue( columnIndex, x, -1);
    }
    @Override
	public void updateNull(String columnName) throws SQLException {
        updateNull( findColumn( columnName ) );
    }
    @Override
	public void updateBoolean(String columnName, boolean x) throws SQLException {
        updateBoolean( findColumn( columnName ), x );
    }
    @Override
	public void updateByte(String columnName, byte x) throws SQLException {
        updateByte( findColumn( columnName ), x );
    }
    @Override
	public void updateShort(String columnName, short x) throws SQLException {
        updateShort( findColumn( columnName ), x );
    }
    @Override
	public void updateInt(String columnName, int x) throws SQLException {
        updateInt( findColumn( columnName ), x );
    }
    @Override
	public void updateLong(String columnName, long x) throws SQLException {
        updateLong( findColumn( columnName ), x );
    }
    @Override
	public void updateFloat(String columnName, float x) throws SQLException {
        updateFloat( findColumn( columnName ), x );
    }
    @Override
	public void updateDouble(String columnName, double x) throws SQLException {
        updateDouble( findColumn( columnName ), x );
    }
    @Override
	public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
        updateBigDecimal( findColumn( columnName ), x );
    }
    @Override
	public void updateString(String columnName, String x) throws SQLException {
        updateString( findColumn( columnName ), x );
    }
    @Override
	public void updateBytes(String columnName, byte[] x) throws SQLException {
        updateBytes( findColumn( columnName ), x );
    }
    @Override
	public void updateDate(String columnName, Date x) throws SQLException {
        updateDate( findColumn( columnName ), x );
    }
    @Override
	public void updateTime(String columnName, Time x) throws SQLException {
        updateTime( findColumn( columnName ), x );
    }
    @Override
	public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
        updateTimestamp( findColumn( columnName ), x );
    }
    @Override
	public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
        updateAsciiStream( findColumn( columnName ), x, length );
    }
    @Override
	public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
        updateBinaryStream( findColumn( columnName ), x, length );
    }
    @Override
	public void updateCharacterStream(String columnName, Reader x, int length) throws SQLException {
        updateCharacterStream( findColumn( columnName ), x, length );
    }
    @Override
	public void updateObject(String columnName, Object x, int scale) throws SQLException {
        updateObject( findColumn( columnName ), x, scale );
    }
    @Override
	public void updateObject(String columnName, Object x) throws SQLException {
        updateObject( findColumn( columnName ), x );
    }
    
    @Override
	public void insertRow() throws SQLException {
		st.con.log.println("insertRow()");
        if(!isInsertRow){
            throw SmallSQLException.create(Language.RSET_NOT_INSERT_ROW);
        }
		getCmd().insertRow( st.con, values);
        clearRowBuffer();
    }
    
    
    /**
     * Test if it on the insert row.
     * @throws SQLException if on the insert row
     */
    private void testNotInsertRow() throws SQLException{
        if(isInsertRow){
            throw SmallSQLException.create(Language.RSET_ON_INSERT_ROW);
        }
    }
    
    @Override
	public void updateRow() throws SQLException {
        try {
        	if(values == null){
                // no changes then also no update needed
                return;
            }
       		st.con.log.println("updateRow()");
            testNotInsertRow();
            final CommandSelect command = getCmd();
            command.updateRow( st.con, values);
            command.relative(0);  //refresh the row
            clearRowBuffer();
        } catch (Exception e) {
            throw SmallSQLException.createFromException(e);
        }
    }
    
    
    @Override
	public void deleteRow() throws SQLException {
		st.con.log.println("deleteRow()");
        testNotInsertRow();
    	getCmd().deleteRow(st.con);
        clearRowBuffer();
    }
    @Override
	public void refreshRow() throws SQLException {
        testNotInsertRow();
        relative(0);
    }
    

    @Override
	public void cancelRowUpdates() throws SQLException{
        testNotInsertRow();
        clearRowBuffer();
    }
    
    
    /**
     * Clear the update row or insert row buffer.
     */
    private void clearRowBuffer(){
        if(values != null){
            for(int i=values.length-1; i>=0; i--){
                values[i].clear();
            }
        }
    }
    

    @Override
	public void moveToInsertRow() throws SQLException {
    	if(isUpdatable){
    		isInsertRow = true;
            clearRowBuffer();
    	}else{
            throw SmallSQLException.create(Language.RSET_READONLY);
    	}
    }
    
    
    @Override
	public void moveToCurrentRow() throws SQLException{
		isInsertRow = false;
        clearRowBuffer();
        if(values == null){
            //init the values array as insert row buffer 
            getUpdateValue(1);
        }
    }
    
    
    @Override
	public Statement getStatement() {
        return st;
    }
    
    
    
    @Override
	public Ref getRef(int i) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getRef method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Ref object");
    }
    
    
    @Override
	public Blob getBlob(int i) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getBlob method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Blob object");
    }
    
    
    @Override
	public Clob getClob(int i) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getClob method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Clob object");
    }
    
    
    @Override
	public Array getArray(int i) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.getArray method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Array");
    }
    
    
    @Override
	public Ref getRef(String columnName) throws SQLException {
        return getRef( findColumn( columnName ) );
    }
    @Override
	public Blob getBlob(String columnName) throws SQLException {
        return getBlob( findColumn( columnName ) );
    }
    @Override
	public Clob getClob(String columnName) throws SQLException {
        return getClob( findColumn( columnName ) );
    }
    @Override
	public Array getArray(String columnName) throws SQLException {
        return getArray( findColumn( columnName ) );
    }
    
    
    @Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        try{
            if(cal == null){
                return getDate(columnIndex);
            }
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            if(wasNull) return null;
            return new Date(DateTime.addDateTimeOffset( expr.getLong(), cal.getTimeZone() ));
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public Date getDate(String columnName, Calendar cal) throws SQLException {
        return getDate( findColumn( columnName ), cal );
    }
    
    
    @Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        try{
            if(cal == null){
                return getTime(columnIndex);
            }
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            if(wasNull) return null;
            return new Time(DateTime.addDateTimeOffset( expr.getLong(), cal.getTimeZone() ));
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public Time getTime(String columnName, Calendar cal) throws SQLException {
        return getTime( findColumn( columnName ), cal );
    }
    
    
    @Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        try{
            if(cal == null){
                return getTimestamp(columnIndex);
            }
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            if(wasNull) return null;
            return new Timestamp(DateTime.addDateTimeOffset( expr.getLong(), cal.getTimeZone() ));
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
        return getTimestamp( findColumn( columnName ), cal );
    }
    
    
    @Override
	public URL getURL(int columnIndex) throws SQLException {
        try{
            Expression expr = getValue(columnIndex);
            wasNull = expr.isNull();
            if(wasNull) return null;
            return new URL( expr.getString() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    
    
    @Override
	public URL getURL(String columnName) throws SQLException {
        return getURL( findColumn( columnName ) );
    }
    
    
    @Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.updateRef method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Ref");
    }
    
    
    @Override
	public void updateRef(String columnName, Ref x) throws SQLException {
        updateRef( findColumn( columnName ), x );
    }
    
    
    @Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.updateBlob method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Blob");
    }
    
    
    @Override
	public void updateBlob(String columnName, Blob x) throws SQLException {
        updateBlob( findColumn( columnName ), x );
    }
    
    
    @Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.updateClob method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Clob");
    }
    
    
    @Override
	public void updateClob(String columnName, Clob x) throws SQLException {
        updateClob( findColumn( columnName ), x );
    }
    
    
    @Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
        /**@todo: Implement this java.sql.ResultSet.updateArray method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Array");
    }
    
    
    @Override
	public void updateArray(String columnName, Array x) throws SQLException {
        updateArray( findColumn( columnName ), x );
    }
    
	/*========================================================

	private methods

	=========================================================*/

    /**
     * Get the expression of a column. 
     * This expression can be used to request a value of the current row.
     */
    final private Expression getValue(int columnIndex) throws SQLException{
        if(values != null){
            ExpressionValue value = values[ metaData.getColumnIdx( columnIndex ) ];
            if(!value.isEmpty() || isInsertRow){ 
                return value;
            }
        }
        return metaData.getColumnExpression(columnIndex);
    }
    

	final private ExpressionValue getUpdateValue(int columnIndex) throws SQLException{
		if(values == null){
			int count = metaData.getColumnCount();
			values = new ExpressionValue[count];
			while(count-- > 0){
				values[count] = new ExpressionValue();
			}
		}
		return values[ metaData.getColumnIdx( columnIndex ) ];
	}
	
    
    final private void updateValue(int columnIndex, Object x, int dataType) throws SQLException{
		getUpdateValue( columnIndex ).set( x, dataType );
		if(st.con.log.isLogging()){
			
			st.con.log.println("parameter '"+metaData.getColumnName(columnIndex)+"' = "+x+"; type="+dataType);
		}
    }
    
    
	final private void updateValue(int columnIndex, Object x, int dataType, int length) throws SQLException{
		getUpdateValue( columnIndex ).set( x, dataType, length );
		if(st.con.log.isLogging()){
			st.con.log.println("parameter '"+metaData.getColumnName(columnIndex)+"' = "+x+"; type="+dataType+"; length="+length);
		}
	}


	final private CommandSelect getCmd() throws SQLException {
		if(cmd == null){
            throw SmallSQLException.create(Language.RSET_CLOSED);
        }
        st.con.testClosedConnection();
		return cmd;
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
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String columnLabel, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}