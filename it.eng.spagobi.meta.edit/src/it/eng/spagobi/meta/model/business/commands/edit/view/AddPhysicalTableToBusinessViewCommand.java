/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.meta.model.business.commands.edit.view;

import it.eng.spagobi.meta.initializer.BusinessModelInitializer;
import it.eng.spagobi.meta.initializer.descriptor.BusinessViewInnerJoinRelationshipDescriptor;
import it.eng.spagobi.meta.model.business.BusinessColumnSet;
import it.eng.spagobi.meta.model.business.BusinessTable;
import it.eng.spagobi.meta.model.business.BusinessView;
import it.eng.spagobi.meta.model.business.commands.edit.AbstractSpagoBIModelEditCommand;
import it.eng.spagobi.meta.model.physical.PhysicalColumn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cortella
 *
 */
public class AddPhysicalTableToBusinessViewCommand extends AbstractSpagoBIModelEditCommand {
	
	BusinessColumnSet businessColumnSet;
	BusinessTable businessTable;
	BusinessView addedBusinessView;
	BusinessView businessView;
	BusinessViewInnerJoinRelationshipDescriptor joinRelationshipDescriptor;
	List<PhysicalColumn> addedColumns = new ArrayList<PhysicalColumn>();
		
	private static Logger logger = LoggerFactory.getLogger(AddPhysicalTableToBusinessViewCommand.class);
	
	
	public AddPhysicalTableToBusinessViewCommand(EditingDomain domain, CommandParameter parameter) {
		super( "model.business.commands.edit.view.addtable.label"
			 , "model.business.commands.edit.view.addtable.description"
			 , "model.business.commands.edit.view.addtable"
			 , domain, parameter);
	}
	
	public AddPhysicalTableToBusinessViewCommand(EditingDomain domain){
		this(domain, null);
	}
	
	@Override
	public void execute() {
		BusinessModelInitializer initializer = new BusinessModelInitializer();
		joinRelationshipDescriptor = (BusinessViewInnerJoinRelationshipDescriptor)parameter.getValue();
		businessColumnSet = (BusinessColumnSet)parameter.getOwner();
		
		//for BusinessTable upgrade to BusinessView
		if (businessColumnSet instanceof BusinessTable){
			businessTable = (BusinessTable)businessColumnSet;
			addedBusinessView = initializer.upgradeBusinessTableToBusinessView(businessTable, joinRelationshipDescriptor);
			
			//add columns in the join relationship to the BusinessView
			addedColumns.addAll(joinRelationshipDescriptor.getSourceColumns()) ;
			addedColumns.addAll(joinRelationshipDescriptor.getDestinationColumns());
			for (PhysicalColumn physicalColumn : addedColumns){
				//check if column is already present
				if (addedBusinessView.getSimpleBusinessColumn(physicalColumn) == null){
					initializer.addColumn(physicalColumn, addedBusinessView);
				}
			}
			addedColumns.clear();
			
		}
		//for BusinessView, update the Physical Tables
		else if (businessColumnSet instanceof BusinessView){
			businessView = (BusinessView)businessColumnSet;
			businessView = initializer.addPhysicalTableToBusinessView(businessView, joinRelationshipDescriptor);
			
			//add columns in the join relationship to the BusinessView
			addedColumns.addAll(joinRelationshipDescriptor.getSourceColumns()) ;
			addedColumns.addAll(joinRelationshipDescriptor.getDestinationColumns());
			for (PhysicalColumn physicalColumn : addedColumns){
				//check if column is already present
				if (businessView.getSimpleBusinessColumn(physicalColumn) == null){
					initializer.addColumn(physicalColumn, businessView);					
				}
			}
			addedColumns.clear();			
		}
		
		this.executed = true;
		logger.debug("Command [{}] executed succesfully", AddPhysicalTableToBusinessViewCommand.class.getName());
	}
	
	
	@Override
	public void undo() {
		BusinessModelInitializer initializer = new BusinessModelInitializer();
		//undo the upgrade of a BusinessTable to BusinessView
		if (businessTable != null){
			businessTable = initializer.downgradeBusinessViewToBusinessTable(addedBusinessView);
		} 
		//undo the update of the BusinessView
		else if (businessView != null){
			businessView = initializer.removePhysicalTableToBusinessView(businessView, joinRelationshipDescriptor);
		}
	}
	
	@Override
	public void redo() {
		BusinessModelInitializer initializer = new BusinessModelInitializer();
		//redo the upgrade of a BusinessTable to BusinessView
		if (businessTable != null){
			initializer.upgradeBusinessTableToBusinessView(businessTable, joinRelationshipDescriptor);
			
		} else if (businessView != null){
			initializer.addPhysicalTableToBusinessView(businessView, joinRelationshipDescriptor);
		}
	}
	
	@Override
	public Collection<?> getAffectedObjects() {
		Collection affectedObjects = Collections.EMPTY_LIST;
		if(businessView != null) {
			affectedObjects = new ArrayList();
			affectedObjects.add(businessView);
		} else if (addedBusinessView != null) {
			affectedObjects = new ArrayList();
			affectedObjects.add(addedBusinessView);
		} else if (businessTable != null){
			affectedObjects = new ArrayList();
			affectedObjects.add(businessTable);
		}
		return affectedObjects;
	}
	
}
