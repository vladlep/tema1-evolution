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
 * MemoryResult.java
 * ---------------
 * Author: Volker Berlin
 * 
 */
package smallsql.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import smallsql.database.language.Language;

/**
 * @author Volker Berlin
 *
 */
class MemoryResult extends DataSource {

	ExpressionValue[] currentRow;
    private final Columns columns = new Columns();
	private int rowIdx = -1;
	private List rowList = new ArrayList(); // List of ExpressionGroup[] 

	/**
	 * This constructor is only use for extended classes.
	 */
	MemoryResult(){/* should be empty */}
	
	
	/**
	 * Constructor for DatabaseMetaData. ResultSets that not based on a store.
	 */
	MemoryResult(Object[][] data, int colCount) throws SQLException{
        for(int c=0; c<colCount; c++){
            Column column = new Column();
            column.setDataType(SQLTokenizer.NULL);
            columns.add( column );
        }
		for(int r=0; r<data.length; r++){
			Object[] row = data[r];
            ExpressionValue[] rowValues = new ExpressionValue[row.length];
			addRow(rowValues);
			for(int c=0; c<colCount; c++){
                ExpressionValue expr = rowValues[c] = new ExpressionValue();
				expr.set( row[c], -1);
                Column column = columns.get(c);
                if(expr.getDataType() != SQLTokenizer.NULL){
                    column.setDataType(expr.getDataType());
                }
                if(expr.getPrecision() > column.getPrecision()){
                    column.setPrecision(expr.getPrecision());
                }
			}
		}
	}
	
	final void addRow(ExpressionValue[] row){
		rowList.add(row);
	}
    
    
    /**
     * Return a Column to described the meta data
     * @param colIdx  the index of the column starting with 0.
     */
    final Column getColumn(int colIdx){
        return columns.get(colIdx);
    }
    
    
    /**
     * Add a column to the list of Columns. This should call only form COnstructor.
     * @param column
     */
    final void addColumn(Column column){
        columns.add(column);
    }
	
	/*==============================================================================

		Methods for Interface RowSource

	==============================================================================*/
	
	
	@Override
	final boolean isScrollable(){
		return true;
	}

	
	@Override
	final void beforeFirst(){
		rowIdx = -1;
		currentRow = null;
	}
	
	@Override
	final boolean isBeforeFirst(){
		return rowIdx < 0 || rowList.size() == 0;
	}
    
	@Override
	final boolean isFirst(){
		return rowIdx == 0 && currentRow != null;
	}
    
	@Override
	final boolean first(){
		rowIdx = 0;
		return move();
	}
    
	@Override
	final boolean previous(){
		if(rowIdx-- < 0) rowIdx = -1;
		return move();
	}
	
	@Override
	final boolean next(){
		rowIdx++;
		return move();
	}
	
	@Override
	final boolean last(){
		rowIdx = rowList.size() - 1;
		return move();
	}
	
	
	@Override
	final boolean isLast(){
		return rowIdx == rowList.size() - 1 && currentRow != null;
	}
    
	@Override
	final boolean isAfterLast(){
		return rowIdx >= rowList.size() || rowList.size() == 0;
	}
    
	@Override
	final void afterLast(){
		rowIdx = rowList.size();
		currentRow = null;
	}
	
	
	@Override
	final boolean absolute(int row) throws SQLException{
		if(row == 0) throw SmallSQLException.create(Language.ROW_0_ABSOLUTE);
		rowIdx = (row > 0) ?
			Math.min( row - 1, rowList.size() ):
			Math.max( row +rowList.size(), -1 );
		return move();
	}
	
	
	@Override
	final boolean relative(int rows){
		if(rows == 0) return (currentRow != null);
		rowIdx = Math.min( Math.max( rowIdx + rows, -1), rowList.size());
		return move();
	}
	
	
	@Override
	final int getRow(){
		return currentRow == null ? 0 : rowIdx+1;
	}
	
	
	@Override
	final long getRowPosition(){
		return rowIdx;
	}
	
	
	@Override
	final void setRowPosition(long rowPosition) throws Exception{
		rowIdx = (int)rowPosition;
		move();
	}
	

	@Override
	final boolean rowInserted(){
		return false;
	}
	
	
	@Override
	final boolean rowDeleted(){
		return false;
	}
	
	
	@Override
	void nullRow(){
		throw new Error();
	}
	

	@Override
	void noRow(){
		currentRow = null;
	}
	

	final private boolean move(){
		if(rowIdx < rowList.size() && rowIdx >= 0){
			currentRow = (ExpressionValue[])rowList.get(rowIdx);
			return true;
		}
		currentRow = null;
		return false;
	}

	/*=======================================================================
 
		Methods for Data Access
 
	=======================================================================*/

	@Override
	boolean isNull( int colIdx ) throws Exception{
		return get( colIdx ).isNull();
	}

	
	@Override
	boolean getBoolean( int colIdx ) throws Exception{
		return get( colIdx ).getBoolean();
	}

	@Override
	int getInt( int colIdx ) throws Exception{
		return get( colIdx ).getInt();
	}

	@Override
	long getLong( int colIdx ) throws Exception{
		return get( colIdx ).getLong();
	}

	@Override
	float getFloat( int colIdx ) throws Exception{
		return get( colIdx ).getFloat();
	}

	@Override
	double getDouble( int colIdx ) throws Exception{
		return get( colIdx ).getDouble();
	}

	@Override
	long getMoney( int colIdx ) throws Exception{
		return get( colIdx ).getMoney();
	}

	@Override
	MutableNumeric getNumeric( int colIdx ) throws Exception{
		return get( colIdx ).getNumeric();
	}

	@Override
	Object getObject( int colIdx ) throws Exception{
		return get( colIdx ).getObject();
	}

	@Override
	String getString( int colIdx ) throws Exception{
		return get( colIdx ).getString();
	}
	

	@Override
	byte[] getBytes( int colIdx ) throws Exception{
		return get( colIdx ).getBytes();
	}
	

	@Override
	int getDataType( int colIdx ){
		return columns.get( colIdx ).getDataType();
		//return get( colIdx ).getDataType(); // problems if no currentRow
	}
	
	
	@Override
	final TableView getTableView(){
		return null;
	}
	
	
	final void deleteRow() throws Exception{
		throw SmallSQLException.create(Language.RSET_READONLY);
	}

	
	final void updateRow(Expression[] updateValues) throws Exception{
		throw SmallSQLException.create(Language.RSET_READONLY);
	}
	
	
	final void insertRow(Expression[] updateValues) throws Exception{
		throw SmallSQLException.create(Language.RSET_READONLY);
	}
	



/*====================================================
 Helper functions
 ===================================================*/
 
 	/**
 	 * Returns the current Expression for the columnIdx. 
 	 * The columnIdx starts at 0.
 	 * There is no index check. 
 	 */	
	private Expression get(int colIdx) throws Exception{
		if(currentRow == null) throw SmallSQLException.create(Language.ROW_NOCURRENT);
		return currentRow[ colIdx ];
	}
	

	/**
	 * Return size of the ResultSet.
	 */
	int getRowCount(){
		return rowList.size();
	}
	
	
	@Override
	void execute() throws Exception{
        rowList.clear();
	}
}
