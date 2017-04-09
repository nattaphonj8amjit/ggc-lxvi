package com.ball.lxvi.helper;

import org.springframework.stereotype.Component;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

@Component
public class DataStoreHelper {

	private static final String PROJECT_ID = "lxvi-164014";

	private static Datastore dataStore;

	public static Datastore getService() {
		if (dataStore == null) {
			dataStore = DatastoreOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
		}
		return dataStore;
	}

}
