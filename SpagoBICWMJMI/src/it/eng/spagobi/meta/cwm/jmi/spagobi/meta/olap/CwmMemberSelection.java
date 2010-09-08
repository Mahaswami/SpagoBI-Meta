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
package it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap;

/**
 * MemberSelection object instance interface.
 * MemberSelection represents an arbitrary subset of the members of a Dimension.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmMemberSelection extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmClass {
    /**
     * Returns the value of reference dimension.
     * @return Value of reference dimension.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmDimension getDimension();
    /**
     * Sets the value of reference dimension. See {@link #getDimension} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setDimension(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmDimension newValue);
    /**
     * Returns the value of reference memberSelectionGroup.
     * @return Value of reference memberSelectionGroup. Element type: {@link it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmMemberSelectionGroup}
     */
    public java.util.Collection/*<org.pentaho.pms.cwm.pentaho.meta.olap.CwmMemberSelectionGroup>*/ getMemberSelectionGroup();
}
