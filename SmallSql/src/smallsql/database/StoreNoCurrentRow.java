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
 * StoreNoCurrentRow.java
 * ---------------
 * Author: Volker Berlin
 * 
 */
package smallsql.database;

import java.sql.*;
import smallsql.database.language.Language;

/*
 * @author Volker Berlin
 *
 * This store is used if the row pointer is before or after the rows.
 */
public class StoreNoCurrentRow extends Store {

	private SQLException noCurrentRow(){
		return SmallSQLException.create(Language.ROW_NOCURRENT);
	}


	@Override
	boolean isNull(int offset) throws SQLException {
		throw noCurrentRow();
	}


	@Override
	boolean getBoolean(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	byte[] getBytes(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	double getDouble(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	float getFloat(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	int getInt(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	long getLong(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	long getMoney(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	MutableNumeric getNumeric(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	Object getObject(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}


	@Override
	String getString(int offset, int dataType) throws Exception {
		throw noCurrentRow();
	}



	@Override
	void scanObjectOffsets(int[] offsets, int[] dataTypes) {
		// TODO Auto-generated method stub

	}


	@Override
	int getUsedSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	long getNextPagePos(){
		//TODO
		return -1;
	}
	
	@Override
	void deleteRow(SSConnection con) throws SQLException{
		throw noCurrentRow();
	}
}
