package au.gov.amsa.configuration.properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigurationFromMap implements Configuration {

	private final Map<String, String> map;

	public ConfigurationFromMap(Map<String, String> map) {
		this.map = map;
	}

	public static Builder builder() {
		return new Builder();
	}

	public  static class Builder {
		private final Map<String,String> map= new HashMap<String,String>();
		
		public Builder add(String key, String value) {
			map.put(key,value);
			return this;
		}
		
		public ConfigurationFromMap build() {
			return new ConfigurationFromMap(map);
		}
	}

	@Override
	public Object getProperty(String context, String name) {
		if (context==null)
			return map.get(name);
		else 
			return map.get(context + "." + name);
	}

	@Override
	public Enumeration<String> getPropertyNames(String context) {
		//make a defensive copy
		Map<String,String> map = new HashMap<String,String>(this.map);
		final Iterator<String> it = map.keySet().iterator();
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}

			@Override
			public String nextElement() {
				return it.next();
			}
			
		};
	}

}
