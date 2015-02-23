package app.testcase;

import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.api.server.spi.config.ApiResourceProperty;

@PersistenceCapable
public class TestObject {
	
    @PrimaryKey
	@ApiResourceProperty(name = "id")
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	public String key;

    @Persistent
	public List<String> listField;
	
    @Persistent
	public Boolean booleanField;
	
    @Persistent
	public String stringField;

}
