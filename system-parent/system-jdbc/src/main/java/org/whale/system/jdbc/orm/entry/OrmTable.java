package org.whale.system.jdbc.orm.entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.whale.system.common.util.Strings;


/**
 * orm实体表信息
 *
 * @author wjs
 * 2014年9月6日-下午1:52:01
 */
public class OrmTable extends Atable {

	/** table 数据库名 */
	private String tableDbName;
	/** table 中文名 */
	private String tableCnName;
	
	/** 序列 */
	private String sequence;
	/** 数据库 所对应的拥有者  */
	private String tableSchemaOwner;
	/** 保证插入只有一条记录，重复记录将被忽略 */
	private boolean unique;
	/** 主键字段 */
	private OrmColumn idCol;
	/** 所有 @Column 字段 */
	private List<OrmColumn> ormCols;
	/** Map<attrName, OrmColumn> 所有 @Column 字段 */
	private Map<String, OrmColumn> ormColMap;
	/** 所有 @Order 字段 */
	private List<OrmColumn> orderCols;
	/** @OptimisticLock 乐观锁字段 */
	private OrmColumn optimisticLockCol;
	
	//-----------------用户自定义元注释 信息读取保存---------------
	
	private Map<String, Object> extInfo;
	
	//----------------- 提高效率, 预先读取 ------------
	/** */
	private String sqlColPrexs;
	/** @Order 对应sql的Order排序尾巴 */
	private String sqlOrderSuffixStr;
	/** @Order 对应sql的Select 字段集合头部 */
	private StringBuilder sqlHeadPrefixStr;
//	/**非空检查字段集合 */
//	private List<OrmColumn> notNullCheckCols;
	/**唯一性检查字段集合 */
	private List<OrmColumn> uniqueCheckCols;
	/**预先执行SQL集合 */
	private List<OrmColumn> sqlExecCols;
	/**待校验检查字段集合 */
	private List<OrmColumn> validateCols;
	
	/**存在默认值的字段集合，主要用于getObject(T t) 和 query(T t)的模糊查询，防止初始值污染查询条件 */
	private List<OrmColumn> valueCols;
	
	/**java字段名对应数据库字段名 */
	private Map<String, String> javaAsSqlColumn = new HashMap<String, String>();
	/**数据库字段名对应java字段名 */
	private Map<String, String> sqlAsJavaColumn = new HashMap<String, String>();
	
	
	public OrmTable(){
		super();
	}
	
	public OrmTable(Atable table){
		super();
		this.setClazz(table.getClazz());
		this.setCols(table.getCols());
		this.setEntityName(table.getEntityName());
		this.setParent(table.getParent());
		this.setSuperClazz(table.getSuperClazz());
	}
	
	
	public String getSqlColPrexs(){
		if(Strings.isBlank(sqlColPrexs)){
			StringBuilder strb = new StringBuilder(80);
			for(OrmColumn ormCol : ormCols){
				if (ormCol.getIsId() && ormCol.getIdIgnore()){
					continue;
				}
				strb.append(",").append(ormCol.getSqlName());
			}
			if(strb.length() > 1){
				strb.deleteCharAt(0);
			}
			this.sqlColPrexs = strb.toString();
		}
		return sqlColPrexs;
	}
	
	/**
	 * 返回SQL排序字符
	 * 
	 * 取消默认按ID排序，提供效率
	 */
	public String getSqlOrderSuffix(){
		if(sqlOrderSuffixStr == null){
			StringBuilder strb = new StringBuilder(20);
			if(orderCols != null && orderCols.size() > 0){
				strb.append(" ORDER BY ");
				for(OrmColumn orderCol : orderCols){
					strb.append(orderCol.getSqlName()).append(orderCol.getOrmOrder().getAsc()? " asc," : " desc,");
				}
				strb.deleteCharAt(strb.length()-1);
			}
			sqlOrderSuffixStr = strb.toString();
		}
		
		return sqlOrderSuffixStr;
	}
	
	/**
	 * 取得当前对象的select t.* from db t
	 * 
	 * @return
	 * 2015年6月14日 上午7:57:01
	 */
	public String getSqlHeadPrefix(){
		if(sqlHeadPrefixStr == null){
			StringBuilder strb = new StringBuilder(100);
			strb.append("SELECT ").append(getSqlColPrexs()).append(" ")
				.append("FROM ").append(this.getTableDbName());
			sqlHeadPrefixStr = strb;
		}
		return sqlHeadPrefixStr.toString();
	}
	
	/**
	 * 
	 *功能说明: 是否有主键字段
	 *创建人: wjs
	 *创建时间:2013-3-19 上午10:01:10
	 *@return boolean
	 *
	 */
//	public boolean hasIdCol(){
//		return idCol != null;
//	}
	
	public String getTableDbName() {
		return tableDbName;
	}
	public void setTableDbName(String tableDbName) {
		this.tableDbName = tableDbName;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getTableSchemaOwner() {
		return tableSchemaOwner;
	}
	public void setTableSchemaOwner(String tableSchemaOwner) {
		this.tableSchemaOwner = tableSchemaOwner;
	}
	public OrmColumn getIdCol() {
		return idCol;
	}
	public void setIdCol(OrmColumn idCol) {
		this.idCol = idCol;
	}
	public List<OrmColumn> getOrmCols() {
		return ormCols;
	}
	public void setOrmCols(List<OrmColumn> ormCols) {
		this.ormCols = ormCols;
		this.ormColMap = new HashMap<String, OrmColumn>();
		for(OrmColumn ormColumn : ormCols){
			this.ormColMap.put(ormColumn.getAttrName(), ormColumn);
		}
	}
	public String getTableCnName() {
		return tableCnName;
	}
	public void setTableCnName(String tableCnName) {
		this.tableCnName = tableCnName;
	}
	public List<OrmColumn> getOrderCols() {
		return orderCols;
	}
	public void setOrderCols(List<OrmColumn> orderCols) {
		this.orderCols = orderCols;
	}
	
//	public List<OrmColumn> getNotNullCheckCols() {
//		return notNullCheckCols;
//	}
//
//	public void setNotNullCheckCols(List<OrmColumn> notNullCheckCols) {
//		this.notNullCheckCols = notNullCheckCols;
//	}
	
	public OrmColumn getOptimisticLockCol() {
		return optimisticLockCol;
	}

	public void setOptimisticLockCol(OrmColumn optimisticLockCol) {
		this.optimisticLockCol = optimisticLockCol;
	}

	public List<OrmColumn> getUniqueCheckCols() {
		return uniqueCheckCols;
	}

	public void setUniqueCheckCols(List<OrmColumn> uniqueCheckCols) {
		this.uniqueCheckCols = uniqueCheckCols;
	}
	
	public List<OrmColumn> getValidateCols() {
		return validateCols;
	}

	public void setValidateCols(List<OrmColumn> validateCols) {
		this.validateCols = validateCols;
	}

	public List<OrmColumn> getSqlExecCols() {
		return sqlExecCols;
	}

	public void setSqlExecCols(List<OrmColumn> sqlExecCols) {
		this.sqlExecCols = sqlExecCols;
	}

	/**
	 * 外部扩张信息缓存
	 * @return
	 * 2015年4月26日 上午8:28:21
	 */
	public Map<String, Object> getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(Map<String, Object> extInfo) {
		this.extInfo = extInfo;
	}
	
	public List<OrmColumn> getValueCols() {
		return valueCols;
	}

	public void setValueCols(List<OrmColumn> valueCols) {
		this.valueCols = valueCols;
	}

	@Override
	public String toString() {
		return "OrmTable [" + tableDbName + ":"+ sequence + ", ormCols=" + ormCols + ", extInfo=" + extInfo + "]";
	}

	public Map<String, String> getJavaAsSqlColumn() {
		return javaAsSqlColumn;
	}

	public void setJavaAsSqlColumn(Map<String, String> javaAsSqlColumn) {
		this.javaAsSqlColumn = javaAsSqlColumn;
	}

	public Map<String, String> getSqlAsJavaColumn() {
		return sqlAsJavaColumn;
	}

	public void setSqlAsJavaColumn(Map<String, String> sqlAsJavaColumn) {
		this.sqlAsJavaColumn = sqlAsJavaColumn;
	}

	public Map<String, OrmColumn> getOrmColMap() {
		return ormColMap;
	}

	public void setOrmColMap(Map<String, OrmColumn> ormColMap) {
		this.ormColMap = ormColMap;
	}

	public boolean getUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}
}
