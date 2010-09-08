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
package it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral;

/**
 * Parameter object instance interface.
 * Parameters are used in the specification of operations, methods and events. 
 * A Parameter may include a name, type, and direction of communication.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmParameter extends it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmModelElement {
    /**
     * Returns the value of attribute defaultValue.
     * An Expression whose evaluation yields a value to be used when no argument 
     * is supplied for the Parameter.
     * @return Value of attribute defaultValue.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmExpression getDefaultValue();
    /**
     * Sets the value of defaultValue attribute. See {@link #getDefaultValue} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setDefaultValue(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmExpression newValue);
    /**
     * Returns the value of attribute kind.
     * Specifies what kind of a Parameter is required.
     * @return Value of attribute kind.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.ParameterDirectionKind getKind();
    /**
     * Sets the value of kind attribute. See {@link #getKind} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setKind(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.ParameterDirectionKind newValue);
    /**
     * Returns the value of reference behavioralFeature.
     * @return Value of reference behavioralFeature.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.CwmBehavioralFeature getBehavioralFeature();
    /**
     * Sets the value of reference behavioralFeature. See {@link #getBehavioralFeature} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setBehavioralFeature(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.CwmBehavioralFeature newValue);
    /**
     * Returns the value of reference event.
     * @return Value of reference event.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.CwmEvent getEvent();
    /**
     * Sets the value of reference event. See {@link #getEvent} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setEvent(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.behavioral.CwmEvent newValue);
    /**
     * Returns the value of reference type.
     * @return Value of reference type.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmClassifier getType();
    /**
     * Sets the value of reference type. See {@link #getType} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setType(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmClassifier newValue);
}
