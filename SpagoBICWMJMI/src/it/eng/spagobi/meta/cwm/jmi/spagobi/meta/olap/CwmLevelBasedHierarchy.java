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
 * LevelBasedHierarchy object instance interface.
 * A LevelBasedHierarchy is a hierarchy that describes relationships between 
 * specific levels of a Dimension. LevelBasedHierarchy can be used to model 
 * to "pure level" hierarchies (e.g., dimension-level tables) and "mixed" 
 * hierarchies (i.e., levels + linked nodes).
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmLevelBasedHierarchy extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmHierarchy {
    /**
     * Returns the value of reference hierarchyLevelAssociation.
     * @return Value of reference hierarchyLevelAssociation. Element type: {@link 
     * it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmHierarchyLevelAssociation}
     */
    public java.util.List/*<org.pentaho.pms.cwm.pentaho.meta.olap.CwmHierarchyLevelAssociation>*/ getHierarchyLevelAssociation();
}
