package org.whale.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.whale.system.base.BaseDao;
import org.whale.system.domain.RoleAuth;

@Repository
public class RoleAuthDao extends BaseDao<RoleAuth, Long> {

	public List<RoleAuth> getByRoleId(Long roleId){
		
		return this.query(this.q().eq(RoleAuth.F_roleId, roleId));
	}
		
	public List<RoleAuth> getByAuthCode(String authCode){
		
		return this.query(this.q().eq(RoleAuth.F_authCode, authCode));
	}
	
	public void deleteByRoleId(Long roleId){
		
		this.delete(this.q().eq(RoleAuth.F_roleId, roleId));
	}
	
	public void deleteByAuthCode(String authCode){
		
		this.delete(this.q().eq(RoleAuth.F_authCode, authCode));
	}
}
