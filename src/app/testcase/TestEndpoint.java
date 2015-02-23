package app.testcase;

import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.PersistenceAware;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;

//@PersistenceAware
@Api(name = "testendpoint", version = "v1", namespace = @ApiNamespace(ownerDomain = "app.testcase", ownerName = "app.testcase", packagePath = ""))
public class TestEndpoint {
	


	@ApiMethod(name = "listTestObjects", httpMethod = HttpMethod.GET)
	public List<TestObject> listTestObjects() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query query=pm.newQuery(TestObject.class);
		List<TestObject> rc=(List<TestObject>) query.execute();
		
		pm.close();
		return rc;
	}
	
	@ApiMethod(name = "addTestObject", httpMethod = HttpMethod.POST)
	public TestObject addTestObject(TestObject testObject) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		pm.makePersistent(testObject);
		
		pm.close();
		return testObject;
	}
	
	@ApiMethod(name = "getTestObject", httpMethod = HttpMethod.GET)
	public TestObject getTestObject(@Named("myKey") String myKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TestObject rc=pm.getObjectById(TestObject.class, myKey);
		
		pm.close();
		return rc;
	}
	
	@ApiMethod(name = "updateTestObject", httpMethod = HttpMethod.PUT)
	public TestObject updateTestObject(@Named("myKey") String myKey, TestObject testObject) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TestObject rc=pm.getObjectById(TestObject.class, myKey);
		
		if (testObject.booleanField != null)
			rc.booleanField=testObject.booleanField;
		if (testObject.getStringField() != null)
			rc.setStringField(testObject.getStringField());
		if (testObject.listField != null)
			rc.listField=testObject.listField;
		
		pm.makePersistent(rc);
		
		pm.close();
		return rc;
	}
	

	

	
}
