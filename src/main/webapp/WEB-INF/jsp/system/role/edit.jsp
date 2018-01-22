<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色编辑</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"src="${ctx}/static/my/system/role/edit.js"></script>
<script type="text/javascript">
	var permissions = '${permissions}';
</script>
</head>
<body>
	<div class="popup-container ad-credit">
		<form id="roleEditform" class="layui-form">
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<input id="id" type="hidden" name="id" value="${role.id}" /> 
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>唯一标识</label>
				<div class="layui-input-block">
					<input type="text" id="key" name="key" disabled="disabled"
						value="${role.key}" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>角色名称</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name" 
						value="${role.name}" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">工作内容</label>
				<textarea name="description" placeholder="最多输入20个中文字符"
					 rows="8" cols="60" maxlength='20'>${role.description}</textarea>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">权限</label>
				<div class="layui-input-block" lay-skin="primary">
					<div id="mytree"
						style="height: 200px; overflow: auto; border: 1px solid #ccc">
						<div class="ztree" id="permissionTree"></div>
					</div>
				</div>
			</div>
		 <div class="layui-form-item" style= "margin-top:50px">
                <div class="popup-div-content">
	                <button type="submit" class="layui-btn layui-btn-sm">确认</button>
	                <button type="reset" id="toCancel" class="layui-btn layui-btn-primary layui-btn-sm">取消</button> 
                </div>
            </div>
			<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
		</form>
	</div>
</body>
</html>