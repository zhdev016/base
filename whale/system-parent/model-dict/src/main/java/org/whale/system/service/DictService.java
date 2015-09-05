package org.whale.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.whale.system.base.Cmd;
import org.whale.system.cache.service.DictCacheService;
import org.whale.system.common.util.Strings;
import org.whale.system.dao.DictDao;
import org.whale.system.dao.DictItemDao;
import org.whale.system.domain.Dict;
import org.whale.system.domain.DictItem;
import org.whale.system.jdbc.IOrmDao;

@Service
public class DictService extends BaseService<Dict, Long> {

	@Autowired
	private DictDao dictDao;
	@Autowired
	private DictItemDao dictItemDao;
	@Autowired
	private DictCacheService dictCacheService;
	
	@Override
	public void delete(Long dictId){
		this.dictDao.delete(dictId);
		
		this.dictItemDao.deleteBy(Cmd.newCmd(DictItem.class).and("dictId", dictId));
	}
	
	public Dict getByDictCode(String dictCode){
		if(Strings.isBlank(dictCode))
			return null;
		return this.dictDao.getByDictCode(dictCode.trim());
	}
	
	@Override
	public IOrmDao<Dict, Long> getDao() {
		return dictDao;
	}
	
}
