package au.gov.amsa.configuration.properties;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author dxm
 * 
 */
public class EntityManagerConfiguration implements Configuration {

	private EntityManagerFactory entityManagerFactory;
	private String nameValueQuery;
	private Map<String, String> map;

	public final String getNameValueQuery() {
		return nameValueQuery;
	}

	public final void setNameValueQuery(String nameValueQuery) {
		this.nameValueQuery = nameValueQuery;
	}

	public final EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public final void setEntityManagerFactory(
			EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Override
	public final Object getProperty(String context, String name) {
		if (map == null)
			load();
		String property = name;
		if (context != null)
			property = context + "." + name;
		return map.get(property);
	}

	private void load() {
		EntityManager em = entityManagerFactory.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNativeQuery(nameValueQuery)
				.getResultList();
		map = new HashMap<String, String>();
		for (Object[] row : list) {
			map.put((String) row[0], (String) row[1]);
		}
		em.close();
	}

	@Override
	public final Enumeration<String> getPropertyNames(String context) {
		return Collections.enumeration(map.keySet());
	}

}
