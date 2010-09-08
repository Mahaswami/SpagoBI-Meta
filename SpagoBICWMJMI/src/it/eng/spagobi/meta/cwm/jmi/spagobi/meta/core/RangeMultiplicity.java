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
package it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core;

/**
 * RangeMultiplicity association proxy interface.
 * The RangeMultiplicity association identifies the set of MultiplicityRange 
 * instances that specify the lower and upper bounds of individual cardinality 
 * ranges defined by a Multiplicity instance. A MultiplicityRange instance 
 * must be owned by a single Multiplicity instance.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface RangeMultiplicity extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicity multiplicity, it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicityRange range);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param range Required value of the second association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicity getMultiplicity(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicityRange range);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param multiplicity Required value of the first association end.
     * @return Collection of related objects.
     */
    public java.util.Collection getRange(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicity multiplicity);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     */
    public boolean add(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicity multiplicity, it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicityRange range);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     */
    public boolean remove(it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicity multiplicity, it.eng.spagobi.meta.cwm.jmi.spagobi.meta.core.CwmMultiplicityRange range);
}
