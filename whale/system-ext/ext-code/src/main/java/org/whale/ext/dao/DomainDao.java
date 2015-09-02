package org.whale.ext.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.whale.ext.domain.Domain;
import org.whale.system.base.BaseDao;
import org.whale.system.base.Query;
import org.whale.system.jdbc.util.DbKind;
@Repository
public class DomainDao extends BaseDao<Domain, Long> {

	public Domain getBySqlName(String sqlName){
		
		return this.getBy(Query.newQuery(Domain.class).addEq("sqlName", sqlName));
	}
	
	String queryAllTable_MYSQL =  "SELECT t.table_name AS name,"
								+ "		t.TABLE_COMMENT AS comments "
								+ " FROM information_schema.`TABLES` t "
								+ "	WHERE t.TABLE_SCHEMA = (select database()) "
								+ "ORDER BY t.TABLE_NAME";
	
	String queryAllTable_ORACLE = "SELECT t.TABLE_NAME AS name, "
								+ "		c.COMMENTS AS comments "
								+ " FROM user_tables t, user_tab_comments c"
								+ "	WHERE t.table_name = c.table_name "
								+ " ORDER BY t.TABLE_NAME";
	
	/**
	 * 获取所有数据库所有表
	 * @return
	 */
	public List<Map<String, Object>> queryAllTable(){
		if(DbKind.isMysql()){
			return this.queryForList(queryAllTable_MYSQL);
		}else{
			return this.queryForList(queryAllTable_ORACLE);
		}
	}
	
}
