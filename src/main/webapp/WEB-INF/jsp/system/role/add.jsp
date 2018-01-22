<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色编辑</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/role/add.js"></script>
</head>
<body>
	<div class="popup-container ad-credit">
		<form id="roleAddform" class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>唯一标识</label>
				<div class="layui-input-block">
					<input type="text" id="key" name="key" value="" placeholder="请输入" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>角色名称</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name" 
						value="" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">工作内容</label>
				<textarea name="description" placeholder="最多输入20个中文字符"
					lay-verify="required" rows="8" cols="60" maxlength='20'>${role.description}</textarea>
			</div>

			<div class="layui-form-item">
				<div class="layui-form-item">
					<label class="layui-form-label">权限</label>
					<div class="layui-input-block" lay-skin="primary">
						<div id="mytree"
							style="height: 200px; overflow: auto; border: 1px solid #ccc">
							<div class="ztree" id="permissionTree"></div>
						</div>
					</div>
				</div>
			</div>
			<input type="hidden" id="ztree" class="layui-input">
		 <div class="layui-form-item">
				<div class="popup-div-content">
					<button type="submit" class="layui-btn layui-btn-sm">确认</button>
					<button type="button" id="toCancel"
						class="layui-btn layui-btn-primary layui-btn-sm">取消</button>
				</div>
			</div>
			<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
		</form>
	</div>
</body>
</html>