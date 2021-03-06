/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.meta.model.business.commands.edit.table;

import it.eng.spagobi.meta.initializer.BusinessModelInitializer;
import it.eng.spagobi.meta.model.business.BusinessColumn;
import it.eng.spagobi.meta.model.business.BusinessColumnSet;
import it.eng.spagobi.meta.model.business.BusinessIdentifier;
import it.eng.spagobi.meta.model.business.BusinessTable;
import it.eng.spagobi.meta.model.business.commands.edit.AbstractSpagoBIModelEditCommand;
import it.eng.spagobi.meta.model.physical.PhysicalColumn;
import it.eng.spagobi.meta.model.physical.PhysicalTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public class AddColumnsToBusinessTable extends AbstractSpagoBIModelEditCommand {

	BusinessModelInitializer initializer;
	
	// input values
	BusinessColumnSet businessTable;
	Collection<PhysicalColumn> columnsToAdd;
	
	// cache edited columns (added and removed) for undo e redo
	List<BusinessColumn> addedColumns;
	BusinessIdentifier addedIdentifier;
	private PhysicalTable addedPhysicalTable;
	
	//check if Business Table has empty Physical Table reference
	boolean isEmptyBusinessTable = false;


	
		
	private static Logger logger = LoggerFactory.getLogger(ModifyBusinessTableColumnsCommand.class);
	
	public AddColumnsToBusinessTable(EditingDomain domain, CommandParameter parameter) {
		super( "model.business.commands.edit.table.addcolumns.label"
			 , "model.business.commands.edit.table.addcolumns.description"
			 , "model.business.commands.edit.table.addcolumns"
			 , domain, parameter);
		initializer = new BusinessModelInitializer();	
	}
	
	public AddColumnsToBusinessTable(EditingDomain domain) {
		this(domain, null);
	}
	
	protected void clearCachedObjects() {
		addedColumns = new ArrayList<BusinessColumn>();
		addedIdentifier = null;
	}
	
	@Override
	protected boolean prepare() {
		businessTable = (BusinessColumnSet)parameter.getOwner();
		columnsToAdd = (Collection)parameter.getValue();
		return (businessTable != null && columnsToAdd != null);
	}
	@Override
	public void execute() {
		
		clearCachedObjects();
		//check if BusinessTable has null Physical Table reference
		if (businessTable instanceof BusinessTable){
			BusinessTable bTable = (BusinessTable)businessTable;
			PhysicalTable physicalTable = bTable.getPhysicalTable();
			if (physicalTable == null){
				isEmptyBusinessTable = true;
			}
			else{
				isEmptyBusinessTable = false;
			}				
		}
		
		
		for(PhysicalColumn column: columnsToAdd) {
			if(businessTable.getSimpleBusinessColumn(column) == null) { // avoid columns duplication
				initializer.addColumn(column, businessTable);
				if (isEmptyBusinessTable){
					addedPhysicalTable = column.getTable();
					((BusinessTable)businessTable).setPhysicalTable(addedPhysicalTable);
					isEmptyBusinessTable = false;
				}
				addedColumns.add( businessTable.getSimpleBusinessColumn(column) );
			}
			
		}
		
		addIdentifier();
		
		executed = true;
	}
	
	@Override
	public void undo() {
		undoAddIdentifier();
		undoAddPhysicalTable();
		
		for(BusinessColumn column: addedColumns) {
			businessTable.getColumns().remove(column);
		}	
	}
	
	@Override
	public void redo() {
		execute();
	}
	
	private void addIdentifier() {
		if(businessTable instanceof BusinessTable) {
			BusinessTable table = (BusinessTable)businessTable;
			BusinessIdentifier identifier = table.getIdentifier();
			if(identifier == null) {
				addedIdentifier = initializer.addIdentifier(table, table.getModel());
			}
		}
	}
	
	private void undoAddIdentifier() {
		if(addedIdentifier != null) {
			businessTable.getModel().getIdentifiers().remove(addedIdentifier);
		}
	}
	
	private void undoAddPhysicalTable(){
		if (addedPhysicalTable != null){
			((BusinessTable)businessTable).setPhysicalTable(null);
		}
	}
	
	@Override
	public Collection<?> getAffectedObjects() {
		BusinessColumnSet businessTable = (BusinessColumnSet)parameter.getOwner();
		Collection affectedObjects = Collections.EMPTY_LIST;
		if(businessTable != null) {
			affectedObjects = new ArrayList();
			affectedObjects.add(businessTable);
		}
		return affectedObjects;
	}
	
	@Override
	public Collection<?> getResult() {
		// TODO the result here should change with operation type (execute = columns; undo = table)
		return getAffectedObjects();
	}

}
