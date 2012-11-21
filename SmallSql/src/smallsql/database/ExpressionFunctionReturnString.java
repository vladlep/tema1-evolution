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
 * ExpressionFunctionString.java
 * ---------------
 * Author: Volker Berlin
 * 
 * Created on 23.06.2006
 */
package smallsql.database;


/**
 * @author Volker Berlin
 */
abstract class ExpressionFunctionReturnString extends ExpressionFunction {


    @Override
	boolean isNull() throws Exception {
		return param1.isNull();
	}


	@Override
	final boolean getBoolean() throws Exception {
		if(isNull()) return false;
		return Utils.string2boolean(getString().trim());
	}


	@Override
	final int getInt() throws Exception {
		if(isNull()) return 0;
		return Integer.parseInt(getString().trim());
	}


	@Override
	final long getLong() throws Exception {
		if(isNull()) return 0;
		return Long.parseLong(getString().trim());
	}


	@Override
	final float getFloat() throws Exception {
		if(isNull()) return 0;
		return Float.parseFloat(getString().trim());
	}


	@Override
	final double getDouble() throws Exception {
		if(isNull()) return 0;
		return Double.parseDouble(getString().trim());
	}


	@Override
	final long getMoney() throws Exception {
		if(isNull()) return 0;
		return Money.parseMoney(getString().trim());
	}


	@Override
	final MutableNumeric getNumeric() throws Exception {
		if(isNull()) return null;
		return new MutableNumeric(getString().trim());
	}


	@Override
	final Object getObject() throws Exception {
		return getString();
	}
}
