/* =============================================================
 * SmallSQL : a free Java DBMS library for the Java(tm) platform
 * =============================================================
 *
 * (C) Copyright 2004-2006, by Volker Berlin.
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
 * ExpressionFunctionLength.java
 * ---------------
 * Author: Volker Berlin
 * 
 * Created on 21.06.2004
 */
package smallsql.database;


/**
 * @author Volker Berlin
 */
final class ExpressionFunctionLength extends ExpressionFunctionReturnInt {


	@Override
	final int getFunction() {
		return SQLTokenizer.LENGTH;
	}


	@Override
	final int getInt() throws Exception {
		String str = param1.getString();
		if(str == null) return 0;
		int length = str.length();
		while(length>=0 && str.charAt(length-1) == ' ') length--;
		return length;
	}
}
