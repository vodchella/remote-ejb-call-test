package kz.umag.remote.test;

import kz.umag.nom.product_info.remote.IProductUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.naming.*;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class TestApplication {

	private static final Map<String, String> monolithModules = Map.of(
			"fin", "finance-ejb",
			"nom", "nomenclature-ejb",
			"opr", "operation-ejb",
			"org", "organization-ejb"
	);

	@Bean
	public Context context() throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProps.put("jboss.naming.client.ejb.context", true);
		jndiProps.put("java.naming.provider.url", "http-remoting://localhost:8080");
		return new InitialContext(jndiProps);
	}

	@Bean
	public IProductUnit productUnit(Context context) throws NamingException {
		return (IProductUnit) context.lookup(getFullPath(context, IProductUnit.class));
	}

	private String getMonolithModuleName(Class classType) {
		String[] parts = classType.getName().split("\\.");
		return monolithModules.get(parts[2]);
	}

	private String findRemoteBeanName(Context context,
									  String path,
									  Class classType) throws NamingException {

		String viewClassName = classType.getName();
		NamingEnumeration<NameClassPair> list = context.list(path);
		while (list.hasMore()) {
			String[] parts = list.next().getName().split("!");
			if (viewClassName.equals(parts[1])) {
				return parts[0];
			}
		}
		throw new NamingException(String.format("Class %s not found", viewClassName));
	}

	private String getFullPath(Context context,
							   Class classType) throws NamingException {

		String appName = "umag-backend-ear";
		String moduleName = getMonolithModuleName(classType);
		String path = String.format("%s/%s", appName, moduleName);
		findRemoteBeanName(context, path, classType);
		String beanName = findRemoteBeanName(context, path, classType);
		return String.format("ejb:%s/%s!%s", path, beanName, classType.getName());
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
