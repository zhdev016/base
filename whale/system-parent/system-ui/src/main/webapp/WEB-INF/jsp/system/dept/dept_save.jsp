<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增 部门</title>
<%@include file="/jsp/form.jsp" %>
<%@include file="/jsp/ztree.jsp" %>
<script type="text/javascript">
function save(){
	$.save({'url':'${ctx}/dept/doSave'}); 
}

//校验函数
$(function() {
	$("#dataForm").validate({
		rules: {
			"deptName": {
				validIllegalChar: true,
				maxlength: 64,
				required: true
			},
			"deptCode": {
				validIllegalChar: true,
				maxlength: 32
			},
			"orderNo": {
				maxlength: 4
			},
			"deptTel": {
				validIllegalChar: true,
				maxlength: 32
			},
			"deptAddr": {
				validIllegalChar: true,
				maxlength: 128
			}
		}
	});
});

</script>
</head>
    
<body style="overflow-x: hidden;"> 
	<ul class="nav nav-tabs" id="topTab">
    	<li><a href="#" onclick="go('${ctx}/dept/goList')">机构列表</a></li>
    	<li class="active"><a href="${ctx}/dept/goSave"><i class="fa fa-plus"></i> 新增机构</a></li>
	</ul>
	
	<div id="infoBoxDiv" class="my_infoBox alert alert-danger"></div>
	<div class="row" style="margin:10px 20px;">
        <form class="form-horizontal m-t" id="dataForm">
            <div class="form-group">
                <label class="col-sm-2 control-label">父部门：</label>
                <div class="input-group col-sm-4 ">
                    <tag:tree nodeName="deptName" nodeId="id" id="pid" nodes="${nodes }" nodePId="pid" afterLoadTree="filterSelf" value="${item.pid }"></tag:tree>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"><span class="required">*</span>部门名称：</label>
                <div class="col-sm-4">
                    <input id="deptName" name="deptName" class="form-control" required="" aria-required="true" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门编码：</label>
                <div class="col-sm-4">
                    <input id="deptCode" name="deptCode" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系电话：</label>
                <div class="col-sm-4">
                    <input id="deptTel" name="deptTel" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系地址：</label>
                <div class="col-sm-4">
                    <input id="deptAddr" name="deptAddr" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序：</label>
                <div class="col-sm-4">
                    <input id="orderNo" name="orderNo" value="${orderNo }" onkeyup="value=value.replace(/[^\d]/g,'')" class="form-control" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注：</label>
                <div class="col-sm-4">
                    <textarea id="remark" name="remark" rows="3" cols="58" ></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12 col-sm-offset-2">
                    <button class="btn btn-primary" type="button" id="saveBut" onclick="save();"><i class='fa fa-save'></i> 提 交</button>
                    <button class="btn btn-success" type="button" id="continueBut" onclick="go('${ctx}/dept/goSave');" style="display: none;"><i class='fa fa-thumbs-up'></i> 继续添加</button>
                </div>
            </div>
        </form>
     </div>
</body>
</html>
