/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.meta.editor.business.wizards.inline;

import it.eng.spagobi.commons.resource.IResourceLocator;
import it.eng.spagobi.meta.editor.SpagoBIMetaEditorPlugin;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.physical.PhysicalColumn;
import it.eng.spagobi.meta.model.physical.PhysicalTable;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author cortella
 *
 */
public class AddBusinessTableWizardPageColumnSelection extends WizardPage {

	private AddBusinessTableWizardPagePhysicalTableSelection pageOneRef;
	private BusinessModel owner;
	private Table columns,fields;
	private TableItem[] columnsToImport;
	private boolean columnSelected = false;
	private PhysicalTable physicalTable;
	private Button bAddField, bRemoveField, bAddAllField, bRemoveAllField;
	
	private static final IResourceLocator RL = SpagoBIMetaEditorPlugin.getInstance().getResourceLocator(); 
	
	/**
	 * @param pageName
	 */
	protected AddBusinessTableWizardPageColumnSelection(String pageName,
			BusinessModel owner,
			AddBusinessTableWizardPagePhysicalTableSelection columnSelectionPage,
			PhysicalTable physicalTable) {
		super(pageName);
		setTitle(RL.getString("business.editor.wizard.addbusinessclass.title"));
		setDescription(RL.getString("business.editor.wizard.addbusinessclass.columnselection.description"));
		ImageDescriptor image = ImageDescriptor.createFromURL( (URL)RL.getImage("it.eng.spagobi.meta.editor.business.wizards.inline.createBC") );
		columnsToImport = null;
	    if (image!=null) setImageDescriptor(image);	
	    pageOneRef = columnSelectionPage;
	    this.physicalTable = physicalTable;
		this.owner = owner;
	}

	@Override
	public void createControl(Composite parent) {
		//Main composite
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		gl.makeColumnsEqualWidth = true;
		composite.setLayout(gl);
		
		Group columnsGroup = createColumnsGroup(composite, SWT.SHADOW_ETCHED_IN);

		createPhysicalColumnsGroup(columnsGroup, SWT.NONE);
		
		createButtonsGroup(columnsGroup, SWT.NONE);

		createBusinessColumnsGroup(columnsGroup, SWT.NONE);
		
		addListeners();
        
 		//Important: Setting page control
 		setControl(composite);
 		
 		//first check
 		checkPageComplete();
 		
 		//workaround: if a PhysicalTable is passed, use It to populate widgets
 		if (physicalTable != null){
 			addTableItems(physicalTable);
 			//pageOneRef.checkPageComplete();
 		}
	}

	public Group createColumnsGroup(Composite composite, int style){
		//Columns Group
		Group columnsGroup = new Group(composite, style);
		columnsGroup.setText(RL.getString("business.editor.wizard.addbusinessclass.columnselection.label"));
		GridLayout glColumns = new GridLayout();
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		glColumns.numColumns = 3;
		glColumns.makeColumnsEqualWidth = false;
		columnsGroup.setLayout(glColumns);
		columnsGroup.setLayoutData(gd2);
		
		return columnsGroup;
	}
	
	public void createPhysicalColumnsGroup(Composite composite, int style){
		//Physical Columns Group -------------------------------
		Composite compPhysicalColumns = new Composite(composite, SWT.NONE);
		GridLayout glL = new GridLayout();
		GridData gdL = new GridData(GridData.FILL_BOTH);
		glL.numColumns = 1;
		compPhysicalColumns.setLayout(glL);
		compPhysicalColumns.setLayoutData(gdL);
		Label lblLeftTab = new Label(compPhysicalColumns,SWT.NONE);
		lblLeftTab.setText(RL.getString("business.editor.wizard.addbusinessclass.physicalcolumnsselection"));
 		columns = new Table(compPhysicalColumns, SWT.BORDER | SWT.MULTI);
 		columns.setLayoutData(gdL);
	}
	
	public void createButtonsGroup(Composite composite, int style){
 		//Buttons Group -------------------------------
		Composite compButtons = new Composite(composite, SWT.NONE);
		GridLayout glC = new GridLayout();
		glC.numColumns = 1;
		compButtons.setLayout(glC);
	    bAddAllField = new Button(compButtons,SWT.FLAT);
		bAddAllField.setToolTipText(RL.getString("business.editor.wizard.addbusinessclass.columnselection.addallbutton.tooltip"));
		Image imageAddAll = ImageDescriptor.createFromURL( (URL)RL.getImage("it.eng.spagobi.meta.editor.business.wizards.inline.double_arrow_right") ).createImage();
	    if (imageAddAll!=null) bAddAllField.setImage(imageAddAll);
	    
		bAddField = new Button(compButtons,SWT.FLAT);
		bAddField.setToolTipText(RL.getString("business.editor.wizard.addbusinessclass.columnselection.addbutton.tooltip"));
		Image imageAdd = ImageDescriptor.createFromURL( (URL)RL.getImage("it.eng.spagobi.meta.editor.business.wizards.inline.arrow_right") ).createImage();
	    if (imageAdd!=null) bAddField.setImage(imageAdd);
		bRemoveField = new Button(compButtons,SWT.FLAT);
		bRemoveField.setToolTipText(RL.getString("business.editor.wizard.addbusinessclass.columnselection.removebutton.tooltip"));
		Image imageRem = ImageDescriptor.createFromURL( (URL)RL.getImage("it.eng.spagobi.meta.editor.business.wizards.inline.arrow_left") ).createImage();
	    if (imageRem!=null) bRemoveField.setImage(imageRem);	
	    
		bRemoveAllField = new Button(compButtons,SWT.FLAT);
		bRemoveAllField.setToolTipText(RL.getString("business.editor.wizard.addbusinessclass.columnselection.removeallbutton.tooltip"));
		Image imageRemAll = ImageDescriptor.createFromURL( (URL)RL.getImage("it.eng.spagobi.meta.editor.business.wizards.inline.double_arrow_left") ).createImage();
	    if (imageRemAll!=null) bRemoveAllField.setImage(imageRemAll);
	}
	
	public void createBusinessColumnsGroup(Composite composite, int style){
		//Business Columns Group -------------------------------
		Composite compBusinessColumns = new Composite(composite, SWT.NONE);
		GridLayout glR = new GridLayout();
		GridData gdR = new GridData(GridData.FILL_BOTH);
		glR.numColumns = 1;
		compBusinessColumns.setLayout(glR);
		compBusinessColumns.setLayoutData(gdR);
		Label lblRightTab = new Label(compBusinessColumns,SWT.NONE);
		lblRightTab.setText(RL.getString("business.editor.wizard.addbusinessclass.columnselection"));
 		fields = new Table(compBusinessColumns, SWT.BORDER | SWT.MULTI);
 		fields.setLayoutData(gdR);		
	}
	
	public void addListeners(){
		//adding listener to Add button		
 		bAddField.addListener(SWT.Selection, new Listener() {		
			@Override
			public void handleEvent(Event event) {
				TableItem tiSel = null;
				TableItem[] tiMultiSel = null;
				//single selection
				if (columns.getSelectionCount() == 1)
					tiSel = columns.getSelection()[0];
				//multi selection
				else if (columns.getSelectionCount() > 1){
					int selectionCount = columns.getSelectionCount();
					tiMultiSel = new TableItem[selectionCount];
					for (int i=0; i<selectionCount; i++){
						tiMultiSel[i] = columns.getSelection()[i];
					}
				}
				if (tiSel!= null){
					TableItem ti = new TableItem(fields, 0);
					ti.setText(tiSel.getText());
					ti.setData(tiSel.getData());
					columns.remove(columns.getSelectionIndex());
				}
				if (tiMultiSel != null){
					for (int i=0; i< tiMultiSel.length; i++){
						TableItem ti = new TableItem(fields, 0);
						ti.setText(tiMultiSel[i].getText());
						ti.setData(tiMultiSel[i].getData());											
					}
					columns.remove(columns.getSelectionIndices());
				}
				checkPageComplete();
			}
		});
 		
		//adding listener to Remove button		
 		bRemoveField.addListener(SWT.Selection, new Listener() {		
			@Override
			public void handleEvent(Event event) {
				TableItem tiSel = null;
				TableItem[] tiMultiSel = null;
				//single selection
				if (fields.getSelectionCount() == 1)
					tiSel = fields.getSelection()[0];
				//multi selection
				else if (fields.getSelectionCount() > 1){
					int selectionCount = fields.getSelectionCount();
					tiMultiSel = new TableItem[selectionCount];
					for (int i=0; i<selectionCount; i++){
						tiMultiSel[i] = fields.getSelection()[i];
					}
				}
				if (tiSel!= null){
					TableItem ti = new TableItem(columns, 0);
					ti.setText(tiSel.getText());
					ti.setData(tiSel.getData());					
					fields.remove(fields.getSelectionIndex());
				}
				if (tiMultiSel != null){
					for (int i=0; i< tiMultiSel.length; i++){
						TableItem ti = new TableItem(columns, 0);
						ti.setText(tiMultiSel[i].getText());
						ti.setData(tiMultiSel[i].getData());											
					}
					fields.remove(fields.getSelectionIndices());
				}
				checkPageComplete();

			}
		}); 	
 		
		//adding listener to Add All button		
 		bAddAllField.addListener(SWT.Selection, new Listener() {		
			@Override
			public void handleEvent(Event event) {
				TableItem[] columnToAdd = null;
				columnToAdd = columns.getItems();
				
				//add Fields to Business Class panel
				for (int i=0; i< columnToAdd.length; i++){
					TableItem ti = new TableItem(fields, 0);
					ti.setText(columnToAdd[i].getText());
					ti.setData(columnToAdd[i].getData());											
				}
				//Remove columns from Physical Table panel
				columns.removeAll();
				
				checkPageComplete();
			}
		});
 		
		//adding listener to Remove All button		
 		bRemoveAllField.addListener(SWT.Selection, new Listener() {		
			@Override
			public void handleEvent(Event event) {
				TableItem[] fieldsToRemove = null;
				fieldsToRemove = fields.getItems();
				
				//add Fields to Physical Table panel
				for (int i=0; i< fieldsToRemove.length; i++){
					TableItem ti = new TableItem(columns, 0);
					ti.setText(fieldsToRemove[i].getText());
					ti.setData(fieldsToRemove[i].getData());											
				}
				//Remove columns from Business Class panel 
				fields.removeAll();
				
				checkPageComplete();
			}
		});  		
	}
	
	//add the original physical columns as TableItem (in the left Table Widget)
	public void addTableItems(String tableName){		
		columns.removeAll();
		fields.removeAll();

		if (tableName != null) {
			//retrieve the Physical Table Columns
			PhysicalTable pTable = owner.getPhysicalModel().getTable(tableName);
			int numCols = pTable.getColumns().size();
			for (int i=0; i<numCols; i++){
				TableItem ti = new TableItem(columns, 0);
				PhysicalColumn pColumn = pTable.getColumns().get(i);
				//associate table item with the object It represents
				ti.setData(pColumn);
				ti.setText(pColumn.getName());
			}
		}
	}

	//add the original physical columns as TableItem (in the left Table Widget), whit PhysicalTable specified
	public void addTableItems(PhysicalTable physicalTable){		
		columns.removeAll();
		fields.removeAll();
		//retrieve the Physical Table Columns
		PhysicalTable pTable = physicalTable;
		int numCols = pTable.getColumns().size();
		for (int i=0; i<numCols; i++){
			TableItem ti = new TableItem(columns, 0);
			PhysicalColumn pColumn = pTable.getColumns().get(i);
			//associate table item with the object It represents
			ti.setData(pColumn);
			ti.setText(pColumn.getName());
		}

	}	
	
	
	//check if the right conditions to go forward occurred
	private void checkPageComplete(){
		if (pageOneRef != null){
			if (pageOneRef.isColumnSelection()){
				if(fields.getItemCount() > 0){
					setColumnSelected(true);				
					//store the Physical Columns selected
					setErrorMessage(null);
					setColumnsToImport(fields.getItems());
					setPageComplete(true);
				}
				else{			
					setErrorMessage(RL.getString("business.editor.wizard.addbusinessclass.columnselection.error.noselection"));
					setPageComplete(false);
				}
			}
		}
		else {
			if(fields.getItemCount() > 0){
				setColumnSelected(true);				
				//store the Physical Columns selected
				setErrorMessage(null);
				setColumnsToImport(fields.getItems());
				setPageComplete(true);
			}
			else{			
				setErrorMessage(RL.getString("business.editor.wizard.addbusinessclass.columnselection.error.noselection"));
				setPageComplete(false);
			}
			

		}
	}
	
	/**
	 * @param columnsToImport the columnsToImport to set
	 */
	public void setColumnsToImport(TableItem[] columnsToImport) {
		this.columnsToImport = columnsToImport;
	}

	/**
	 * @return the columnsToImport
	 */
	public TableItem[] getColumnsToImport() {
		return columnsToImport;
	}

	
	
	/**
	 * @param columnSelected the columnSelected to set
	 */
	public void setColumnSelected(boolean columnSelected) {
		this.columnSelected = columnSelected;
	}

	/**
	 * @return the columnSelected
	 */
	public boolean isColumnSelected() {
		return columnSelected;
	}	
}
