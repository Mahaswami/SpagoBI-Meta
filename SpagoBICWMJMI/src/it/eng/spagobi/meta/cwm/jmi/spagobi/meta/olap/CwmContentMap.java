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
 * ContentMap object instance interface.
 * ContentMap is a subclass of TransformationMap that maps CubeRegion attributes 
 * to their physical data sources.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmContentMap extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.transformation.CwmTransformationMap {
    /**
     * Returns the value of reference cubeDeployment.
     * @return Value of reference cubeDeployment.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmCubeDeployment getCubeDeployment();
    /**
     * Sets the value of reference cubeDeployment. See {@link #getCubeDeployment} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setCubeDeployment(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.olap.CwmCubeDeployment newValue);
}
