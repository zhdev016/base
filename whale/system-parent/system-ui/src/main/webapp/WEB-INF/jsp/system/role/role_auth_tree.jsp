<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head >
<%@include file="/jsp/form.jsp" %>
<%@include file="/jsp/ztree.jsp" %>
<script type="text/javascript">
    var initSelIds = [];
	var zTree;

	var setting = {
			check: {
				enable: true,
				nocheckInherit: true
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId"
				},
				key:{
					name: "name"
				}
			}
		};
	
	var zNodes = [];
	var totalAuths = ${totalAuths};
	var hasAuths = ${hasAuths};
	var allMenus = ${allMenus}
	
	$(document).ready(function(){
		$("#treeDiv").height($.h()-150);
		zNodes.push({"id": 0, "pId": null, "name": "设置权限", "isParent": true,"open":true});
		
		for(var i=0; i<allMenus.length; i++){
			zNodes.push({"id": allMenus[i].menuId, "pId": allMenus[i].parentId, "name": allMenus[i].menuName, "isParent": true,"checked": false});
		}
		
		var checked = false;
		for(var i=0; i<totalAuths.length; i++){
			checked = false;
			for(var j=hasAuths.length-1; j>=0;j--){
				if(totalAuths[i].authId == hasAuths[j].authId){
					checked = true;
					hasAuths.splice(j,1);
					break;
				}
			}
			zNodes.push({"id": totalAuths[i].authCode, "pId": totalAuths[i].menuId, "name": totalAuths[i].authName, "isParent": false,"checked": checked});
		}
		
		for(var i=0; i<hasAuths.length; i++){
			zNodes.push({"id": hasAuths[i].authCode, "pId": totalAuths[i].menuId, "name": hasAuths[i].authName, "isParent": false,"checked": true,"chkDisabled":true});
		}
		
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("tree");
		
		zTree.expandAll(true);
	});
	
	function save(){
		var nodes = zTree.getCheckedNodes(true);
		var idArr = [];
		var id;
		if(nodes != null && nodes.length > 0){
			for(var i=0;i<nodes.length;i++){
				if(!nodes[i].isParent){
					idArr.push(nodes[i].id);
				}
			}
		}
		for(var i=0; i<hasAuths.length; i++){
			if(idArr.indexOf(hasAuths[i].authCode) == -1){
				idArr.push(hasAuths[i].authCode);
			}
		}
		
		$.save({url: "${ctx}/role/doSetRoleAuth?roleId=${roleId}",datas:{authCodeS: idArr.join(',')}, onSuccess: function(){
			$.msg('设置角色权限成功！');
		}});
	}
    </script>
</head>
<body class="my_formBody"> 
	<div id="formBoxDiv" class="my_formBox" >
		<div id="infoBoxDiv" class="my_infoBox alert alert-danger"></div>
		<div style="flow:left;height:410px;overflow:auto;" id="treeDiv">
			<ul id="tree" class="ztree"></ul>
		</div>
	</div>
	<div class="form-group">
                <div class="col-sm-12 ">
                    <button class="btn btn-primary" type="button" id="saveBut" onclick="save();"><i class='fa fa-save'></i> 保 存</button>
                </div>
            </div>
</body>
</html>


