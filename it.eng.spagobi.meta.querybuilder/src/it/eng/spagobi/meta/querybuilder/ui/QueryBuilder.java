/**
 SpagoBI, the Open Source Business Intelligence suite

 Copyright (C) 2012 Engineering Ingegneria Informatica S.p.A. - SpagoBI Competency Center
 This Source Code Form is subject to the terms of the Mozilla Public
 License, v. 2.0. If a copy of the MPL was not distributed with this file,
 You can obtain one at http://mozilla.org/MPL/2.0/.
 
**/
package it.eng.spagobi.meta.querybuilder.ui;


import it.eng.qbe.datasource.IDataSource;
import it.eng.qbe.model.structure.FilteredModelStructure;
import it.eng.qbe.model.structure.IModelEntity;
import it.eng.qbe.model.structure.IModelField;
import it.eng.qbe.model.structure.IModelStructure;
import it.eng.qbe.model.structure.ModelCalculatedField;
import it.eng.qbe.model.structure.filter.IQbeTreeEntityFilter;
import it.eng.qbe.model.structure.filter.IQbeTreeFieldFilter;
import it.eng.qbe.model.structure.filter.QbeTreeAccessModalityEntityFilter;
import it.eng.qbe.model.structure.filter.QbeTreeAccessModalityFieldFilter;
import it.eng.qbe.model.structure.filter.QbeTreeFilter;
import it.eng.qbe.model.structure.filter.QbeTreeOrderEntityFilter;
import it.eng.qbe.model.structure.filter.QbeTreeOrderFieldFilter;
import it.eng.qbe.query.ExpressionNode;
import it.eng.qbe.query.Query;
import it.eng.qbe.query.WhereField;
import it.eng.qbe.query.WhereField.Operand;
import it.eng.qbe.statement.AbstractStatement;
import it.eng.spagobi.meta.querybuilder.dnd.QueryBuilderDragListener;
import it.eng.spagobi.meta.querybuilder.ui.editor.SpagoBIDataSetEditor;
import it.eng.spagobi.meta.querybuilder.ui.shared.edit.tables.QueryEditGroup;
import it.eng.spagobi.meta.querybuilder.ui.shared.edit.tree.ModelLabelProvider;
import it.eng.spagobi.meta.querybuilder.ui.shared.edit.tree.ModelTreeViewer;
import it.eng.spagobi.meta.querybuilder.ui.shared.result.ResultTableViewer;
import it.eng.spagobi.tools.dataset.common.query.AggregationFunctions;
import it.eng.spagobi.tools.dataset.common.query.IAggregationFunction;

import java.util.List;
import java.util.Set;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author cortella
 *
 */
public class QueryBuilder {

	protected IModelStructure modelView;
	protected Query query;
	protected IDataSource dataSource;
	private int whereFilterCount=1;
	private int havingFilterCount=1;
	private ResultTableViewer tableViewer = null;
	private QueryEditGroup compositeFilters = null;
	private ModelLabelProvider labelProvider = null;
	
	private static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	
	public QueryBuilder(IDataSource dataSource){
		
		logger.debug("IN: Creating QueryBuilder with DataSource [{}]",dataSource.getName());
		this.dataSource = dataSource;
		logger.debug("Creating Model Label Provider");
		labelProvider = new ModelLabelProvider(this.dataSource);
		IQbeTreeEntityFilter entityFilter = null;
		IQbeTreeFieldFilter fieldFilter = null;
		entityFilter = new QbeTreeAccessModalityEntityFilter();
		entityFilter = new QbeTreeOrderEntityFilter(entityFilter);
		fieldFilter = new QbeTreeAccessModalityFieldFilter();
		fieldFilter = new QbeTreeOrderFieldFilter(fieldFilter);
		QbeTreeFilter filters =  new QbeTreeFilter(entityFilter, fieldFilter);
		logger.debug("Get Datamart Model Structure from Datasource");
		IModelStructure iDatamartModelStructure = dataSource.getModelStructure();
		logger.debug("Get Model Names From Datamart Model Structure");
		Set modelnames = iDatamartModelStructure.getModelNames();
		//iDatamartModelStructure.getRootEntities(modelName)
		this.modelView =  new FilteredModelStructure(iDatamartModelStructure, dataSource, filters);
		this.query = new Query();
		logger.debug("OUT: Finish Creation of QueryBuilder with DataSource [{}]",dataSource.getName());
	}


	/*
	 * Create UI components for Query Edit
	 * @return the composite populated with widgets
	 */
	public Composite createEditComponents(Composite parent){
		logger.debug("IN: Create UI components for Query Edit");

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		//Create main grid with two columns
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		//Create Business Model Tree 
		logger.debug("Creating Business Model Tree UI");
		createEditBusinessModelTree(composite);

		//Create Query Filters
		logger.debug("Creating Query Filters UI");
		createEditGroup(composite);
		logger.debug("OUT: Finish creation of UI components for Query Edit");


		return container;
	}

	/*
	 * Create UI for Query Edit - Business Model Tree
	 */
	public void createEditBusinessModelTree(Composite composite){
		logger.debug("IN: Create UI for Query Edit - Business Model Tree");

		Composite compositeTree = new Composite(composite, SWT.NONE);
		GridData gd_compositeTree = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_compositeTree.widthHint = 180;
		compositeTree.setLayoutData(gd_compositeTree);
		compositeTree.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group groupBusinessModelTree = new Group(compositeTree, SWT.NONE);
		groupBusinessModelTree.setText("Business Model");
		groupBusinessModelTree.setLayout(new FillLayout(SWT.HORIZONTAL));

		//*******************************************
		// Business Model Tree Viewer 
		//*******************************************
		logger.debug("Creating Business Model Tree Viewer");
		ModelTreeViewer businessModelTreeViewer = new ModelTreeViewer(groupBusinessModelTree, dataSource, modelView, this);
		logger.debug("Finished creation of Business Model Tree Viewer");
		Transfer[] transferTypes = new Transfer[]{ TextTransfer.getInstance(),LocalSelectionTransfer.getTransfer()  };
		businessModelTreeViewer.addDragSupport(DND.DROP_MOVE, transferTypes, new QueryBuilderDragListener(businessModelTreeViewer));
		logger.debug("OUT: Finish Creation UI for Query Edit - Business Model Tree");

	}

	public QueryEditGroup createEditGroup(Composite composite){
		logger.debug("IN: Creating Query Edit Group");

		compositeFilters = new QueryEditGroup(composite, this);
		refreshQueryEditGroup();
		logger.debug("OUT: Created Query Edit Group");

		return compositeFilters;
	}

	/*
	 *  Create UI components for Query Results
	 *  @return the composite populated with widgets
	 */
	public Composite createResultsComponents(Composite parent){		
		logger.debug("IN: Creating Query Results UI Component");

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(container, SWT.NONE);
		FillLayout fl_composite = new FillLayout(SWT.HORIZONTAL);
		fl_composite.marginWidth = 2;
		fl_composite.marginHeight = 2;
		composite.setLayout(fl_composite);

		Group groupQueryResult = new Group(composite, SWT.NONE);
		groupQueryResult.setText("Query Result");
		GridLayout gl_groupQueryResult = new GridLayout(1, false);
		gl_groupQueryResult.marginRight = 1;
		gl_groupQueryResult.marginTop = 1;
		gl_groupQueryResult.marginLeft = 1;
		gl_groupQueryResult.marginBottom = 1;
		groupQueryResult.setLayout(gl_groupQueryResult);

		//Create Table widget to host results
		createResultsTableViewer(groupQueryResult);
		logger.debug("OUT: Created Query Results UI Component");


		return container;
	}

	/*
	 *  Create Table widget for Query Results
	 */
	public ResultTableViewer createResultsTableViewer(Group groupQueryResult){
		tableViewer = new ResultTableViewer(groupQueryResult, this);
		return tableViewer;
	}
	
	public void refreshQueryResultPage(){
		if(tableViewer!=null){
			tableViewer.updateTable();
		}
	}
	
	public void refreshQueryEditGroup(){
		if(compositeFilters!=null){
			compositeFilters.refresh(query);
		}
	}
	
	public void refreshSelectFields(){
		compositeFilters.refreshSelectTable(query.getSelectFields(true));
	}

	public Query addWhereField(IModelField dataMartField){
		boolean isCalculatedField = false;
		String fieldName = dataMartField.getParent().getName()+" : "+dataMartField.getName();
		String expression = null;
		if (dataMartField instanceof ModelCalculatedField){
			isCalculatedField = true;
			expression = ((ModelCalculatedField)dataMartField).getExpression();
		}		
		return addWhereField(dataMartField.getUniqueName(), fieldName, isCalculatedField, expression);
	}

	public Query addWhereField(String uniqueName, String fieldName, boolean isCalculatedField, String expression){
		String[] values = new String[1];
		values[0] = uniqueName;
		String operandType;
		if (isCalculatedField){
			operandType = AbstractStatement.OPERAND_TYPE_INLINE_CALCULATED_FIELD;
			JSONObject jsonobject = new JSONObject();
			try {
				jsonobject.put("expression",expression );
			} catch (JSONException e) {
				e.printStackTrace();
				logger.debug("Json Object error in addWhereField: "+e);
			}
			values[0] = jsonobject.toString();
		} else {
			operandType = AbstractStatement.OPERAND_TYPE_SIMPLE_FIELD;
		}
			

		Operand leftOperand = new Operand(values,fieldName, operandType, values,values);
		query.addWhereField("Filter"+whereFilterCount, "Filter"+whereFilterCount, true, leftOperand, "NONE", null, "AND");
		ExpressionNode node = query.getWhereClauseStructure();
		if(node==null){
			node = new ExpressionNode("NO_NODE_OP","$F{Filter" +whereFilterCount+"}");
			query.setWhereClauseStructure(node);
		}else{
			//get the previous field
			WhereField previousAddedField = (WhereField)query.getWhereFields().get(query.getWhereFields().size()-2);
			ExpressionNode operationNode = new ExpressionNode("NODE_OP", previousAddedField.getBooleanConnector());
			ExpressionNode filterNode = new ExpressionNode("NO_NODE_OP","$F{Filter" +whereFilterCount+"}");
			operationNode.addChild(node);
			operationNode.addChild(filterNode);
			query.setWhereClauseStructure(operationNode);
		}
		whereFilterCount++;
		return query;
	}

	public Query addHavingField(IModelField dataMartField){	
		boolean isCalculatedField = false;
		String fieldName = dataMartField.getParent().getName()+" : "+dataMartField.getName();
		String expression = null;
		if (dataMartField instanceof ModelCalculatedField){
			isCalculatedField = true;
			expression = ((ModelCalculatedField)dataMartField).getExpression();
		}	
		return addHavingField(dataMartField.getUniqueName(),fieldName, AggregationFunctions.NONE_FUNCTION, isCalculatedField, expression);
	}

	public Query addHavingField(String uniqueName, String fieldName,IAggregationFunction aggregation, boolean isCalculatedField, String expression){

		String[] values = new String[1];
		values[0] = uniqueName;
		String operandType;
		if (isCalculatedField){
			operandType = AbstractStatement.OPERAND_TYPE_INLINE_CALCULATED_FIELD;
			JSONObject jsonobject = new JSONObject();
			try {
				jsonobject.put("expression",expression );
			} catch (JSONException e) {
				e.printStackTrace();
				logger.debug("Json Object error in addHavingField: "+e);
			}
			values[0] = jsonobject.toString();
		} else {
			operandType = AbstractStatement.OPERAND_TYPE_SIMPLE_FIELD;
		}

		it.eng.qbe.query.HavingField.Operand leftOperand = new it.eng.qbe.query.HavingField.Operand(values, fieldName, operandType , values, values,aggregation);
		query = getQuery();
		query.addHavingField("Having"+havingFilterCount, "Having"+havingFilterCount, false, leftOperand, "NONE", null, "AND");

		havingFilterCount++;
		return query;

	}
	
	public Query addSelectFields(Object selectionData){
		logger.debug("SelectionData: "+selectionData.getClass().getName());
		if (selectionData instanceof IModelEntity){
   			logger.debug("Dragghed the entity [{}]",((IModelEntity)selectionData).getUniqueName());
   			IModelEntity dataMartEntity = (IModelEntity)selectionData;
			List<IModelField> dataMartFields = dataMartEntity.getAllFields();
			for (IModelField dataMartField : dataMartFields){
				addField(dataMartField);
			}        	
        } 
		else if(selectionData instanceof ModelCalculatedField){       	
        	addField((ModelCalculatedField)selectionData);
        }
		else if(selectionData instanceof IModelField){       	
        	addField((IModelField)selectionData);
        }
		return query;
	}
	
	public void addField(ModelCalculatedField dataMartCalculatedField){
		String alias = labelProvider.getLabel(dataMartCalculatedField);
		query.addInLineCalculatedFiled(alias, dataMartCalculatedField.getExpression(), null, dataMartCalculatedField.getType(), null, true, true, false, null, "NONE");
	}
	
	public void addField(IModelField dataMartField) {
		String alias = labelProvider.getLabel(dataMartField);
		query.addSelectFiled(dataMartField.getUniqueName(), "NONE", alias , true, true, false, null, dataMartField.getPropertyAsString("format"));
	}
	
	protected IEditorPart getEditorPart() {
		SpagoBIDataSetEditor dataSetEditor = null;
		IEditorPart editorPart = 
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editorPart instanceof SpagoBIDataSetEditor){
			dataSetEditor = (SpagoBIDataSetEditor)editorPart;
		}
		return dataSetEditor;
	}
	
	public void setDirtyEditor(){	
		SpagoBIDataSetEditor dataSetEditor = (SpagoBIDataSetEditor)getEditorPart();
		if (dataSetEditor != null){
			dataSetEditor.setDirty(true);
			dataSetEditor.fireDirty();
		}
	}
	
	public IDataSource getDataSource() {
		return dataSource;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public IModelStructure getModelView() {
		return modelView;
	}

	public IModelStructure getBaseModelStructure() {
		return modelView;
	}

	public ModelLabelProvider getLabelProvider() {
		return labelProvider;
	}
}
