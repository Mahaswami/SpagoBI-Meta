/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/
package it.eng.spagobi.meta.cwm.jmi.spagobi.meta.relational;

/**
 * SQLDataType object instance interface.
 * A SQLDataType is used to reference any datatype associated with a column
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmSqldataType extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmClassifier {
    /**
     * Returns the value of attribute typeNumber.
     * The number assigned to the datatype by the owning RDBMS
     * @return Value of attribute typeNumber.
     */
    public java.lang.Integer getTypeNumber();
    /**
     * Sets the value of typeNumber attribute. See {@link #getTypeNumber} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setTypeNumber(java.lang.Integer newValue);
}
