package org.whale.system.base;

import org.springframework.core.PriorityOrdered;

/**
 * 
 */
public interface UserContextAccessor extends PriorityOrdered{

	void addCustomDatas(UserContext uc);
}
