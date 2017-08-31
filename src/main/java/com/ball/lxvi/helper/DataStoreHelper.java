package com.ball.lxvi.helper;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ball.lxvi.service.ProductService;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

public class DataStoreHelper {
	private static final Log log = LogFactory.getFactory().getInstance(DataStoreHelper.class);
	private static final String PROJECT_ID = "lxvi-164014";

	private static Datastore dataStore;

	public static Datastore getService() {
		if (dataStore == null) {
			dataStore = DatastoreOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
		}
		return dataStore;
	}

	public static Map<String, Object> toMap(Entity e) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String name : e.getNames()) {
			if (Entity.class.isAssignableFrom(e.getValue(name).get().getClass())) {
				map.put(name, toMap((Entity) e.getValue(name).get()));
			} else {
				map.put(name, e.getValue(name).get());
			}
		}
		return map;
	}

	public static List<Map<String, Object>> toList(QueryResults<Entity> results) {
		List<Map<String, Object>> list = new LinkedList<>();
		while (results.hasNext()) {
			list.add(toMap(results.next()));
		}
		return list;
	}

}
