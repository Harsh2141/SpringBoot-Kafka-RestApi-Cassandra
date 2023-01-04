package com.order.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.InsertOptions.InsertOptionsBuilder;
import org.springframework.data.cassandra.core.UpdateOptions;
import org.springframework.data.cassandra.core.UpdateOptions.UpdateOptionsBuilder;
import org.springframework.data.cassandra.core.cql.QueryOptions;
import org.springframework.data.cassandra.core.cql.QueryOptions.QueryOptionsBuilder;
import org.springframework.data.cassandra.core.cql.WriteOptions;
import org.springframework.data.cassandra.core.cql.WriteOptions.WriteOptionsBuilder;
import org.springframework.stereotype.Repository;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import com.datastax.oss.driver.api.querybuilder.update.UpdateStart;
import com.order.entity.CounterTest;

@Repository
public class BaseRepository<E, K> {

	@Autowired
	CassandraTemplate cassandraTemplate;
	
	InsertOptionsBuilder builder = InsertOptions.builder().consistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
	InsertOptions options = builder.build();
	
	WriteOptionsBuilder writeBuilder = WriteOptions.builder().consistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
	WriteOptions writeOptions = writeBuilder.build();
	
	QueryOptionsBuilder queryBuilder = QueryOptions.builder().consistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
	QueryOptions queryOptions = queryBuilder.build();
	
	UpdateOptionsBuilder updateBuilder = UpdateOptions.builder().consistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
    UpdateOptions updateOptions = updateBuilder.build();

	ConsistencyLevel readConsistencyLevel = ConsistencyLevel.LOCAL_QUORUM;


	public List<E> findListByMultipleNaturalId(final Class<E> entityClass, final Map<String, Object> map) {

		Select select = QueryBuilder.selectFrom(cassandraTemplate.getTableName(entityClass).toString()).all()
				.allowFiltering();
		System.out.println(select);
		List<Relation> whereClauseList = getWhereClauseFromMap(map);
		SimpleStatement statement = select.where(whereClauseList).build().setConsistencyLevel(readConsistencyLevel);

		return cassandraTemplate.select(statement, entityClass);
	}

	private List<Relation> getWhereClauseFromMap(Map<String, Object> map) {
		List<Relation> whereClauseList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Relation relation = Relation.column(entry.getKey()).isEqualTo(QueryBuilder.literal(entry.getValue()));
			whereClauseList.add(relation);
		}
		return whereClauseList;
	}

	public List<E> findListByNaturalId(Class<E> entityClass, String idField, final K id) {

		Select select = QueryBuilder.selectFrom(cassandraTemplate.getTableName(entityClass).toString()).all()
				.allowFiltering();
		select = select.whereColumn(idField).isEqualTo(QueryBuilder.literal(id));
		return cassandraTemplate.select(select.build().setConsistencyLevel(readConsistencyLevel), entityClass);
	}
	
	
	private List<Relation> testMaxMap(Map<String, Object> max,List<Relation> whereClauseList) {
		for (Map.Entry<String, Object> entry : max.entrySet()) {
			Relation relation = Relation.column(entry.getKey()).isLessThan(QueryBuilder.literal(entry.getValue()));
			whereClauseList.add(relation);
		}
		return whereClauseList;
	}
	private List<Relation> testMinMap(Map<String, Object> min,List<Relation> whereClauseList) {
		for (Map.Entry<String, Object> entry : min.entrySet()) {
			Relation relation = Relation.column(entry.getKey()).isGreaterThan(QueryBuilder.literal(entry.getValue()));
			whereClauseList.add(relation);
		}
		return whereClauseList;
	}
	public List<E> findListByMultipleNaturalIdClone(final Class<E> entityClass, final Map<String, Object> map, final Map<String, Object> maxMap, final Map<String, Object> minMap) {

		Select select = QueryBuilder.selectFrom(cassandraTemplate.getTableName(entityClass).toString()).all()
				.allowFiltering();
		System.out.println(select);
		List<Relation> whereClauseList = getWhereClauseFromMap(map);
		if(maxMap != null) {
			whereClauseList = testMaxMap(maxMap,whereClauseList);
		} 
		if(minMap != null) {
			whereClauseList = testMinMap(minMap,whereClauseList);
		}
		SimpleStatement statement = select.where(whereClauseList).build().setConsistencyLevel(readConsistencyLevel);

		return cassandraTemplate.select(statement, entityClass);
	}
	
//	public E save(final E entity) {
//		cassandraTemplate.update(entity);
//		return cassandraTemplate.insert(entity, options).getEntity();
//	}
	public void save(final E entity) {
		cassandraTemplate.update(entity);
//		return cassandraTemplate.insert(entity, options).getEntity();
	}
	
	public E update1(final E entity) {
        
        Relation relation = Relation.column("tablename").isEqualTo(QueryBuilder.literal("waveid"));
        
        UpdateStart update1 = QueryBuilder.update(cassandraTemplate.getTableName(CounterTest.class).toString());
        Update u=update1.increment("counter",QueryBuilder.literal(1)).where(relation);
//        c.setCounter();
        System.out.println(u.toString());
//        cassandraTemplate.select(u,CounterTest.class);
//        cassandraTemplate.select(u.toString(),entity);
        CounterTest c = new CounterTest();
        c.setTablename("waveid");
        c.setCounter(BigInteger.valueOf(15));
//        cassandraTemplate.update(u.asCql(),updateOptions);
//        System.out.println(cassandraTemplate.insert(c,options));
        System.out.println(cassandraTemplate.update(c,updateOptions));
//        cassandraTemplate.getCqlOperations().execute(u.toString(),updateOptions);
//        cassandraTemplate.getCqlOperations().execute(u.toString());
//        System.out.println(findListByQueryString(null,u.toString()).toString());
//        System.out.println(cassandraTemplate.getCqlOperations().queryForResultSet(u.toString()));
//        cassandraTemplate.getCqlOperations().queryForRows(u.toString(), CounterTest.class);
//        System.out.println(cassandraTemplate.getCqlOperations().queryForResultSet(u.toString()).toString());
        	
//        cassandraTemplate.doupdate(u.toString(),entityClass);
//        doCount

//        Update update = QueryBuilder.update("counter_test").
//                
//                where(QueryBuilder.eq("tablename", "waveid"))
//                .with (QueryBuilder.incr( "counter", QueryBuilder.literal(1 ));

//        UpdateStart update1 = QueryBuilder.update(cassandraTemplate.getTableName(CounterTest.class).toString());
//        Update u = update1.increment("counter",QueryBuilder.literal(1)).whereColumn("tablename").isEqualTo(QueryBuilder.literal("waveid"));
//        cassandraTemplate.update(u);
        
        return entity;
    }

	@SuppressWarnings("unchecked")
	public E findListByQueryString(final Class<E> entityClass, final String queryString) {
		return (E) cassandraTemplate.getCqlOperations().queryForResultSet(queryString);
	}

}
