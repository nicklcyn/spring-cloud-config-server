package com.example.config.server.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class MyPropertiesHandler implements PropertySourceLoader {

	private static final Logger logger = LoggerFactory.getLogger(MyPropertiesHandler.class);

	@Override
	public String[] getFileExtensions() {
		return new String[] { "properties", "xml" };
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		Map<String, ?> properties = loadProperties(resource);
		if (properties.isEmpty()) {
			return Collections.emptyList();
		}
		return Collections.singletonList(new OriginTrackedMapPropertySource(name, properties));
	}

	private Map<String, ?> loadProperties(Resource resource) throws IOException {
		String filename = resource.getFilename();
		if (filename != null && filename.endsWith(".xml")) {
			return (Map) PropertiesLoaderUtils.loadProperties(resource);
		}
		return new OriginTrackedPropertiesLoader(resource).load();
	}

}
