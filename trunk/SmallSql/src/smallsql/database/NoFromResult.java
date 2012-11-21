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
 * .java
 * ---------------
 * Author: Volker Berlin
 * 
 * Created on 17.04.2004
 */
package smallsql.database;

/**
 * This class is used for SELECT command without FROM clause.
 * 
 * @author Volker Berlin
 */
final  class NoFromResult extends RowSource {

	private int rowPos; // 0-before; 1-on row; 2-after last 
	

	@Override
	final boolean isScrollable(){
		return true;
	}
	
	
	@Override
	final void beforeFirst(){
		rowPos = 0;
	}

	@Override
	final boolean isBeforeFirst(){
		return rowPos <= 0;
	}
    
	@Override
	final boolean isFirst(){
		return rowPos == 1;
	}
    
	@Override
	final boolean first(){
		rowPos = 1;
		return true;
	}

	@Override
	final boolean previous(){
		rowPos--;
		return rowPos == 1;
	}
	
	
	@Override
	final boolean next(){
		rowPos++;
		return rowPos == 1;
	}

	@Override
	final boolean last(){
		rowPos = 1;
		return true;
	}
	
	@Override
	final boolean isLast(){
		return rowPos == 1;
	}
    
	@Override
	final boolean isAfterLast(){
		return rowPos > 1;
	}
    
	@Override
	final void afterLast(){
		rowPos = 2;
	}
	
	@Override
	final boolean absolute(int row){
		rowPos = (row > 0) ?
			Math.min( row, 1 ) :
			Math.min( row +1, -1 );
		return rowPos == 1;
	}
	
	@Override
	final boolean relative(int rows){
		if(rows == 0) return rowPos == 1;
		rowPos = Math.min( Math.max( rowPos + rows, -1), 1);
		return rowPos == 1;
	}
	
	@Override
	final int getRow(){
		return rowPos == 1 ? 1 : 0;
	}
	
	@Override
	final long getRowPosition() {
		return rowPos;
	}
	

	@Override
	final void setRowPosition(long rowPosition){
		rowPos = (int)rowPosition;
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
	final void nullRow() {
		throw new Error();
	}
	

	@Override
	final void noRow() {
		throw new Error();
	}


	@Override
	final void execute() throws Exception{/* can be empty, nothing to do */}

    
    /**
     * @inheritDoc
     */
    @Override
	boolean isExpressionsFromThisRowSource(Expressions columns){
        //if there are a expression in the list then it can not from this RowSource
        return columns.size() == 0;
    }
}
