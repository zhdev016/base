package org.whale.system.jdbc.orm.sql;

import org.whale.system.jdbc.orm.entry.OrmSql;
import org.whale.system.jdbc.orm.entry.OrmTable;


/**
 * 创建对象不同操作类型对应的@OrmSql
 *
 * @author wjs
 * 2014年9月6日-下午1:54:15
 */
public interface SqlBuilder {

	OrmSql build(OrmTable ormTable, int opType);
}
