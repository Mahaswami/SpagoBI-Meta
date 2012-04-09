/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

**/
package it.eng.spagobi.meta.test.initializer.mysql;

import it.eng.spagobi.meta.test.initializer.mysql.MySQLBusinessModelInizializtaionTest;
import it.eng.spagobi.meta.test.initializer.mysql.MySQLPhysicalModelInizializtaionTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public class MySqlInitializationTestSuite extends TestCase {
	static public Test suite() {
		TestSuite suite = new TestSuite("Initialization tests on MySql");
		suite.addTestSuite(MySQLPhysicalModelInizializtaionTest.class);
		suite.addTestSuite(MySQLBusinessModelInizializtaionTest.class);
		return suite;
	}
}