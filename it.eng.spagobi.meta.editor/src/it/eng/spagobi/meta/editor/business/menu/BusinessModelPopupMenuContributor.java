/**

SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2010 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

**/
package it.eng.spagobi.meta.editor.business.menu;

import it.eng.spagobi.meta.editor.business.actions.RefreshViewerAction;
import it.eng.spagobi.meta.editor.business.actions.ShowPropertiesViewAction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public class BusinessModelPopupMenuContributor {

	ISelection selection;
	Collection<?> newChildDescriptors;
	Collection<?> newSiblingDescriptors;
	IEditorPart activeEditorPart;
	
	RefreshViewerAction refreshViewerAction;
	ShowPropertiesViewAction showPropertiesViewAction;
	
	public BusinessModelPopupMenuContributor() {
		refreshViewerAction = new RefreshViewerAction();
		showPropertiesViewAction = new ShowPropertiesViewAction();
	}
	
	public void menuAboutToShow(IMenuManager menuManager) {
		
		Map<String, Collection<IAction>> actions;
		MenuManager submenuManager;
		
		actions = BusinessModelMenuActionFactory.getActions(activeEditorPart, newChildDescriptors, selection);
		Iterator<String> it = actions.keySet().iterator();
		while(it.hasNext()) {
			String menuName = it.next();
			submenuManager = new MenuManager(menuName);
			populateManager(submenuManager, actions.get(menuName), null);
			menuManager.insertBefore("edit", submenuManager);
		}
		
		menuManager.insertAfter("additions-end", new Separator("ui-actions"));
		menuManager.insertAfter("ui-actions", showPropertiesViewAction);
		menuManager.insertAfter("ui-actions", refreshViewerAction);
	}
	
	public void setActiveEditor(IEditorPart activeEditorPart) {
		refreshViewerAction.setActiveEditorPart(activeEditorPart);
		showPropertiesViewAction.setActiveEditorPart(activeEditorPart);
	}
	
	public void selectionChanged(IEditorPart activeEditorPart, SelectionChangedEvent event) {
	
		this.activeEditorPart = activeEditorPart;
		selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
			Object object = ((IStructuredSelection)selection).getFirstElement();

			EditingDomain domain = ((IEditingDomainProvider)activeEditorPart).getEditingDomain();

			newChildDescriptors = domain.getNewChildDescriptors(object, null);
			newSiblingDescriptors = domain.getNewChildDescriptors(null, object);
		}
	}
	
	/**
	 * This populates the specified <code>manager</code> with {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection,
	 * by inserting them before the specified contribution item <code>contributionID</code>.
	 * If <code>contributionID</code> is <code>null</code>, they are simply added.
	 */
	protected void populateManager(IContributionManager manager, Collection<? extends IAction> actions, String contributionID) {
		if (actions != null) {
			for (IAction action : actions) {
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				}
				else {
					manager.add(action);
				}
			}
		}
	}
}
