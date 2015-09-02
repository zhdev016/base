package org.whale.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.whale.system.base.BaseDao;
import org.whale.system.base.Query;
import org.whale.system.domain.User;

@Repository
public class UserDao extends BaseDao<User, Long> {
	
	public List<User> getByDeptId(Long deptId){

		return this.queryBy(Query.newQuery(User.class).addEq("deptId", deptId));
	}

	public User getByUserName(String userName){

		return this.getBy(Query.newQuery(User.class).addEq("userName", userName));
	}
	
	final String findAuthIds_SQL ="select ra.authId "+
			"from sys_user_role ur, sys_role_auth ra, sys_role r "+
			"where ur.userId=? "+
			"and ur.roleId = ra.roleId "+
			"and r.roleId = ra.roleId "+
			"and r.status = 1";
	public List<Long> findAuthIds(Long userId){
		
		List<Map<String, Object>> list = this.queryForList(findAuthIds_SQL, userId);
		if(list == null || list.size() < 1)
			return null;
		
		List<Long> rs = new ArrayList<Long>(list.size());
		for(Map<String, Object> map : list){
			rs.add(Long.parseLong(map.get("authId").toString()));
		}
		return rs;
	}
	
	
}
