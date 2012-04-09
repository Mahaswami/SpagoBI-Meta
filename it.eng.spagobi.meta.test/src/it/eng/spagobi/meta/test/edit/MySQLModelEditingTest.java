package it.eng.spagobi.meta.test.edit;

import it.eng.spagobi.meta.model.ModelFactory;
import it.eng.spagobi.meta.model.business.BusinessIdentifier;
import it.eng.spagobi.meta.model.business.BusinessRelationship;
import it.eng.spagobi.meta.model.business.BusinessTable;
import it.eng.spagobi.meta.test.TestConnectionFactory;
import it.eng.spagobi.meta.test.TestCostants;

import java.util.List;


public class MySQLModelEditingTest extends AbstractModelEditingTest2{

	public void setUp() throws Exception {
		super.setUp();
		try {
			rootModel = ModelFactory.eINSTANCE.createModel();
			rootModel.setName("modelDemo");
			
			physicalModelInitializer.setRootModel(rootModel);		
			physicalModel = physicalModelInitializer.initialize( 
	        		"physicalModelDemo", 
	        		TestConnectionFactory.createConnection(dbType),
	        		"Test Connection",
	        		TestCostants.MYSQL_DRIVER,
	        		TestCostants.MYSQL_URL,
	        		TestCostants.MYSQL_USER,
	        		TestCostants.MYSQL_PWD,
	        		"DB Name",
	        		TestConnectionFactory.getDefaultCatalogue(dbType), 
	        		TestConnectionFactory.getDefaultSchema(dbType));
			
	        
	        businessModel = businessModelInitializer.initialize("businessModelDemo", physicalModel);
		} catch(Exception t) {
			System.err.println("An unespected error occurred during setUp: ");
			t.printStackTrace();
			throw t;
		}
	}
	
	// add specific test here
	
	
	public void testBusinessModelEditing() {
		BusinessTable businessTable = businessModel.getBusinessTables().get(0);
		BusinessIdentifier  businessIdentifier = businessTable.getIdentifier();
		List<BusinessRelationship> businessRelationships = businessTable.getRelationships();
		String businessTableName = businessTable.getName(); 
		
		assertNotNull("Business table cannot be null", businessModel.getBusinessTable(businessTableName));
		if(businessIdentifier != null) {
			assertNotNull("Business identifier cannot be null", businessModel.getIdentifier(businessTable));
		}
		if(businessRelationships != null && businessRelationships.size() > 0) {
			for(BusinessRelationship r : businessRelationships) {
				assertNotNull("Business identifier cannot be null", businessModel.getRelationships().contains(r));
			}
		}
		
		assertTrue("Impossible to delete table", businessModel.deleteBusinessTable(businessTableName));
		
		assertTrue( "Business Tabele has not been removes properly", businessModel.getBusinessTable(businessTableName) == null);
		
		if(businessIdentifier != null) {
			assertTrue("Business identifier cannot be null", businessModel.getIdentifier(businessTable) == null);
		}
		if(businessRelationships != null && businessRelationships.size() > 0) {
			for(BusinessRelationship r : businessRelationships) {
				assertTrue("Business identifier cannot be null", businessModel.getRelationships().contains(r) == false);
			}
		}
	}
}
