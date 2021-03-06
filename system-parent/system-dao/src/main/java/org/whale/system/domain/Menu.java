package org.whale.system.domain;

import java.util.HashMap;
import java.util.Map;

import org.whale.system.annotation.jdbc.Column;
import org.whale.system.annotation.jdbc.Id;
import org.whale.system.annotation.jdbc.Order;
import org.whale.system.annotation.jdbc.Table;
import org.whale.system.annotation.jdbc.Validate;
import org.whale.system.base.BaseEntry;
import org.whale.system.common.util.TreeNode;

/**
 * 
 * @author wjs
 *
 */
@Table(value="sys_menu", cnName="菜单")
public class Menu extends BaseEntry implements TreeNode{

	private static final long serialVersionUID = -122342341L;
	
	public static final int MENU_TAB = 1;
	
	public static final int MENU_FOLDER = 2;
	
	public static final int MENU_URL = 3;

	/**父id */
	public static final String F_parentId = "parentId";
	/**菜单名称 */
	public static final String F_menuName = "menuName";
	/**菜单类型 */
	public static final String F_menuType = "menuType";
	/**是否公共 */
	public static final String F_publicFlag = "publicFlag";

	@Id
	@Column(cnName="id")
	private Long menuId;
	
	@Validate(required=true)
	@Order(index=1)
	@Column(cnName="父id")
	private Long parentId;
	
	@Validate(required=true)
	@Column(unique=true, cnName="菜单名称")
	private String menuName;
	
	@Column(cnName="菜单类型")
	private Integer menuType;
	
	
	@Column(cnName="菜单地址")
	private String menuUrl;
	
	@Column(cnName="菜单图标")
	private String inco;
	
	@Column(cnName="打开类型")
	private Integer openType = 1;
	
	@Order(index=2)
	@Column(cnName="排序")
	private Integer orderNo;
	
	@Validate(enums={"0","1"})
	@Column(cnName="打开状态")
	private Integer openState = 1;
	
	@Column(cnName="是否公共")
	private Boolean publicFlag = false;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getInco() {
		return inco;
	}

	public void setInco(String inco) {
		this.inco = inco;
	}

	public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOpenState() {
		return openState;
	}

	public void setOpenState(Integer openState) {
		this.openState = openState;
	}


	public Boolean getPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(Boolean publicFlag) {
		this.publicFlag = publicFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inco == null) ? 0 : inco.hashCode());
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
		result = prime * result
				+ ((menuName == null) ? 0 : menuName.hashCode());
		result = prime * result
				+ ((menuType == null) ? 0 : menuType.hashCode());
		result = prime * result + ((menuUrl == null) ? 0 : menuUrl.hashCode());
		result = prime * result
				+ ((openState == null) ? 0 : openState.hashCode());
		result = prime * result
				+ ((openType == null) ? 0 : openType.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (other.getMenuId() == null){
			return this.getMenuId() == null;
		}
	
		return other.getMenuId().equals(this.menuId);
	}

	@Override
	public Long id() {
		return menuId;
	}

	@Override
	public Long pid() {
		return parentId;
	}

	@Override
	public String name() {
		return menuName;
	}

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("menuType", this.getMenuType());
		tmp.put("menuUrl", this.getMenuUrl());
		tmp.put("openType", this.getOpenType());
		tmp.put("openState", this.getOpenState());
		tmp.put("publicFlag", this.getPublicFlag());
		return tmp;
	}

}
