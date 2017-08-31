package com.ball.lxvi.service;

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
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.ReadOption;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.Transaction;

@Service
public class ProductService {

	private static final Log log = LogFactory.getFactory().getInstance(ProductService.class);
	private static final String KIND = "product";
	private Datastore datastore;
	private KeyFactory productKey;

	public ProductService() {
		datastore = DataStoreHelper.getService();
		productKey = datastore.newKeyFactory().setKind(KIND);
	}

	public Map<String, Object> example() {
		Transaction transaction = datastore.newTransaction();
		Key key = datastore.allocateId(
				datastore.newKeyFactory().addAncestors(PathElement.of("Building", "A"), PathElement.of("Floor", 8))
						.setKind("sale_order").newKey());
		Entity e = Entity.newBuilder(key).set("created", DateTime.now())
				.set("product", datastore.get(productKey.newKey(5722646637445120L))).build();
		transaction.put(e);
		transaction.commit();
		return DataStoreHelper.toMap(e);
	}

	public long save(String name, double price, String description) {
		Transaction transaction = datastore.newTransaction();
		Key key = datastore.allocateId(productKey.newKey());
		try {

			Entity product = Entity.newBuilder(key).set("name", name)
					.set("description", StringValue.newBuilder(description).setExcludeFromIndexes(true).build())
					.set("price", price).set("created", DateTime.now()).set("leather", "japan-cow").set("done", false)
					.build();
			transaction.put(product);
			transaction.commit();
			log.info(product.toString());
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

		return key.getId();
	}

	public Map<String, Object> get(long id) {
		return DataStoreHelper.toMap(datastore.get(productKey.newKey(id)));

	}

	public boolean update(long id) {
		Transaction transaction = datastore.newTransaction();
		try {
			Entity entity = transaction.get(productKey.newKey(id));
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
		Entity entity = transaction.get(productKey.newKey(id));
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
		return DataStoreHelper.toList(datastore.run(query, ReadOption.eventualConsistency()));
	}

}
