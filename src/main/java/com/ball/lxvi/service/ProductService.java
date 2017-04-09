package com.ball.lxvi.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ball.lxvi.helper.DataStoreHelper;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.Transaction;

@Service
public class ProductService {

	private static final Log log = LogFactory.getFactory().getInstance(ProductService.class);
	private static final String KIND = "product";
	private Datastore datastore;
	private KeyFactory keyFactory;

	public ProductService() {
		datastore = DataStoreHelper.getService();
		keyFactory = datastore.newKeyFactory().setKind(KIND);
	}

	public long save(String name, double price, String description) {
		Transaction transaction = datastore.newTransaction();
		Key key = datastore.allocateId(keyFactory.newKey());
		try {
			Entity product = Entity.newBuilder(key).set("name", name)
					.set("description", StringValue.newBuilder(description).setExcludeFromIndexes(true).build())
					.set("price", price).set("created", DateTime.now()).set("leather", "japan-cow").set("done", false)

					.build();
			transaction.put(product);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
		return key.getId();
	}

	public Map<String, Object> get(long id) {
		return toMap(datastore.get(keyFactory.newKey(id)));

	}

	public boolean update(long id) {
		Transaction transaction = datastore.newTransaction();
		try {
			Entity entity = transaction.get(keyFactory.newKey(id));
			if (entity != null) {
				transaction.put(Entity.newBuilder(entity).set("done", true).build());
				transaction.commit();
			}
			return entity != null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public boolean delete(long id) {
		Transaction transaction = datastore.newTransaction();
		Entity entity = transaction.get(keyFactory.newKey(id));
		try {
			if (entity != null) {
				transaction.delete(entity.getKey());
				transaction.commit();
			}
			return entity != null;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public List<Map<String, Object>> query() {
		Query<Entity> query = Query.newEntityQueryBuilder().setKind(KIND).setFilter(PropertyFilter.eq("done", true))
				.setOrderBy(OrderBy.asc("price")).setLimit(10).build();
		return toList(datastore.run(query));
	}

	public Map<String, Object> toMap(Entity e) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String name : e.getNames()) {
			map.put(name, e.getValue(name).get());
		}
		return map;
	}

	public List<Map<String, Object>> toList(QueryResults<Entity> results) {
		List<Map<String, Object>> list = new LinkedList<>();
		while (results.hasNext()) {
			list.add(toMap(results.next()));
		}
		return list;
	}

}
