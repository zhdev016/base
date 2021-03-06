package ${domain.pkgName!"org.whale.system"}.dao;

import org.springframework.stereotype.Repository;

import org.whale.system.base.BaseDao;
import ${domain.pkgName!"org.whale.system"}.domain.${domain.domainName};

/**
 * ${domain.domainCnName}Dao 
 *
 * @author ${domain.author}
 * ${.now}
 */
@Repository
public class ${domain.domainName}Dao extends BaseDao<${domain.domainName}, ${domain.idAttr.type}> {

<#list domain.attrs as attr>
    <#if !attr.isId && attr.isUnique>
    /**
	 * 按 ${attr.cnName} 获取 ${domain.domainCnName}
	 * @param ${attr.name} ${attr.cnName}
	 * @return
	 */
    public ${domain.domainName} getBy${attr.name?cap_first }(${attr.type} ${attr.name}) {
    	return this.getBy(this.cmd().and(${domain.domainName}.F_${attr.name}, ${attr.name}));
    }
    </#if>
</#list>
	
}