<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色编辑</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/role/details.js"></script>
<script type="text/javascript">
	var permissions = '${permissions}';
</script>
	
</head>
<body>
	<div class="cont-contnet">
		<div id="roleform" class="layui-form">
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>唯一标识</label>
				<div class="layui-input-block">
					<input type="text" name="key" disabled="disabled"
						value="${role.key}" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>角色名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" disabled="disabled"
						value="${role.name}" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">工作内容</label>
				<textarea name="description" rows="8" cols="60" disabled="disabled" ">${role.description}</textarea>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">权限</label>
				<div class="layui-input-block" lay-skin="primary" style="z-index:1002">
					
					<div id="mytree"  
						style="height: 200px; overflow: auto; border: 1px solid #ccc">
						<div class="ztree" id="permissionTree"></div>
					</div>
					
				</div>
			</div>
			<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
		</div>
	</div>
</body>
</html>