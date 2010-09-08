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
package it.eng.spagobi.meta.cwm.jmi.spagobi.meta.transformation;

/**
 * Transformation object instance interface.
 * This represents a transformation from a set of sources to a set of targets. 
 * If a model already exists for the object that performs the Transformation, the
 * n the model can be related to the Transformation via a TransformationUse de
 * pendency.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmTransformation extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmNamespace {
    /**
     * Returns the value of attribute function.
     * Any code or script for the Transformation.
     * @return Value of attribute function.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmProcedureExpression getFunction();
    /**
     * Sets the value of function attribute. See {@link #getFunction} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setFunction(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmProcedureExpression newValue);
    /**
     * Returns the value of attribute functionDescription.
     * A short description for any code or script performed by the Transformation.
     * @return Value of attribute functionDescription.
     */
    public java.lang.String getFunctionDescription();
    /**
     * Sets the value of functionDescription attribute. See {@link #getFunctionDescription} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setFunctionDescription(java.lang.String newValue);
    /**
     * Returns the value of attribute isPrimary.
     * This Transformation is the primary transformation for the associated TransformationTask.
     * @return Value of attribute isPrimary.
     */
    public boolean isPrimary();
    /**
     * Sets the value of isPrimary attribute. See {@link #isPrimary} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setPrimary(boolean newValue);
    /**
     * Returns the value of reference source.
     * @return Value of reference source. Element type: {@link it.eng.spagobi.meta.cwm.jmi.spagobi.meta.transformation.CwmDataObjectSet}
     */
    public java.util.Collection/*<org.pentaho.pms.cwm.pentaho.meta.transformation.CwmDataObjectSet>*/ getSource();
    /**
     * Returns the value of reference target.
     * @return Value of reference target. Element type: {@link it.eng.spagobi.meta.cwm.jmi.spagobi.meta.transformation.CwmDataObjectSet}
     */
    public java.util.Collection/*<org.pentaho.pms.cwm.pentaho.meta.transformation.CwmDataObjectSet>*/ getTarget();
    /**
     * Returns the value of reference use.
     * @return Value of reference use. Element type: {@link it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmDependency}
     */
    public java.util.Collection/*<org.pentaho.pms.cwm.pentaho.meta.core.CwmDependency>*/ getUse();
}
