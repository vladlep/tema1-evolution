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
 * SSCallableStatament.java
 * ---------------
 * Author: Volker Berlin
 * 
 */
package smallsql.database;

import java.sql.*;
import java.math.*;
import java.util.Map;
import java.util.Calendar;
import java.net.URL;
import java.io.*;

import smallsql.database.language.Language;

public class SSCallableStatement extends SSPreparedStatement implements CallableStatement {

    private boolean wasNull;

    SSCallableStatement( SSConnection con, String sql ) throws SQLException {
        super( con, sql );
    }

    SSCallableStatement( SSConnection con, String sql, int rsType, int rsConcurrency ) throws SQLException {
        super( con, sql, rsType, rsConcurrency );
    }

    private Expression getValue(int i) throws SQLException{
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw new java.lang.UnsupportedOperationException("Method getValue() not yet implemented.");
    }

    private int findParameter( String parameterName ){
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw new java.lang.UnsupportedOperationException("Method findParameter() not yet implemented.");
    }
/*==============================================================================

    Public Interface

==============================================================================*/
    @Override
	public void registerOutParameter(int i, int sqlType) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw new java.lang.UnsupportedOperationException("Method registerOutParameter() not yet implemented.");
    }
    @Override
	public void registerOutParameter(int i, int sqlType, int scale) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw new java.lang.UnsupportedOperationException("Method registerOutParameter() not yet implemented.");
    }
    
    
    @Override
	public boolean wasNull(){
        return wasNull;
    }
    
    
    @Override
	public String getString(int i) throws SQLException {
        try{
            String obj = getValue(i).getString();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public boolean getBoolean(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            return expr.getBoolean();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public byte getByte(int i) throws SQLException {
        return (byte)getInt( i );
    }
    @Override
	public short getShort(int i) throws SQLException {
        return (byte)getInt( i );
    }
    @Override
	public int getInt(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            return expr.getInt();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public long getLong(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            return expr.getLong();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public float getFloat(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            return expr.getFloat();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public double getDouble(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            return expr.getLong();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public BigDecimal getBigDecimal(int i, int scale) throws SQLException {
        try{
            MutableNumeric obj = getValue(i).getNumeric();
            wasNull = obj == null;
            if(wasNull) return null;
            return obj.toBigDecimal(scale);
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public byte[] getBytes(int i) throws SQLException {
        try{
            byte[] obj = getValue(i).getBytes();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Date getDate(int i) throws SQLException {
        try{
			Expression expr = getValue(i);
            wasNull = expr.isNull();
			if(wasNull) return null;
			return DateTime.getDate( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Time getTime(int i) throws SQLException {
        try{
			Expression expr = getValue(i);
            wasNull = expr.isNull();
			if(wasNull) return null;
			return DateTime.getTime( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Timestamp getTimestamp(int i) throws SQLException {
        try{
            Expression expr = getValue(i);
            wasNull = expr.isNull();
            if(wasNull) return null;
            return DateTime.getTimestamp( expr.getLong() );
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Object getObject(int i) throws SQLException {
        try{
            Object obj = getValue(i).getObject();
            wasNull = obj == null;
            return obj;
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public BigDecimal getBigDecimal(int i) throws SQLException {
        try{
            MutableNumeric obj = getValue(i).getNumeric();
            wasNull = obj == null;
            if(wasNull) return null;
            return obj.toBigDecimal();
        }catch(Exception e){
            throw SmallSQLException.createFromException( e );
        }
    }
    @Override
	public Ref getRef(int i) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getRef() not yet implemented.");
    }
    @Override
	public Blob getBlob(int i) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getBlob() not yet implemented.");
    }
    @Override
	public Clob getClob(int i) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getClob() not yet implemented.");
    }
    @Override
	public Array getArray(int i) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getArray() not yet implemented.");
    }
    @Override
	public Date getDate(int i, Calendar cal) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getDate() not yet implemented.");
    }
    @Override
	public Time getTime(int i, Calendar cal) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getTime() not yet implemented.");
    }
    @Override
	public Timestamp getTimestamp(int i, Calendar cal) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getTimestamp() not yet implemented.");
    }
    @Override
	public void registerOutParameter(int i, int sqlType, String typeName) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method registerOutParameter() not yet implemented.");
    }
    @Override
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        registerOutParameter( findParameter( parameterName ), sqlType );
    }
    @Override
	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        registerOutParameter( findParameter( parameterName ), sqlType, scale );
    }
    @Override
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        registerOutParameter( findParameter( parameterName ), sqlType, typeName );
    }
    @Override
	public URL getURL(int parameterIndex) throws SQLException {
        /**@todo: Implement this java.sql.CallableStatement method*/
        throw SmallSQLException.create(Language.UNSUPPORTED_OPERATION, "Method getURL() not yet implemented.");
    }
    @Override
	public void setURL(String parameterName, URL x) throws SQLException {
        setURL( findParameter( parameterName ), x );
    }
    @Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
        setNull( findParameter( parameterName ), sqlType );
    }
    @Override
	public void setBoolean(String parameterName, boolean x) throws SQLException {
        setBoolean( findParameter( parameterName ), x );
    }
    @Override
	public void setByte(String parameterName, byte x) throws SQLException {
        setByte( findParameter( parameterName ), x );
    }
    @Override
	public void setShort(String parameterName, short x) throws SQLException {
        setShort( findParameter( parameterName ), x );
    }
    @Override
	public void setInt(String parameterName, int x) throws SQLException {
        setInt( findParameter( parameterName ), x );
    }
    @Override
	public void setLong(String parameterName, long x) throws SQLException {
        setLong( findParameter( parameterName ), x );
    }
    @Override
	public void setFloat(String parameterName, float x) throws SQLException {
        setFloat( findParameter( parameterName ), x );
    }
    @Override
	public void setDouble(String parameterName, double x) throws SQLException {
        setDouble( findParameter( parameterName ), x );
    }
    @Override
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        setBigDecimal( findParameter( parameterName ), x );
    }
    @Override
	public void setString(String parameterName, String x) throws SQLException {
        setString( findParameter( parameterName ), x );
    }
    @Override
	public void setBytes(String parameterName, byte[] x) throws SQLException {
        setBytes( findParameter( parameterName ), x );
    }
    @Override
	public void setDate(String parameterName, Date x) throws SQLException {
        setDate( findParameter( parameterName ), x );
    }
    @Override
	public void setTime(String parameterName, Time x) throws SQLException {
        setTime( findParameter( parameterName ), x );
    }
    @Override
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        setTimestamp( findParameter( parameterName ), x );
    }
    @Override
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        setAsciiStream( findParameter( parameterName ), x, length );
    }
    @Override
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        setBinaryStream( findParameter( parameterName ), x, length );
    }
    @Override
	public void setObject(String parameterName, Object x, int sqlType, int scale) throws SQLException {
        setObject( findParameter( parameterName ), x, sqlType, scale );
    }
    @Override
	public void setObject(String parameterName, Object x, int sqlType) throws SQLException {
        setObject( findParameter( parameterName ), x, sqlType );
    }
    @Override
	public void setObject(String parameterName, Object x) throws SQLException {
        setObject( findParameter( parameterName ), x );
    }
    @Override
	public void setCharacterStream(String parameterName, Reader x, int length) throws SQLException {
        setCharacterStream( findParameter( parameterName ), x, length );
    }
    @Override
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        setDate( findParameter( parameterName ), x, cal );
    }
    @Override
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        setTime( findParameter( parameterName ), x, cal );
    }
    @Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        setTimestamp( findParameter( parameterName ), x, cal );
    }
    @Override
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        setNull( findParameter( parameterName ), sqlType, typeName );
    }
    @Override
	public String getString(String parameterName) throws SQLException {
        return getString( findParameter( parameterName ) );
    }
    @Override
	public boolean getBoolean(String parameterName) throws SQLException {
        return getBoolean( findParameter( parameterName ) );
    }
    @Override
	public byte getByte(String parameterName) throws SQLException {
        return getByte( findParameter( parameterName ) );
    }
    @Override
	public short getShort(String parameterName) throws SQLException {
        return getShort( findParameter( parameterName ) );
    }
    @Override
	public int getInt(String parameterName) throws SQLException {
        return getInt( findParameter( parameterName ) );
    }
    @Override
	public long getLong(String parameterName) throws SQLException {
        return getLong( findParameter( parameterName ) );
    }
    @Override
	public float getFloat(String parameterName) throws SQLException {
        return getFloat( findParameter( parameterName ) );
    }
    @Override
	public double getDouble(String parameterName) throws SQLException {
        return getDouble( findParameter( parameterName ) );
    }
    @Override
	public byte[] getBytes(String parameterName) throws SQLException {
        return getBytes( findParameter( parameterName ) );
    }
    @Override
	public Date getDate(String parameterName) throws SQLException {
        return getDate( findParameter( parameterName ) );
    }
    @Override
	public Time getTime(String parameterName) throws SQLException {
        return getTime( findParameter( parameterName ) );
    }
    @Override
	public Timestamp getTimestamp(String parameterName) throws SQLException {
        return getTimestamp( findParameter( parameterName ) );
    }
    @Override
	public Object getObject(String parameterName) throws SQLException {
        return getObject( findParameter( parameterName ) );
    }
    @Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return getBigDecimal( findParameter( parameterName ) );
    }
    @Override
	public Ref getRef(String parameterName) throws SQLException {
        return getRef( findParameter( parameterName ) );
    }
    @Override
	public Blob getBlob(String parameterName) throws SQLException {
        return getBlob( findParameter( parameterName ) );
    }
    @Override
	public Clob getClob(String parameterName) throws SQLException {
        return getClob( findParameter( parameterName ) );
    }
    @Override
	public Array getArray(String parameterName) throws SQLException {
        return getArray( findParameter( parameterName ) );
    }
    @Override
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return getDate( findParameter( parameterName ), cal );
    }
    @Override
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return getTime( findParameter( parameterName ), cal );
    }
    @Override
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return getTimestamp( findParameter( parameterName ), cal );
    }
    @Override
	public URL getURL(String parameterName) throws SQLException {
        return getURL( findParameter( parameterName ) );
    }

	@Override
	public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
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
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
	public void setAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public <T> T getObject(int parameterIndex, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String parameterName, Class<T> type)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}