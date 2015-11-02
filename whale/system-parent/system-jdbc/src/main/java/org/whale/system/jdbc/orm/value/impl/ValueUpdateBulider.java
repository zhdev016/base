package org.whale.system.jdbc.orm.value.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.whale.system.common.exception.OrmException;
import org.whale.system.common.util.ListUtil;
import org.whale.system.jdbc.orm.entry.OrmColumn;
import org.whale.system.jdbc.orm.entry.OrmSql;
import org.whale.system.jdbc.orm.entry.OrmTable;
import org.whale.system.jdbc.orm.entry.OrmValue;
import org.whale.system.jdbc.util.AnnotationUtil;

@Component
public class ValueUpdateBulider {

	public OrmValue getUpdate(Object obj, OrmSql ormSql) {
		OrmValue ormValues = new OrmValue(ormSql);
		OrmTable ormTable = ormSql.getTable();
		
		//验证id或复合主键的值不能为空
		OrmColumn idCol = ormTable.getIdCol();
		
		if(AnnotationUtil.getFieldValue(obj, idCol.getField()) == null)
			throw new OrmException("对象 ["+obj.getClass().getName()+"] 中主键字段 ["+idCol.getAttrName()+"] 值不能为空");
		
		//验证乐观锁字段值不能为空
		OrmColumn optimisticLockCol = ormTable.getOptimisticLockCol();
		if(optimisticLockCol != null && AnnotationUtil.getFieldValue(obj, optimisticLockCol.getField()) == null){
			throw new OrmException("对象 ["+obj.getClass().getName()+"] 中乐观锁字段 ["+optimisticLockCol.getAttrName()+"] 值不能为空");
		}
		
		//只获取需要更新字段的值， updateable
		ormValues.setArgs(AnnotationUtil.getFieldValues(obj, ormSql.getFields()).toArray());
		
		return ormValues;
	}
	
	public OrmValue getUpdate(List<?> objs, OrmSql ormSql) {
		OrmValue ormValues = new OrmValue(ormSql);
		OrmTable ormTable = ormSql.getTable();
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(Object obj : objs){
			//验证id或复合主键的值不能为空
			OrmColumn idCol = ormTable.getIdCol();
			if(AnnotationUtil.getFieldValue(obj, idCol.getField()) == null)
				throw new OrmException("对象 ["+obj.getClass().getName()+"] 中主键字段 ["+idCol.getAttrName()+"] 值不能为空");
			//乐观锁 字段值不能为空
			OrmColumn optimisticLockCol = ormTable.getOptimisticLockCol();
			if(optimisticLockCol != null && AnnotationUtil.getFieldValue(obj, optimisticLockCol.getField()) == null){
				throw new OrmException("对象 ["+obj.getClass().getName()+"] 中乐观锁字段 ["+optimisticLockCol.getAttrName()+"] 值不能为空");
			}
			batchArgs.add(AnnotationUtil.getFieldValues(obj, ormSql.getFields()).toArray());
		}
		
		ormValues.setBatchArgs(batchArgs);
		return ormValues;
	}
	
	public OrmValue getUpdateNotNull(Object obj, OrmTable ormTable) {
		OrmValue ormValue = new OrmValue();
		List<OrmColumn> cols = ormTable.getOrmCols();
		
		List<Integer> argTypes = new ArrayList<Integer>();
		List<Field> fields = new ArrayList<Field>();
		List<OrmColumn> sCols = new ArrayList<OrmColumn>();
		List<Object> objs = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("update ");
		sql.append(ormTable.getTableDbName()).append(" t set ");
		
		//待更新字段
		Object val = null;
		for(OrmColumn col : cols){
			if(col.getIsId() || !col.getUpdateAble() ||(val = AnnotationUtil.getFieldValue(obj, col.getField())) == null) {
				//乐观锁 字段值不能为空
				if(col.getIsOptimisticLock()){
					throw new OrmException("对象 ["+obj.getClass().getName()+"] 中乐观锁字段 ["+col.getAttrName()+"] 值不能为空");
				}
				continue;
			}
			//乐观锁 t.version = t.version+1， 版本+1
			if(col.getIsOptimisticLock()){
				sql.append(" t.").append(col.getSqlName()).append("=(t.").append(col.getSqlName()).append("+1),");
				continue;
			}
				
			sql.append(" t.").append(col.getSqlName()).append("=?,");
			argTypes.add(col.getType());
			fields.add(col.getField());
			sCols.add(col);
			objs.add(val);
		}
		if(objs.size() < 1){
			throw new OrmException("查找不到需要更新的字段");
		}
		
		sql.deleteCharAt(sql.length()-1);
		sql.append(" where ");
		OrmColumn idCol = ormTable.getIdCol();
		sql.append("t.").append(idCol.getSqlName()).append("=?");
		argTypes.add(idCol.getType());
		fields.add(idCol.getField());
		sCols.add(idCol);
		val = AnnotationUtil.getFieldValue(obj, idCol.getField());
		if(val == null)
			throw new OrmException("对象 ["+obj.getClass().getName()+"] 中主键字段 ["+idCol.getAttrName()+"] 值不能为空");
		objs.add(val);
		
		//乐观锁  AND t.version = ?
		OrmColumn optimisticLockCol = ormTable.getOptimisticLockCol();
		if(optimisticLockCol != null){
			sql.append(" AND t.").append(optimisticLockCol.getSqlName()).append("=?");
			argTypes.add(optimisticLockCol.getType());
			fields.add(optimisticLockCol.getField());
			sCols.add(optimisticLockCol);
			val = AnnotationUtil.getFieldValue(obj, optimisticLockCol.getField());
			if(val == null)
				throw new OrmException("对象 ["+obj.getClass().getName()+"] 中乐观锁字段 ["+idCol.getAttrName()+"] 值不能为空");
			objs.add(val);
		}
		
		ormValue.setSql(sql.toString());
		ormValue.setFields(fields);
		ormValue.setArgTypes(ListUtil.toArray(argTypes));
		ormValue.setTable(ormTable);
		ormValue.setOpType(OrmSql.OPT_UPDATE_ONLY);
		ormValue.setCols(sCols);
		ormValue.setArgs(objs.toArray());
		
		return ormValue;
	}

}
