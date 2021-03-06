/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
 **/
package it.eng.spagobi.meta.editor.olap.actions;

import it.eng.spagobi.meta.editor.business.BusinessModelEditor;
import it.eng.spagobi.meta.editor.olap.editor.hierarchies.HierarchiesEditorMainPage;
import it.eng.spagobi.meta.initializer.OlapModelInitializer;
import it.eng.spagobi.meta.initializer.descriptor.HierarchyDescriptor;
import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessColumnSet;
import it.eng.spagobi.meta.model.olap.Dimension;
import it.eng.spagobi.meta.model.olap.OlapModel;
import it.eng.spagobi.meta.model.olap.commands.edit.dimension.EditHierarchiesCommand;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author cortella
 * 
 */
public class EditHierarchiesAction extends AbstractSpagoBIModelAction {

	private BusinessColumnSet businessColumnSet;
	private final Shell parentShell;
	private final BusinessModelEditor businessModelEditor;
	private final String projectName;

	/**
	 * @param commandClass
	 * @param workbenchPart
	 * @param selection
	 */
	public EditHierarchiesAction(IWorkbenchPart workbenchPart, ISelection selection) {
		super(EditHierarchiesCommand.class, workbenchPart, selection);
		parentShell = workbenchPart.getSite().getShell();
		businessModelEditor = (BusinessModelEditor) workbenchPart;
		projectName = businessModelEditor.getProjectName();
	}

	/**
	 * This executes the command.
	 */
	@Override
	public void run() {
		try {
			businessColumnSet = (BusinessColumnSet) owner;

			Model rootModel = businessColumnSet.getModel().getParentModel();
			OlapModel olapModel = rootModel.getOlapModels().get(0);
			OlapModelInitializer olapInitializer = new OlapModelInitializer();
			Dimension dimension = olapInitializer.getDimension(businessColumnSet);

			HierarchiesEditorMainPage hierarchiesEditor = new HierarchiesEditorMainPage(parentShell, olapModel, businessColumnSet, projectName);
			hierarchiesEditor.create();
			if (hierarchiesEditor.open() == Window.OK) {
				// Interaction Olap Model - Internal Hierarchy Descriptor

				List<HierarchyDescriptor> hierarchiesDescriptors = hierarchiesEditor.getHierarchiesDescriptors();
				// Erase All Hierarchies then construct object from scratch based on descriptors
				dimension.getHierarchies().clear();
				for (HierarchyDescriptor hierarchiesDescriptor : hierarchiesDescriptors) {
					olapInitializer.addHierarchy(dimension, hierarchiesDescriptor);
				}
				// Set the Business Model Editor to dirty to enable Save
				businessModelEditor.setDirty(true);
				businessModelEditor.firePropertyChange(IEditorPart.PROP_DIRTY);

			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
