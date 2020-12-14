package restapi;


import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

@Resource.Classpath("ApplicationConfig.properties")
public class ApplicationConfigReader 
{

	public ApplicationConfigReader()
	{
		PropertyLoader.newInstance().populate(this);
	}

	@Property(value = "baseURL")
	private String baseURL;

	@Property(value = "guid")
	private String guid;

	@Property(value = "login")
	private String login;

	@Property(value = "password")
	private String password;

	public String getBaseURL() {
		return baseURL;
	}

	public String getGuid() {
		return guid;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
}
