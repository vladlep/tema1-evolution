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
 * ViewResult.java
 * ---------------
 * Author: Volker Berlin
 * 
 * Created on 05.06.2004
 */
package smallsql.database;

import java.sql.*;


/**
 * @author Volker Berlin
 */
class ViewResult extends TableViewResult {
	
	final private View view;
	final private Expressions columnExpressions;
	final private CommandSelect commandSelect;


	ViewResult(View view){
		this.view = view;
		this.columnExpressions = view.commandSelect.columnExpressions;
		this.commandSelect     = view.commandSelect;
	}
	

	/**
	 * Constructor is used for UNION
	 * @throws Exception 
	 * 
	 */
	ViewResult(SSConnection con, CommandSelect commandSelect) throws SQLException{
		try{
			this.view = new View( con, commandSelect);
			this.columnExpressions = commandSelect.columnExpressions;
			this.commandSelect     = commandSelect;
		}catch(Exception e){
			throw SmallSQLException.createFromException(e);
		}
	}
	
	
	/**
	 * Is used for compile() of different Commands
	 * 
	 * @param con
	 * @return true if now init; false if already init
	 * @throws Exception
	 */
	@Override
	boolean init( SSConnection con ) throws Exception{
		if(super.init(con)){
			commandSelect.compile(con);
			return true;
		}
		return false;
	}




/*=====================================================================
 * 
 * Methods of base class TableViewResult
 * 
 ====================================================================*/	
	
	@Override
	TableView getTableView(){
		return view;
	}
	
	
	@Override
	void deleteRow() throws SQLException{
		commandSelect.deleteRow(con);
	}

	@Override
	void updateRow(Expression[] updateValues) throws Exception{
		commandSelect.updateRow(con, updateValues);
	}
	
	@Override
	void insertRow(Expression[] updateValues) throws Exception{
		commandSelect.insertRow(con, updateValues);
	}

/*=====================================================================
 * 
 * Methods of interface DataSource
 * 
 ====================================================================*/	
	@Override
	boolean isNull(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).isNull();
	}


	@Override
	boolean getBoolean(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getBoolean();
	}


	@Override
	int getInt(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getInt();
	}


	@Override
	long getLong(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getLong();
	}


	@Override
	float getFloat(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getFloat();
	}


	@Override
	double getDouble(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getDouble();
	}


	@Override
	long getMoney(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getMoney();
	}


	@Override
	MutableNumeric getNumeric(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getNumeric();
	}


	@Override
	Object getObject(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getObject();
	}


	@Override
	String getString(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getString();
	}


	@Override
	byte[] getBytes(int colIdx) throws Exception {
		return columnExpressions.get(colIdx).getBytes();
	}


	@Override
	int getDataType(int colIdx) {
		return columnExpressions.get(colIdx).getDataType();
	}


	/*=====================================================
	 * 
	 * Methods of the interface RowSource
	 * 
	 =====================================================*/
	
	@Override
	void beforeFirst() throws Exception {
		commandSelect.beforeFirst();
	}


	@Override
	boolean isBeforeFirst() throws SQLException{
		return commandSelect.isBeforeFirst();
	}
	
	
	@Override
	boolean isFirst() throws SQLException{
		return commandSelect.isFirst();
	}
	
	
	@Override
	boolean first() throws Exception {
		return commandSelect.first();
	}


	@Override
	boolean previous() throws Exception{
		return commandSelect.previous();
	}
	
	
	@Override
	boolean next() throws Exception {
		return commandSelect.next();
	}


	@Override
	boolean last() throws Exception{
		return commandSelect.last();
	}
	
	
	@Override
	boolean isLast() throws Exception{
		return commandSelect.isLast();
	}


	@Override
	boolean isAfterLast() throws Exception{
		return commandSelect.isAfterLast();
	}
    

	@Override
	void afterLast() throws Exception{
		commandSelect.afterLast();
	}
	
	
	@Override
	boolean absolute(int row) throws Exception{
		return commandSelect.absolute(row);
	}
	
	
	@Override
	boolean relative(int rows) throws Exception{
		return commandSelect.relative(rows);
	}
	
	
	@Override
	int getRow() throws Exception{
		return commandSelect.getRow();
	}
	
	
	@Override
	long getRowPosition() {
		return commandSelect.from.getRowPosition();
	}


	@Override
	void setRowPosition(long rowPosition) throws Exception {
		commandSelect.from.setRowPosition(rowPosition);
	}


	@Override
	final boolean rowInserted(){
		return commandSelect.from.rowInserted();
	}
	
	
	@Override
	final boolean rowDeleted(){
		return commandSelect.from.rowDeleted();
	}
	
	
	@Override
	void nullRow() {
		commandSelect.from.nullRow();

	}


	@Override
	void noRow() {
		commandSelect.from.noRow();
	}

	
	@Override
	final void execute() throws Exception{
		commandSelect.from.execute();
	}
}
