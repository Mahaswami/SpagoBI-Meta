package it.eng.spagobi.meta.test.initializer;

import it.eng.spagobi.meta.initializer.BusinessModelInitializer;
import it.eng.spagobi.meta.initializer.PhysicalModelInitializer;
import it.eng.spagobi.meta.model.Model;
import it.eng.spagobi.meta.model.business.BusinessModel;
import it.eng.spagobi.meta.model.physical.PhysicalModel;
import it.eng.spagobi.meta.test.TestCostants;
import junit.framework.TestCase;


public class AbstractModelInizializtaionTest extends TestCase {

	protected static TestCostants.DatabaseType dbType;
	protected static Model model;
	protected static PhysicalModel physicalModel;
	protected static BusinessModel businessModel;
	protected static PhysicalModelInitializer physicalModelInitializer;
	protected static BusinessModelInitializer businessModelInitializer;
	
	protected boolean tearDown = false;
	
    
	public AbstractModelInizializtaionTest() {
		super();
	}
	
	public void setUp() throws Exception {
		try {
			if(physicalModelInitializer == null)  physicalModelInitializer = new PhysicalModelInitializer();
			if(businessModelInitializer == null)  businessModelInitializer = new BusinessModelInitializer();
			tearDown = false;
		} catch(Exception t) {
			System.err.println("An unespected error occurred during setUp: ");
			t.printStackTrace();
			throw t;
		}
	}
	
	protected void tearDown() throws Exception {
		if(tearDown) {
			dbType = null;
			model=null;
			physicalModel=null;
			businessModel=null;
			physicalModelInitializer=null;
			businessModelInitializer=null;
		}
	}
	
	// add generic tests related to model here ...
	
	public void testModelInitializationSmoke() {
		assertNotNull("Metamodel cannot be null", model);
	}
	
	public void testPhysicalModelInitializationSmoke() {
		assertTrue("Metamodel must have one physical model ", model.getPhysicalModels().size() == 1);
	}
	
	public void testBusinessModelInitializationSmoke() {
		assertTrue("Metamodel must have one business model ", model.getBusinessModels().size() == 1);	
	}
}