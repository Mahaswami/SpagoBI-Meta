/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.meta.querybuilder.edit;

import it.eng.qbe.query.HavingField;
import it.eng.spagobi.meta.querybuilder.ui.QueryBuilder;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * @author cortella
 *
 */
public class HavingFilterColumnEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private QueryBuilder queryBuilder;
	/**
	 * @param viewer
	 */
	public HavingFilterColumnEditingSupport(TableViewer viewer, QueryBuilder queryBuilder) {
		super(viewer);
		this.viewer = viewer;
		this.queryBuilder = queryBuilder;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTable());
	}


	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((HavingField)element).getName();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((HavingField) element).setName(String.valueOf(value));
		viewer.refresh();
		
		queryBuilder.setDirtyEditor();
	}

}
