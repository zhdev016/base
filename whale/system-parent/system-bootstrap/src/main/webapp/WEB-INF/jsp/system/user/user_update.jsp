<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/form.jsp" %>
<%@include file="/jsp/ztree.jsp" %>
<script type="text/javascript">
function save(){
	$.save({'url':'${ctx}/user/doUpdate'}); 
}

//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
			"userName": {
				validIllegalChar: true,
				maxlength: 16,
				required: true
			},
			"realName":{
				validIllegalChar: true,
				required: true,
				maxlength: 32
			},
			"phone": {
				validIllegalChar: true,
				required: true,
				isMobile: true,
				maxlength: 11
			},
			"email": {
				validIllegalChar: true,
				maxlength: 32,
				email: true
			},
			"addOn":{
				maxlength: 1024
			},
			"deptId":{
				required: true
			},
			"remark":{
				validIllegalChar: true,
				maxlength: 500
			}
		}
	});
});

</script>
</head>
    
<body class="my_formBody"> 
	<div class="navbar-fixed-bottom my_toolbar" >
		<button type="button" class="btn btn-primary btn-sm" onclick="save()"><i class="fa fa-hdd-o" ></i> 保存</button>
		<button type="button" class="btn btn-info btn-sm" onclick="$.closeWin();"><i class="fa fa-times" ></i> 关闭</button>
	</div>
	<div id="formBoxDiv" class="my_formBox" >
		<div id="infoBoxDiv" class="my_infoBox alert alert-danger"></div>
		<form action="" method="post" id="dataForm">
			<input type="hidden" id="userId" name="userId" value="${item.userId }" />
			<table class="query">
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
				<tbody>
						<tr>
							<td class="td-label"><span class="required">*</span>用户名</td>
							<td class="td-value">${item.userName }</td>
							<td class="td-label" ><span class="required">*</span>真实姓名</td>
							<td class="td-value"><input type="text" style="width:160px;" id="realName" name="realName" value="${item.realName }" maxlength="32" /></td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>联系电话</td>
							<td class="td-value"><input type="text" style="width:160px;" id="phone" name="phone" value="${item.phone }" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11" /></td>
							<td class="td-label" >邮箱地址</td>
							<td class="td-value"><input type="text" style="width:160px;" id="email" name="email" value="${item.email }" maxlength="32" /></td>
						</tr>
						<tr>
							<td class="td-label"><span class="required">*</span>所属部门</td>
							<td class="td-value">
								<tag:tree nodeName="deptName" nodeId="id" id="deptId" nodes="${nodes }" nodePId="pid" mulitSel="false" value="${item.deptId }"></tag:tree>
							</td>
							<td class="td-label" >附加信息</td>
							<td class="td-value">
								<input type="text" style="width:160px;" id="addOn" name="addOn" value="${item.addOn }" />
							</td>
						</tr>
						<tr>
							<td class="td-label" >备注</td>
							<td class="td-value" colspan="3">
								<textarea id="remark" name="remark" rows="5" cols="99" >
								${item.remark }
								</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
</body>
</html>
